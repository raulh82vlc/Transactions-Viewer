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

import com.raulh82vlc.TransactionsViewer.domain.models.GraphDomain;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.VertexDomain;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public class GraphMapper {
    public GraphMapper() {

    }
    public GraphDomain transformToGraph(List<Rate> rates) {
        if (rates == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        GraphDomain rateGraph = new GraphDomain();
        int size = rates.size();
        if (size > 0) {
            for (Rate rate : rates) {
                rateGraph.addEdge(new VertexDomain(rate.getFromCurrency()),
                        new VertexDomain(rate.getToCurrency()),
                        new BigDecimal(rate.getRate()));
            }
        }
        return rateGraph;
    }
}
