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
 * Implementation of the Get Rates List Interactor
 *
 * @author Raul Hernandez Lopez
 */
public class GetRatesListInteractorImpl implements GetRatesListInteractor, Interactor {

    private InteractorExecutor executor;
    private MainThread mainThread;
    private DataRepository<Rate, Transaction> repository;
    private GetRatesListCallback callback;
    private String path;

    @Inject
    GetRatesListInteractorImpl(InteractorExecutor executor,
                               MainThread mainThread,
                               DataRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void execute(String path, GetRatesListCallback callback) throws CustomException {
        this.path = path;
        this.callback = callback;
        this.executor.run(this);
    }

    @Override
    public void run() throws CustomException {
        String errorMessage = "unable to read from file: ";
        try {
            List<Rate> ratesList = repository.getRatesList(path);
            if (ratesList != null) {
                notifySuccessfullyLoaded(ratesList);
            } else {
                notifyError(errorMessage);
            }
        } catch (CustomException e) {
            notifyError(errorMessage + e.getMessage());
        }
    }

    /**
     * <p>Notifies to the UI (main) thread the result of the request,
     * and sends a callback with a list</p>
     */
    private void notifySuccessfullyLoaded(final List<Rate> rateList) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetRatesListOK(rateList);
            }
        });
    }

    /**
     * <p>Notifies to the UI (main) thread that an error happened</p>
     */
    private void notifyError(final String error) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetRatesListKO(error);
            }
        });
    }
}
