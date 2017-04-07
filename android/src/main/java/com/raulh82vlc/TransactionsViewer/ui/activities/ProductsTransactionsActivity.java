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

package com.raulh82vlc.TransactionsViewer.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh82vlc.TransactionsViewer.R;
import com.raulh82vlc.TransactionsViewer.TransactionsViewerApp;
import com.raulh82vlc.TransactionsViewer.di.components.DaggerProductTransactionsComponent;
import com.raulh82vlc.TransactionsViewer.di.components.ProductTransactionsComponent;
import com.raulh82vlc.TransactionsViewer.di.modules.ActivityModule;
import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionUI;
import com.raulh82vlc.TransactionsViewer.ui.adapters.TransactionsListAdapter;
import com.raulh82vlc.TransactionsViewer.ui.presentation.ComputingTransactionsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * <p>Products Transactions UI</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ProductsTransactionsActivity extends BaseActivity implements ComputingTransactionsPresenter.View {
    private final static String KEY_PRODUCT_SKU = "PRODUCT_SKU";
    // CONSTANTS
    private static final String TAG = ProductsTransactionsActivity.class.getSimpleName();
    private static final String GBP_CURRENCY = "GBP";
    private static final String KEY_TRANSACTIONS_PATH = "TRANSACTIONS_PATH";
    private static final String KEY_RATES_PATH = "RATES_PATH";

    // UI Injections
    @InjectView(R.id.title_txt)
    TextView mTitleTxt;
    @InjectView(R.id.no_results_view)
    TextView mEmptyState;
    @InjectView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    // Transactions list adapter
    private TransactionsListAdapter mAdapter;

    // Product Component for dagger
    private ProductTransactionsComponent ProductTransactionsComponent;

    @Inject
    ComputingTransactionsPresenter computingTransactionsPresenter;

    // Data structures
    private String mSkuFromProduct;
    private String mPathTransactions;
    private String mPathRates;

    public static void navigateToDetailsActivity(AppCompatActivity activity, String sku,
                                                 String pathRates, String pathTransactions) {
        Intent intent = new Intent(activity, ProductsTransactionsActivity.class);
        Bundle data = new Bundle();
        data.putString(KEY_PRODUCT_SKU, sku);
        data.putString(KEY_TRANSACTIONS_PATH, pathTransactions);
        data.putString(KEY_RATES_PATH, pathRates);
        intent.putExtras(data);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    public ProductTransactionsComponent component() {
        if (ProductTransactionsComponent == null) {
            ProductTransactionsComponent = DaggerProductTransactionsComponent.builder()
                    .applicationComponent(((TransactionsViewerApp) getApplication()).component())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return ProductTransactionsComponent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);

        computingTransactionsPresenter.setView(this);
        if (getIntent() != null) {
            mSkuFromProduct = getIntent().getStringExtra(KEY_PRODUCT_SKU);
            mPathTransactions = getIntent().getStringExtra(KEY_TRANSACTIONS_PATH);
            mPathRates = getIntent().getStringExtra(KEY_RATES_PATH);
            setInitialTitle();
            setTransactions();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            setRecyclerAdapter();
        }
        setToolbarInitialisation();
    }

    private void setTransactions() {
        startLoader();
        try {
            computingTransactionsPresenter.computeRates(mSkuFromProduct, GBP_CURRENCY, mPathTransactions, mPathRates);
        } catch (CustomException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void startLoader() {
        hideLoader();
        showLoaderWithTitleAndDescription(
                getString(R.string.detail_screen, mSkuFromProduct),
                getString(R.string.loading));
    }

    @Override
    protected void setInitialTitle() {
        mToolbar.setTitle(getString(R.string.detail_screen, mSkuFromProduct));
    }

    @Override
    protected void setToolbarInitialisation() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_details;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    /**
     * <p>Sets the adapter and recyclerview</p>
     **/
    private void setRecyclerAdapter() {
        mAdapter = new TransactionsListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        mAdapter = null;
        computingTransactionsPresenter.resetView();
        super.onDestroy();
    }

    private void setUIWidgetsVisibility(int visible, int gone) {
        mEmptyState.setVisibility(visible);
        mRecyclerView.setVisibility(gone);
        mTitleTxt.setVisibility(gone);
    }

    @Override
    public void errorComputingRates(String error) {
        Log.e(TAG, error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
        hideLoader();
    }

    @Override
    public void computedRatesForTransactions(List<TransactionUI> transactions, String totalAmount) {
        mAdapter.updateTransactions(transactions);
        mTitleTxt.setText(getString(R.string.title_on_transaction_detail, totalAmount));
        setUIWidgetsVisibility(View.GONE, View.VISIBLE);
        hideLoader();
    }

    @Override
    public boolean isReady() {
        return mAdapter != null;
    }
}
