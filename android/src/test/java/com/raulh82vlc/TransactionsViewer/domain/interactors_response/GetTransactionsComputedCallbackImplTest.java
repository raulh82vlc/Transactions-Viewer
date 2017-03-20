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
import com.raulh82vlc.TransactionsViewer.domain.interactors_response.GetTransactionsComputedCallbackImpl;
import com.raulh82vlc.TransactionsViewer.ui.presentation.ComputingTransactionsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
 * <p>GetTransactionsComputedCallbackImpl mock to verify interacts well with the listener</p>
 *
 * @author Raul Hernandez Lopez.
 */

public class GetTransactionsComputedCallbackImplTest {
    private final String amount = "2222";
    private final String error = "error";

    @Mock
    private ComputingTransactionsPresenter.View view;
    @Mock
    private TransactionsRatedDataMapper mapper;
    @Mock
    private List<TransactionRatedDomain> list;

    private ComputeTransactionsInteractor.GetTransactionsComputedCallback callbackToTest;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        callbackToTest = spy(new GetTransactionsComputedCallbackImpl(view, mapper));
    }

    @Test
    public void callbackIsWorkingForOK () throws Exception {
        callbackToTest.onGetTransactionsListOK(list, amount);
        verify(view).computedRatesForTransactions(anyList(), anyString());
        verify(mapper).transformToUI(anyList());
    }

    @Test
    public void callbackIsWorkingForKO () throws Exception {
        callbackToTest.onGetTransactionListKO(error);
        verify(view).errorComputingRates(anyString());
    }
}
