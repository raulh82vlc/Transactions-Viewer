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

import java.util.List;

/**
 * JSON Operations API contract
 *
 * @author Raul Hernandez Lopez
 */
public interface JSONOperations<R, T> {
    /**
     * Gets a the list of rates from the JSON file
     *
     * @param path where is located the JSON file
     */
    List<R> getRatesList(Context context, String path) throws CustomException;

    /**
     * Gets a the list of transactions from the JSON file
     *
     * @param path where is located the JSON file
     */
    List<T> getTransactionsList(Context context, String path) throws CustomException;
}
