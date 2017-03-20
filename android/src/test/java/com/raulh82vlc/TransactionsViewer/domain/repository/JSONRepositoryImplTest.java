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

package com.raulh82vlc.TransactionsViewer.domain.repository;

import com.raulh82vlc.TransactionsViewer.domain.datasources.json.JSONDataSourceImpl;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;
import com.raulh82vlc.TransactionsViewer.domain.repository.JSONRepositoryImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
 * <p>GetTransactionsComputedCallbackImpl mock to verify interacts well with the listener</p>
 *
 * @author Raul Hernandez Lopez.
 */

public class JSONRepositoryImplTest {
    private static final String MY_PATH = "rates010.json";

    @Mock
    private JSONDataSourceImpl dataSource;
    private JSONRepositoryImpl repoUnderTest;
    private List<Rate> rates = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private String SKU = "AAAA1";

    @Before
    public void setup() throws Exception {
        initMocks(this);
        repoUnderTest = spy(new JSONRepositoryImpl(dataSource));
        when(repoUnderTest.getRatesList(MY_PATH)).thenReturn(rates);
        when(repoUnderTest.getTransactionsPerSku(MY_PATH, SKU)).thenReturn(transactions);
    }

    @After
    public void tearDown() {
        repoUnderTest = null;
    }

    @Test
    public void getRatesList () throws Exception {
        rates.add(mock(Rate.class));
        rates.add(mock(Rate.class));
        assertEquals(2, repoUnderTest.getRatesList(MY_PATH).size());
    }

    @Test
    public void getTransactionsPerSku() throws Exception {
        transactions.add(mock(Transaction.class));
        transactions.add(mock(Transaction.class));
        transactions.add(mock(Transaction.class));
        assertEquals(3, repoUnderTest.getTransactionsPerSku(MY_PATH, SKU).size());
    }
}
