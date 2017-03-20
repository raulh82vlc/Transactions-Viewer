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

import javax.inject.Inject;

/**
 * Implementation of the Get specific details of a movie
 *
 * @author Raul Hernandez Lopez
 */
public class GetTransactionListInteractorImpl implements GetTransactionListInteractor, Interactor {

    private InteractorExecutor executor;
    private MainThread mainThread;
    private DataRepository<Rate, Transaction> repository;
    private GetTransactionsListCallback callback;
    private String path;

    @Inject
    GetTransactionListInteractorImpl(InteractorExecutor executor,
                                     MainThread mainThread,
                                     DataRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void execute(String path, GetTransactionsListCallback callback) {
        this.path = path;
        this.callback = callback;
        try {
            this.executor.run(this);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() throws CustomException {
        String errorMessage = "no listing from files ";
        try {
            List<Transaction> transactions = repository.getTransactionsList(path);
            if (transactions != null) {
                notifySuccessfullyLoaded(transactions);
            } else {
                notifyError(errorMessage);
            }
        } catch (CustomException e) {
            notifyError(errorMessage + e.getMessage());
        }
    }

    /**
     * <p>Notifies to the UI (main) thread the corresponding callback with a corresponding movie detail</p>
     */
    private void notifySuccessfullyLoaded(final List<Transaction> transactions) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetTransactionsListOK(transactions);
            }
        });
    }

    /**
     * <p>Notifies to the UI (main) thread that an error has happened</p>
     */
    private void notifyError(final String error) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetTransactionListKO(error);
            }
        });
    }
}
