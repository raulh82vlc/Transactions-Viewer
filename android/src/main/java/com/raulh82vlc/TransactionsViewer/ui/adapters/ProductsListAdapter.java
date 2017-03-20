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

package com.raulh82vlc.TransactionsViewer.ui.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raulh82vlc.TransactionsViewer.R;
import com.raulh82vlc.TransactionsViewer.domain.models.ProductUI;
import com.raulh82vlc.TransactionsViewer.ui.viewholders.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Products listing
 */
public class ProductsListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private List<ProductUI> mProductUIs = new ArrayList<>();

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            if (mOnItemClickListener != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOnItemClickListener.onItemFromListClick((ProductUI) view.getTag());
                    }
                }, 200);
            }
        }
    };

    public ProductsListAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View aItemListView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list, parent, false);
        ViewHolder myViewHolder = new ViewHolder(aItemListView);
        setListeners(myViewHolder);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder productViewHolder, int position) {
        final ProductUI productUI = mProductUIs.get(position);
        productViewHolder.mTitle.setText(productUI.getSku());
        final String transactions = productViewHolder.itemView.getContext().getString(R.string.transaction_product, productUI.getTransactions().size());
        productViewHolder.mSubtitle.setText(transactions);
        productViewHolder.itemView.setTag(productUI);
    }

    @Override
    public int getItemCount() {
        return mProductUIs.size();
    }

    /**
     * Sets the corresponding listeners
     * for a click {@link View.OnClickListener} to our {@link ViewHolder} item
     *
     * @param productViewHolder {@link ViewHolder}
     */
    private void setListeners(final ViewHolder productViewHolder) {
        if (mOnItemClickListener != null) {
            productViewHolder.itemView.setOnClickListener(mOnClickListener);
        }
    }

    public void setOnItemClickFromList(OnItemClickListener onItemClickFromList) {
        mOnItemClickListener = onItemClickFromList;
    }

    public interface OnItemClickListener {
        void onItemFromListClick(ProductUI productUI);
    }

    public void updateProducts(List<ProductUI> productUIList) {
        mProductUIs.clear();
        mProductUIs.addAll(productUIList);
        notifyDataSetChanged();
    }
}
