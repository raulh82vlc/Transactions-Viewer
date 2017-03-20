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

package com.raulh82vlc.TransactionsViewer.domain.datasources.json;

import android.content.Context;

import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.domain.repository.datasources.JSONDataSource;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link JSONDataSource} Implementation
 *
 * @author Raul Hernandez Lopez
 */
public class JSONDataSourceImpl implements JSONDataSource<Rate, Transaction> {

    /**
     * Vars declaration
     */
    private Context mContext;
    private JSONOperations<Rate, Transaction> mJSONOperations;

    @Inject
    JSONDataSourceImpl(Context context,
                       JSONOperationsImpl jsonOperations) {
        mContext = context;
        if (mJSONOperations == null) {
            synchronized (this) {
                if (mJSONOperations == null) {
                    mJSONOperations = jsonOperations;
                }
            }
        }
    }

    @Override
    public List<Rate> getRatesList(String path) throws CustomException {
        return startToReadRatesFromFile(path);
    }

    @Override
    public List<Transaction> getTransactionsList(String path) throws CustomException {
        return startToReadTransactionsFromFile(path);
    }

    /**
     * Starts to read from file the Transactions List
     *
     * @return List of transactions
     * @throws CustomException
     */
    private List<Transaction> startToReadTransactionsFromFile(String path) throws CustomException {
        return mJSONOperations.getTransactionsList(mContext, path);
    }

    /**
     * Starts to read from file the Rates List
     *
     * @return List of rates
     * @throws CustomException
     */
    private List<Rate> startToReadRatesFromFile(String path) throws CustomException {
        return mJSONOperations.getRatesList(mContext, path);
    }
}
