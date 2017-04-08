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
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionUI;

import java.util.List;

/**
 * <p>Presenter which asks a computing of rates given a list of rates as well as a list of transactions
 * and to a concrete Currency</p>
 *
 * @author Raul Hernandez Lopez.
 */

public interface ComputingTransactionsPresenter {

    void setView(View view);

    void resetView();

    void computeRates(String skuFromProduct, String toCurrency, String pathTransactions, String pathRates)
            throws CustomException;

    interface View {
        void errorComputingRates(String error);

        void computedRatesForTransactions(List<TransactionUI> transactions, String totalAmount);

        void visibilityChangesAfterSuccessfulComputedRates();

        void visibilityChangesAfterErrorComputedRates();

        boolean isReady();

        void startLoader();
    }
}
