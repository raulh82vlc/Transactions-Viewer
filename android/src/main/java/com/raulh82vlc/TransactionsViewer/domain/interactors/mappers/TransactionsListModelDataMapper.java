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
import com.raulh82vlc.TransactionsViewer.domain.models.ProductUI;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

/**
 * Mapper conversion from Movie model of the API to Movie model of the UI
 *
 * @author Raul Hernandez Lopez
 */
@ActivityScope
public class TransactionsListModelDataMapper {

    private static final String GBP = "GBP";

    @Inject
    TransactionsListModelDataMapper() {

    }

    /**
     * Transforms a List {@link Transaction} into an TreeMap of {@link Transaction}
     * to maintain the order from A to Z sorted.
     */
    public TreeMap<String, List<Transaction>> transform(List<Transaction> transactionList) {
        if (transactionList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        TreeMap<String, List<Transaction>> map = new TreeMap<>();
        for (Transaction transaction : transactionList) {
            String key = transaction.getSkuIdentifier();
            if (map.containsKey(key)) {
                map.get(key).add(transaction);
            } else {
                List<Transaction> transactions = new ArrayList<>();
                transactions.add(transaction);
                map.put(key, transactions);
            }
        }
        return map;
    }

    public List<ProductUI> transform(Map<String, List<Transaction>> transactionsDictionary) {
        if (transactionsDictionary == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ProductUI productUI;
        List<ProductUI> productUIs = new ArrayList<>();
        for (Map.Entry<String, List<Transaction>> entry : transactionsDictionary.entrySet()) {
            productUI = new ProductUI(entry.getKey(), entry.getValue(), GBP);
            productUIs.add(productUI);
        }
        return productUIs;
    }
}
