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
import com.raulh82vlc.TransactionsViewer.domain.interactors.GetTransactionListInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.SavedTransactionsListInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.mappers.TransactionsListModelDataMapper;
import com.raulh82vlc.TransactionsViewer.domain.interactors_response.GetTransactionsListCallbackImpl;
import com.raulh82vlc.TransactionsViewer.domain.interactors_response.SavedTransactionsCallbackImpl;
import com.raulh82vlc.TransactionsViewer.domain.models.ProductUI;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * <p>Implementation of the {@link TransactionsPresenter}</p>
 *
 * @author Raul Hernandez Lopez
 */
public class TransactionsPresenterImpl implements TransactionsPresenter {

    private final GetTransactionListInteractor interactor;
    private final SavedTransactionsListInteractor interactorSaver;
    private final TransactionsListModelDataMapper transactionsListModelDataMapper;
    private View view;

    @Inject
    TransactionsPresenterImpl(
            GetTransactionListInteractor interactor,
            SavedTransactionsListInteractor interactorSaver,
            TransactionsListModelDataMapper transactionsListModelDataMapper) {
        this.transactionsListModelDataMapper = transactionsListModelDataMapper;
        this.interactor = interactor;
        this.interactorSaver = interactorSaver;
    }

    @Override
    public void startReading(String path) throws CustomException {
        if (view != null) {
            startGettingTransactionsListFromJSON(path);
        }
    }

    private void startGettingTransactionsListFromJSON(String path) throws CustomException {
        interactor.execute(path, new GetTransactionsListCallbackImpl(view, transactionsListModelDataMapper));
    }

    @Override
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The view should be instantiated");
        }
        this.view = view;
    }

    @Override
    public void saveProducts(List<Transaction> transactionList, Map<String, List<Transaction>> transactionsMap)
            throws CustomException {
        if (view != null) {
            List<ProductUI> productUIs = transactionsListModelDataMapper.transform(transactionsMap);
            interactorSaver.executeSaveTransactions(transactionsMap, new SavedTransactionsCallbackImpl(view));
            showProducts(productUIs);
        }
    }

    private void showProducts(List<ProductUI> productUIs) {
        if (productUIs.size() > 0) {
            view.showProductsList(productUIs);
        } else {
            view.showEmptyState();
        }
    }

    @Override
    public void resetView() {
        view = null;
    }
}
