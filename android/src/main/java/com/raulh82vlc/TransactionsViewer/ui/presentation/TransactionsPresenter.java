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

package com.raulh82vlc.TransactionsViewer.ui.presentation;

import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.ProductUI;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;

import java.util.List;
import java.util.Map;

/**
 * <p>Presenter responsible of asking for Transactions and get them back if any available</p>
 *
 * @author Raul Hernandez Lopez
 */
public interface TransactionsPresenter {
    void startReading(String path) throws CustomException;

    void setView(View view);

    void saveProducts(List<Transaction> transactionList, Map<String, List<Transaction>> transactionsMap)
            throws CustomException;

    void resetView();

    interface View {
        void saveProducts(Map<String, List<Transaction>> transactionsMap, List<Transaction> transactionList);

        void errorSavingProducts(String error);

        void productsSavedSuccessfully(String msg);

        void errorGettingTransactions(String error);

        void showProductsList(List<ProductUI> productUIs);

        void showEmptyState();

        boolean isReady();
    }
}
