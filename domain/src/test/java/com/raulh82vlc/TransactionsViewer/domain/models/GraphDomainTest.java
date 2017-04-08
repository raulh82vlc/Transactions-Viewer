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

package com.raulh82vlc.TransactionsViewer.domain.models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Graph Domain Test aims to test different scenarios direct or jumping between vertices of the graph
 *
 * @author Raul Hernandez Lopez.
 */
public class GraphDomainTest {
    private static final int ROUND_DIGITS = 2;
    public static final int NUM_VERTICES_COMPLETE_DATASET = 4;
    private GraphDomain mGraphDomain;

    @Before
    public void setup() throws Exception {
        mGraphDomain = new GraphDomain();
    }

    private void setCompleteDataSetLikeJSON() {
        mGraphDomain.addEdge(new VertexDomain("USD"), new VertexDomain("GBP"), new BigDecimal(0.77));
        mGraphDomain.addEdge(new VertexDomain("GBP"), new VertexDomain("USD"), new BigDecimal(1.3));
        mGraphDomain.addEdge(new VertexDomain("USD"), new VertexDomain("CAD"), new BigDecimal(1.09));
        mGraphDomain.addEdge(new VertexDomain("CAD"), new VertexDomain("USD"), new BigDecimal(0.92));
        mGraphDomain.addEdge(new VertexDomain("GBP"), new VertexDomain("AUD"), new BigDecimal(0.83));
        mGraphDomain.addEdge(new VertexDomain("AUD"), new VertexDomain("GBP"), new BigDecimal(1.2));
    }

    @Test
    public void graphIsEmpty() throws Exception {
        assertEquals(0, mGraphDomain.getSize());
    }

    @Test
    public void currencyForASimpleCombination() throws Exception {
        mGraphDomain.addEdge(new VertexDomain("CAN"), new VertexDomain("GBP"), new BigDecimal(0.5));
        assertEquals(1, mGraphDomain.getSize());
        BigDecimal currency = mGraphDomain.searchCurrency("CAN", "GBP");
        assertEquals(currency, new BigDecimal(0.5));
    }

    @Test
    public void currencyWith2Jumps() throws Exception {
        setCompleteDataSetLikeJSON();
        assertEquals(NUM_VERTICES_COMPLETE_DATASET, mGraphDomain.getSize());
        /**
         * CAD -> USD -> GBP
         */
        BigDecimal currency = mGraphDomain.searchCurrency("CAD", "GBP");
        assertEquals(RoundingUtil.round(new BigDecimal(0.71), ROUND_DIGITS),
                RoundingUtil.round(currency, ROUND_DIGITS));
    }

    @Test
    public void currencyWith3Jumps() throws Exception {
        setCompleteDataSetLikeJSON();
        assertEquals(NUM_VERTICES_COMPLETE_DATASET, mGraphDomain.getSize());
        /**
         * CAD -> USD -> GBP -> AUD
         */
        BigDecimal currency = mGraphDomain.searchCurrency("CAD", "AUD");
        assertEquals(RoundingUtil.round(new BigDecimal(0.59), ROUND_DIGITS),
                RoundingUtil.round(currency, ROUND_DIGITS));
    }

    @Test
    public void currencyFromAndToSame() throws Exception {
        setCompleteDataSetLikeJSON();
        assertEquals(NUM_VERTICES_COMPLETE_DATASET, mGraphDomain.getSize());
        BigDecimal currency = mGraphDomain.searchCurrency("GBP", "GBP");
        assertEquals(RoundingUtil.round(new BigDecimal(1), ROUND_DIGITS), RoundingUtil.round(currency, ROUND_DIGITS));
    }

    @Test
    public void currencyFromEmptyCurrencies() throws Exception {
        setCompleteDataSetLikeJSON();
        assertEquals(NUM_VERTICES_COMPLETE_DATASET, mGraphDomain.getSize());
        BigDecimal currency = mGraphDomain.searchCurrency("", "");
        assertEquals(RoundingUtil.round(new BigDecimal(1), ROUND_DIGITS), RoundingUtil.round(currency, ROUND_DIGITS));
    }
}
