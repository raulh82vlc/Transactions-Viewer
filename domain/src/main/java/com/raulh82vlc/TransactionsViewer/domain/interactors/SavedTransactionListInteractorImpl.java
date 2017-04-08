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
import com.raulh82vlc.TransactionsViewer.domain.executors.Interactor;
import com.raulh82vlc.TransactionsViewer.domain.executors.InteractorExecutor;
import com.raulh82vlc.TransactionsViewer.domain.executors.MainThread;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.domain.repository.DataRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Saved Transactions List Interactor enables repository saving a well as interaction with presenter
 * @author Raul Hernandez Lopez
 */
public class SavedTransactionListInteractorImpl implements SavedTransactionsListInteractor, Interactor {

    private InteractorExecutor executor;
    private MainThread mainThread;
    private DataRepository<Rate, Transaction> repository;
    private Map<String, List<Transaction>> map;
    private SavedTransactionsCallback callback;

    @Inject
    SavedTransactionListInteractorImpl(InteractorExecutor executor,
                                       MainThread mainThread,
                                       DataRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void executeSaveTransactions(Map<String, List<Transaction>> transactionDictionary,
                                        SavedTransactionsCallback savedTransactionsCallback) throws CustomException {
        this.map = transactionDictionary;
        this.callback = savedTransactionsCallback;
        this.executor.run(this);
    }

    @Override
    public void run() throws CustomException {
        if (repository.saveTransactions(map)) {
            notifySuccessfullySaved("Transactions were saved succesfully.");
        } else {
            notifyError("Transactions were not saved properly.");
        }
    }


    /**
     * <p>Notifies to the UI (main) thread the corresponding callback</p>
     */
    private void notifySuccessfullySaved(final String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSavedTransactionsListOK(msg);
            }
        });
    }

    /**
     * <p>Notifies to the UI (main) thread that an error has happened</p>
     */
    private void notifyError(final String errorMessage) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSavedTransactionsListKO(errorMessage);
            }
        });
    }
}
