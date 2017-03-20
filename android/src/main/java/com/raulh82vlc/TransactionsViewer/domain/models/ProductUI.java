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

package com.raulh82vlc.TransactionsViewer.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Product model adapter for UI needs
 *
 * @author Raul Hernandez Lopez.
 */

public class ProductUI implements Parcelable {

    private String sku;
    private List<TransactionUI> transactions;

    public static final Creator<ProductUI> CREATOR = new Creator<ProductUI>() {
        @Override
        public ProductUI createFromParcel(Parcel in) {
            return new ProductUI(in);
        }

        @Override
        public ProductUI[] newArray(int size) {
            return new ProductUI[size];
        }
    };

    private ProductUI(Parcel in) {
        sku = in.readString();
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        in.readList(transactions, Transaction.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sku);
        dest.writeList(transactions);
    }

    public ProductUI(String key, List<Transaction> values, String toCurrency) {
        sku = key;
        transactions = new ArrayList<>(values.size());
        for (Transaction transaction : values) {
            transactions.add(new TransactionUI(transaction.getCurrency(), toCurrency,
                    transaction.getAmountPerTransaction(), "0"));
        }

    }

    public List<TransactionUI> getTransactions() {
        return transactions;
    }

    public String getSku() {
        return sku;
    }
}
