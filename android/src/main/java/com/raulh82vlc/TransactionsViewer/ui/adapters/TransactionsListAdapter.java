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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raulh82vlc.TransactionsViewer.R;
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionUI;
import com.raulh82vlc.TransactionsViewer.ui.viewholders.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Transactions listing
 */
public class TransactionsListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<TransactionUI> mTransactions = new ArrayList<>();

    public TransactionsListAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View aItemListView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list, parent, false);
        ViewHolder myViewHolder = new ViewHolder(aItemListView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final TransactionUI transaction = mTransactions.get(position);
        Context ctx = viewHolder.itemView.getContext();
        String prevAmount = ctx.getString(R.string.amount_transaction,
                transaction.getCurrencyPrev(), String.valueOf(transaction.getAmounPerTransactionPrev()));
        viewHolder.mTitle.setText(prevAmount);
        final String currentAmount = ctx.getString(R.string.amount_transaction, transaction.getCurrencyCurrent(),
                transaction.getAmountPerTransactionCurrent());
        viewHolder.mSubtitle.setText(currentAmount);
        viewHolder.itemView.setTag(transaction);
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public void updateTransactions(List<TransactionUI> transactionUIList) {
        mTransactions.clear();
        mTransactions.addAll(transactionUIList);
        notifyDataSetChanged();
    }
}
