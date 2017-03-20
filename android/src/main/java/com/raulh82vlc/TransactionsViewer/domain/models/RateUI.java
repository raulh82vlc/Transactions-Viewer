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
 * Rate adapted to UI needs
 *
 * @author Raul Hernandez Lopez.
 */

public class RateUI implements Parcelable {
    private String fromCurrency;
    private String toCurrency;
    private String rate;

    public RateUI(String fromCurrency, String toCurrency, String rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    private RateUI(Parcel in) {
        fromCurrency = in.readString();
        toCurrency = in.readString();
        rate = in.readString();
    }

    public static final Creator<RateUI> CREATOR = new Creator<RateUI>() {
        @Override
        public RateUI createFromParcel(Parcel in) {
            return new RateUI(in);
        }

        @Override
        public RateUI[] newArray(int size) {
            return new RateUI[size];
        }
    };

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getRate() {
        return rate;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromCurrency);
        dest.writeString(toCurrency);
        dest.writeString(rate);
    }
}
