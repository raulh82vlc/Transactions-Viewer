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

package com.raulh82vlc.TransactionsViewer.domain.interactors_response;

import com.raulh82vlc.TransactionsViewer.domain.interactors.ComputeTransactionsInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.mappers.TransactionsRatedDataMapper;
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionRatedDomain;
import com.raulh82vlc.TransactionsViewer.ui.presentation.ComputingTransactionsPresenter;

import java.util.List;

/**
 * Get Transactions list by means of its callback, communicating towards its view
 *
 * @author Raul Hernandez Lopez.
 */

public class GetTransactionsComputedCallbackImpl implements
        ComputeTransactionsInteractor.GetTransactionsComputedCallback {

    private final ComputingTransactionsPresenter.View mView;
    private final TransactionsRatedDataMapper transactionsRatedDataMapper;

    public GetTransactionsComputedCallbackImpl(ComputingTransactionsPresenter.View  view,
                                     TransactionsRatedDataMapper transactionsRatedDataMapper) {
        mView = view;
        this.transactionsRatedDataMapper = transactionsRatedDataMapper;
    }

    @Override
    public void onGetTransactionsListOK(List<TransactionRatedDomain> transactionList, String totalAmount) {
        if (mView.isReady()) {
            mView.computedRatesForTransactions(transactionsRatedDataMapper.transformToUI(transactionList), totalAmount);
            mView.visibilityChangesAfterSuccessfulComputedRates();
        }
    }

    @Override
    public void onGetTransactionListKO(String error) {
        if (mView.isReady()) {
            mView.errorComputingRates(error);
            mView.visibilityChangesAfterErrorComputedRates();
        }
    }
}
