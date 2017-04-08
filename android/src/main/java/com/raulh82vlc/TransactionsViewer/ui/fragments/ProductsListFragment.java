/*
 * Copyright (C) 2017 Raul Hernandez Lopez @raulh82vlc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raulh82vlc.TransactionsViewer.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh82vlc.TransactionsViewer.R;
import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.ProductUI;
import com.raulh82vlc.TransactionsViewer.domain.models.RateUI;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.ui.activities.ProductsListActivity;
import com.raulh82vlc.TransactionsViewer.ui.activities.ProductsTransactionsActivity;
import com.raulh82vlc.TransactionsViewer.ui.adapters.ProductsListAdapter;
import com.raulh82vlc.TransactionsViewer.ui.presentation.RatesPresenter;
import com.raulh82vlc.TransactionsViewer.ui.presentation.TransactionsPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * <p>Product List Fragment which uses all injected views or components</p>
 * <p>first of all when the activity is created the component,
 * as well as presenter and view injections for each UI element</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ProductsListFragment extends BaseFragment implements
        RatesPresenter.View,
        TransactionsPresenter.View,
        ProductsListAdapter.OnItemClickListener {

    private static final String TAG = ProductsListFragment.class.getSimpleName();
    private static final String PATH_RATES_DEFAULT = "rates1.json";
    private static final String PATH_TRANSACTIONS_DEFAULT = "transactions1.json";
    private static final String PATH_RATES_PATH2 = "rates2.json";
    private static final String PATH_TRANSACTIONS_PATH2 =  "transactions2.json";

    /**
     * UI injections
     */
    @InjectView(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    @InjectView(R.id.no_results_view)
    public TextView mNoResultsTextView;
    /**
     * DI
     */
    @Inject
    RatesPresenter ratePresenter;

    @Inject
    TransactionsPresenter transactionsPresenter;

    // UI Widgets
    private ProductsListAdapter mAdapter;
    private ProductsListActivity mActivity;
    // Variable which defines what Path for data sets to pass to the detail Intent
    private String mTransactionsPath = PATH_TRANSACTIONS_DEFAULT, mRatesPath = PATH_RATES_DEFAULT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (ProductsListActivity) context;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.component().inject(this);
        ratePresenter.setView(this);
        transactionsPresenter.setView(this);
        loadInfo(PATH_TRANSACTIONS_DEFAULT, PATH_RATES_DEFAULT);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        setRecyclerAdapter();
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
    }

    @Override
    public void onDestroyView() {
        transactionsPresenter.resetView();
        ratePresenter.resetView();
        mAdapter = null;
        super.onDestroyView();
    }

    /**
     * <p>Sets the adapter and recyclerview</p>
     **/
    private void setRecyclerAdapter() {
        mAdapter = new ProductsListAdapter();
        mAdapter.setOnItemClickFromList(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUIWidgetsVisibility(int visible, int gone) {
        mNoResultsTextView.setVisibility(visible);
        mRecyclerView.setVisibility(gone);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.path_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_path1:
                mTransactionsPath = PATH_TRANSACTIONS_DEFAULT;
                mRatesPath = PATH_RATES_DEFAULT;
                loadInfo(mTransactionsPath, mRatesPath);
                return true;
            case R.id.action_path2:
                mTransactionsPath = PATH_TRANSACTIONS_PATH2;
                mRatesPath = PATH_RATES_PATH2;
                loadInfo(mTransactionsPath, mRatesPath);
                return true;
            default:
                return false;
        }
    }

    /**
     * <p>Starts queries for Transactions and Rates</p>
     **/
    public void loadInfo(String pathTransactions, String pathRates) {
        mActivity.showLoaderWithTitleAndDescription(getString(R.string.main_screen),
                getString(R.string.loading));
        loadTransactions(pathTransactions);
        loadRates(pathRates);
    }

    private void loadTransactions(String path) {
        try {
            transactionsPresenter.startReading(path);
        } catch (CustomException e) {
            Log.e(TAG, e.getMessage(), e);
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadRates(String path) {
        try {
            ratePresenter.startReading(path);
        } catch (CustomException e) {
            Log.e(TAG, e.getMessage(), e);
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void addProducts(List<ProductUI> productUIList) {
        mAdapter.updateProducts(productUIList);
        setUIWidgetsVisibility(View.GONE, View.VISIBLE);
    }

    @Override
    public void errorGettingRates(String error) {
        Toast.makeText(mActivity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadedRates(List<RateUI> listOfRates) {
        Toast.makeText(mActivity, getString(R.string.rates_successfully_loaded), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemFromListClick(ProductUI productUI) {
        /** go to the detail screen */
        ProductsTransactionsActivity.navigateToDetailsActivity(mActivity, productUI.getSku(),
                mRatesPath, mTransactionsPath);
    }

    @Override
    public void saveProducts(Map<String, List<Transaction>> transactionsMap, List<Transaction> transactionList) {
        try {
            transactionsPresenter.saveProducts(transactionList, transactionsMap);
        } catch (CustomException e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errorSavingProducts(String error) {
        Toast.makeText(mActivity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void productsSavedSuccessfully(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorGettingTransactions(String error) {
        mAdapter.updateProducts(new ArrayList<ProductUI>());
        mAdapter.notifyDataSetChanged();
        showEmptyState();
        mNoResultsTextView.setText(error);
        mActivity.hideLoader();
        // Toast message of Error
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProductsList(List<ProductUI> productUIs) {
        addProducts(productUIs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRecyclerView.scheduleLayoutAnimation();
        }
        mActivity.hideLoader();
    }

    @Override
    public void showEmptyState() {
        mNoResultsTextView.setText(getString(R.string.no_results));
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
        mActivity.hideLoader();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }
}
