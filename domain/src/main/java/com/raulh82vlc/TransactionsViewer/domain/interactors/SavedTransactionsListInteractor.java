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

package com.raulh82vlc.TransactionsViewer.domain.interactors;

import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;

import java.util.List;
import java.util.Map;

/**
 * Save Transactions interactor
 */

public interface SavedTransactionsListInteractor {
    void executeSaveTransactions(Map<String, List<Transaction>> transactionList,
                                 SavedTransactionsCallback savedTransactionsCallback) throws CustomException;

    interface SavedTransactionsCallback {
        void onSavedTransactionsListOK(String msg);

        void onSavedTransactionsListKO(String error);
    }
}
