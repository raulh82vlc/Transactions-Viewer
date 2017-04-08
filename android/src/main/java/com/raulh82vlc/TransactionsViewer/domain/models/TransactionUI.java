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

/**
 * Transaction adapted to UI needs
 *
 * @author Raul Hernandez Lopez.
 */

public class TransactionUI implements Parcelable {
    private String amounPerTransactionPrev;
    private String amountPerTransactionCurrent;
    private String currencyPrev;
    private String currencyCurrent;

    public TransactionUI(String currencyPrev, String currencyCurrent, String amounPerTransactionPrev,
                         String amountPerTransactionCurrent) {
        this.currencyPrev = currencyPrev;
        this.currencyCurrent = currencyCurrent;
        this.amounPerTransactionPrev = amounPerTransactionPrev;
        this.amountPerTransactionCurrent = amountPerTransactionCurrent;
    }

    private TransactionUI(Parcel in) {
        currencyPrev = in.readString();
        currencyCurrent = in.readString();
        amounPerTransactionPrev = in.readString();
        amountPerTransactionCurrent = in.readString();
    }

    public static final Creator<TransactionUI> CREATOR = new Creator<TransactionUI>() {
        @Override
        public TransactionUI createFromParcel(Parcel in) {
            return new TransactionUI(in);
        }

        @Override
        public TransactionUI[] newArray(int size) {
            return new TransactionUI[size];
        }
    };

    public String getAmounPerTransactionPrev() {
        return amounPerTransactionPrev;
    }

    public String getAmountPerTransactionCurrent() {
        return amountPerTransactionCurrent;
    }

    public String getCurrencyPrev() {
        return currencyPrev;
    }

    public String getCurrencyCurrent() {
        return currencyCurrent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currencyPrev);
        dest.writeString(currencyCurrent);
        dest.writeString(amounPerTransactionPrev);
        dest.writeString(amountPerTransactionCurrent);
    }
}
