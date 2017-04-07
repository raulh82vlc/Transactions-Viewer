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

import com.raulh82vlc.TransactionsViewer.domain.interactors.GetTransactionListInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.mappers.TransactionsListModelDataMapper;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.ui.presentation.TransactionsPresenter;

import java.util.List;

/**
 * Get Transactions List Callback communicating towards its view
 *
 * @author Raul Hernandez Lopez.
 */
public class GetTransactionsListCallbackImpl implements GetTransactionListInteractor.GetTransactionsListCallback {

    private final TransactionsPresenter.View mView;
    private final TransactionsListModelDataMapper transactionsListModelDataMapper;


    public GetTransactionsListCallbackImpl(TransactionsPresenter.View view,
                                           TransactionsListModelDataMapper transactionsListModelDataMapper) {
        mView = view;
        this.transactionsListModelDataMapper = transactionsListModelDataMapper;
    }

    @Override
    public void onGetTransactionsListOK(List<Transaction> transactionList) {
        if (mView.isReady()) {
            mView.saveProducts(transactionsListModelDataMapper.transform(transactionList), transactionList);
        }
    }

    @Override
    public void onGetTransactionListKO(String error) {
        if (mView.isReady()) {
            mView.errorGettingTransactions(error);
        }
    }
}
