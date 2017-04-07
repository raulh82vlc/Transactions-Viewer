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
import com.raulh82vlc.TransactionsViewer.domain.interactors.ComputeTransactionsInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors_response.GetTransactionsComputedCallbackImpl;
import com.raulh82vlc.TransactionsViewer.domain.interactors.mappers.TransactionsRatedDataMapper;

import javax.inject.Inject;

/**
 * Implementation of {@link ComputingTransactionsPresenter}
 *
 * @author Raul Hernandez Lopez.
 */

public class ComputingTransactionsPresenterImpl implements ComputingTransactionsPresenter {
    private final ComputeTransactionsInteractor interactor;
    private final TransactionsRatedDataMapper transactionsRatedDataMapper;
    private View view;

    @Inject
    ComputingTransactionsPresenterImpl(ComputeTransactionsInteractor computeTransactionsInteractor,
                                       TransactionsRatedDataMapper transactionsRatedDataMapper) {
        this.interactor = computeTransactionsInteractor;
        this.transactionsRatedDataMapper = transactionsRatedDataMapper;
    }

    @Override
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The view should be instantiated");
        }
        this.view = view;
    }

    @Override
    public void resetView() {
        view = null;
    }

    @Override
    public void computeRates(String skuFromProduct, String toCurrency, String pathTransactions,
                             String pathRates) throws CustomException {
        if (view != null) {
            view.startLoader();
            startComputingRates(skuFromProduct, toCurrency, pathTransactions, pathRates);
        }
    }

    private void startComputingRates(String skuFromProduct, String toCurrency, String pathTransactions,
                                     String pathRates) throws CustomException {
        interactor.execute(skuFromProduct, new GetTransactionsComputedCallbackImpl(view, transactionsRatedDataMapper),
                toCurrency, pathTransactions, pathRates);

    }
}
