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

package com.raulh82vlc.TransactionsViewer.domain.repository.datasources;


import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;

import java.util.List;

/**
 * <p>Source of retrieving info from the JSON files</p>
 *
 * @author Raul Hernandez Lopez
 */
public interface JSONDataSource<R, T> {

    /**
     * Gets a Rates List from a JSON file
     *
     * @param path path of the file
     */
    List<R> getRatesList(String path) throws CustomException;

    /**
     * Gets a Transactions List from a JSON file
     *
     * @param path path of the file
     */
    List<T> getTransactionsList(String path) throws CustomException;
}
