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
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionRatedDomain;

import java.util.List;

/**
 * Interactor which Computes Transactions to its different currency
 *
 * @author Raul Hernandez Lopez.
 */

public interface ComputeTransactionsInteractor {

    void execute(String skuFromProduct,
                 GetTransactionsComputedCallback getTransactionsComputedCallback,
                 String toCurrency,
                 String pathTransactions, String pathRates) throws CustomException;


    interface GetTransactionsComputedCallback {
        void onGetTransactionsListOK(List<TransactionRatedDomain> transactionList, String totalAmount);

        void onGetTransactionListKO(String error);
    }
}
