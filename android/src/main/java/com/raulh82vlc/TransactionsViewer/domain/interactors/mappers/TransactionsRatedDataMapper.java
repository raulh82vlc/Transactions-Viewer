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

package com.raulh82vlc.TransactionsViewer.domain.interactors.mappers;

import com.raulh82vlc.TransactionsViewer.di.scopes.ActivityScope;
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionRatedDomain;
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionUI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Transactions Rated Data Mapper to map info from Domain to UI and so on
 *
 * @author Raul Hernandez Lopez.
 */

@ActivityScope
public class TransactionsRatedDataMapper {

    @Inject
    TransactionsRatedDataMapper() {

    }

    public List<TransactionUI> transformToUI(List<TransactionRatedDomain> transactionList) {
        if (transactionList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        List<TransactionUI> transactionUIDomList = new ArrayList<>();
        for (TransactionRatedDomain transactionRated : transactionList) {
            transactionUIDomList.add(new TransactionUI(transactionRated.getCurrencyPrev(),
                    transactionRated.getCurrencyCurrent(), transactionRated.getAmounPerTransactionPrev().toString(),
                    transactionRated.getAmountPerTransactionCurrent().toString()));
        }
        return transactionUIDomList;
    }
}
