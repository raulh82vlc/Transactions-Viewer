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

package com.raulh82vlc.TransactionsViewer.domain.repository;

import com.raulh82vlc.TransactionsViewer.domain.datasources.json.JSONDataSourceImpl;
import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.domain.repository.datasources.JSONDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;


/**
 * <p>Implements {@link DataRepository} and allows to have one or more Datasource
 * like {@link JSONDataSource} or one another on it</p>
 *
 * @author Raul Hernandez Lopez
 */
public class JSONRepositoryImpl implements DataRepository<Rate, Transaction> {

    private static final int SUPPORTED_SIMULTANEOUS_PATHS = 1;
    private JSONDataSource<Rate, Transaction> mJsonDataSource;
    private List<Rate> mRates = new ArrayList<>();
    private List<Transaction> mTransactions = new ArrayList<>();
    private Set<String> dataPathListRates = new HashSet<>(SUPPORTED_SIMULTANEOUS_PATHS);
    private Set<String> dataPathListTransactions = new HashSet<>(SUPPORTED_SIMULTANEOUS_PATHS);
    private Map<String, List<Transaction>> mDictionaryOfTransactions;

    @Inject
    JSONRepositoryImpl(JSONDataSourceImpl jsonDataSource) {
        if (mJsonDataSource == null) {
            synchronized (JSONRepositoryImpl.class) {
                if (mJsonDataSource == null) {
                    mJsonDataSource = jsonDataSource;
                }
            }
        }
    }

    @Override
    public List<Rate> getRatesList(String datapath) throws CustomException {
        if (mRates.size() == 0 || !dataPathListRates.contains(datapath)) {
            addRates(datapath);
            cleanUpRatesKeySet();
            dataPathListRates.add(datapath);
        }
        return mRates;
    }

    private void addRates(String datapath) throws CustomException {
        mRates.clear();
        mRates.addAll(mJsonDataSource.getRatesList(datapath));
    }

    private void cleanUpRatesKeySet() {
        if (dataPathListRates.size() == SUPPORTED_SIMULTANEOUS_PATHS) {
            dataPathListRates.clear();
        }
    }

    @Override
    public List<Transaction> getTransactionsList(String datapath) throws CustomException {
        if (mTransactions.size() == 0 || !dataPathListTransactions.contains(datapath)) {
            addTransactions(datapath);
            cleanUpTransactionsKeySet();
            dataPathListTransactions.add(datapath);
        }
        return mTransactions;
    }

    private void addTransactions(String datapath) throws CustomException {
        mTransactions.clear();
        mTransactions.addAll(mJsonDataSource.getTransactionsList(datapath));
    }

    private void cleanUpTransactionsKeySet() {
        if (dataPathListTransactions.size() == SUPPORTED_SIMULTANEOUS_PATHS) {
            dataPathListTransactions.clear();
        }
    }

    @Override
    public List<Transaction> getTransactionsPerSku(String pathTransactions, String sku) {
        if (dataPathListTransactions.contains(pathTransactions)
            && mDictionaryOfTransactions != null) {
            List<Transaction> transactions = mDictionaryOfTransactions.get(sku);
            if (transactions != null) {
                return transactions;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean saveTransactions(Map<String, List<Transaction>> map) {
        int size = map.size();
        if (size > 0) {
            if (mDictionaryOfTransactions != null) {
                mDictionaryOfTransactions.clear();
            } else {
                mDictionaryOfTransactions = new HashMap<>(size);
            }
            for (Map.Entry<String, List<Transaction>> entry : map.entrySet()) {
                mDictionaryOfTransactions.put(entry.getKey(), entry.getValue());
            }
            if (mDictionaryOfTransactions.size() > 0) {
                return true;
            }
        }
        return false;
    }
}
