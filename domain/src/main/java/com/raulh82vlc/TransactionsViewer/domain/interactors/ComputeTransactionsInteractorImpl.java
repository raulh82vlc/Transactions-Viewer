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
import com.raulh82vlc.TransactionsViewer.domain.interactors.mappers.GraphMapper;
import com.raulh82vlc.TransactionsViewer.domain.models.GraphDomain;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.RoundingUtil;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.domain.models.TransactionRatedDomain;
import com.raulh82vlc.TransactionsViewer.domain.repository.DataRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Raul Hernandez Lopez.
 */

public class ComputeTransactionsInteractorImpl implements ComputeTransactionsInteractor, Interactor {

    private static final int ROUND_TO = 2;
    private InteractorExecutor executor;
    private MainThread mainThread;
    private GetTransactionsComputedCallback callback;
    private String mToCurrency;
    private DataRepository<Rate, Transaction> repository;
    private String mPathTransactions;
    private String mPathRates;
    private String mSku;

    @Inject
    ComputeTransactionsInteractorImpl(InteractorExecutor executor,
                               MainThread mainThread,
                               DataRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void execute(String skuFromProduct, GetTransactionsComputedCallback getTransactionsComputedCallback,
                        String toCurrency, String pathTransactions, String pathRates) throws CustomException {
        callback = getTransactionsComputedCallback;
        mSku = skuFromProduct;
        mToCurrency = toCurrency;
        mPathTransactions = pathTransactions;
        mPathRates = pathRates;
        this.executor.run(this);
    }

    @Override
    public void run() throws CustomException {
        String errorMessage = "No transactions were processed.";
        try {
            List<Rate> rates = repository.getRatesList(mPathRates);
            List<Transaction> transactionsList = repository.getTransactionsPerSku(mPathTransactions, mSku);
            if (transactionsList.size() > 0) {
                GraphDomain graphDomain = null;
                if (rates.size() > 0) {
                    graphDomain = new GraphMapper().transformToGraph(rates);
                }
                List<TransactionRatedDomain> transactionRatedDomain = new ArrayList<>(transactionsList.size());
                BigDecimal totalAmountNumeric = computeTransactionIntoRightCurrencyRate(
                        transactionsList, mToCurrency, graphDomain, transactionRatedDomain);
                if (transactionRatedDomain.size() > 0) {
                    notifySuccessfullyComputed(transactionRatedDomain, totalAmountNumeric.toString());
                } else {
                    notifyError(errorMessage);
                }
            } else {
                notifyError(errorMessage);
            }
        } catch (CustomException e) {
            errorMessage = "Problem reading this file: " + e.getMessage();
            notifyError(errorMessage);
        }
    }

    protected BigDecimal computeTransactionIntoRightCurrencyRate(List<Transaction> transactions,
                                                                 String toCurrency, GraphDomain graphDomain,
                                                                 List<TransactionRatedDomain> transactionRatedDomainList) throws CustomException {
        BigDecimal totalAmount = new BigDecimal(0F);
        for (Transaction transaction : transactions) {
            BigDecimal currency = new BigDecimal(1F);
            if (graphDomain != null) {
                currency = graphDomain.searchCurrency(transaction.getCurrency(), toCurrency);
            }
            BigDecimal amountSpentInitially = new BigDecimal(transaction.getAmountPerTransaction());
            TransactionRatedDomain transactionRatedDomain = new TransactionRatedDomain(transaction.getCurrency(), mToCurrency,
                    RoundingUtil.round(amountSpentInitially, ROUND_TO), RoundingUtil.round(amountSpentInitially.multiply(currency), ROUND_TO));
            transactionRatedDomainList.add(transactionRatedDomain);
            totalAmount = totalAmount.add(transactionRatedDomain.getAmountPerTransactionCurrent());
        }
        return RoundingUtil.round(totalAmount, ROUND_TO);
    }

    /**
     * <p>Notifies to the UI (main) thread the corresponding callback</p>
     */
    private void notifySuccessfullyComputed(final List<TransactionRatedDomain> transactions, final String totalAmount) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetTransactionsListOK(transactions, totalAmount);
            }
        });
    }

    /**
     * <p>Notifies to the UI (main) thread that an error has happened</p>
     */
    private void notifyError(final String errorMessage) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetTransactionListKO(errorMessage);
            }
        });
    }
}
