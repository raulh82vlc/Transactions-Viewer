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


import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;

import java.util.List;
import java.util.Map;

/**
 * Repository contract
 *
 * @author Raul Hernandez Lopez
 */
public interface DataRepository<R, T> {

    /**
     * to get Rates List
     **/
    List<R> getRatesList(String path) throws CustomException;

    /**
     * to get Transactions list
     **/
    List<T> getTransactionsList(String path) throws CustomException;

    /**
     * to get Transactions per product's SKU
     **/
    List<T> getTransactionsPerSku(String mPathTransactions, String mSku);

    /**
     * to save Transactions indexed per product's SKU
     **/
    boolean saveTransactions(Map<String, List<T>> map);
}
