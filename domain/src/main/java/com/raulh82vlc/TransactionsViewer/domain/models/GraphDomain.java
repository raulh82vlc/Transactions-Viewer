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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * <p><Graph from the domain composed by a dictionary of Vertex with its adjacent Edges
 * Contains BSF algorithm to find the corresponding currency</p>
 *
 * @author Raul Hernandez Lopez.
 */
public class GraphDomain {
    /*
     * limit which indicates there is no simple combination like USD to GBP without
     * jumping to other currencies
     */
    private static final int NO_DIRECT_COMBINATION = 2;
    // Dictionary of vertices with its corresponding adjacent edges
    private Map<VertexDomain, List<EdgeDomain>> rateGraph;

    /**
     * Constructor which sets the Graph of Rates
     */
    public GraphDomain() {
        rateGraph = new HashMap<>();
    }

    /**
     * Adds and edge to the graph
     *
     * @param from   from {@link VertexDomain}
     * @param to     to {@link VertexDomain}
     * @param weight at the weight is the currency between verteces
     */
    public void addEdge(VertexDomain from, VertexDomain to, BigDecimal weight) {
        if (rateGraph.containsKey(from)) {
            addEdgeToExistingVertex(from, to, weight);
        } else {
            addNewEdgeList(from, to, weight);
        }
    }

    private void addEdgeToExistingVertex(VertexDomain from, VertexDomain to, BigDecimal weight) {
        rateGraph.get(from).add(new EdgeDomain(from, to, weight));
    }

    private void addNewEdgeList(VertexDomain from, VertexDomain to, BigDecimal weight) {
        List<EdgeDomain> edgeList = new ArrayList<>();
        edgeList.add(new EdgeDomain(from, to, weight));
        addEdgesList(from, edgeList);
    }

    private void addEdgesList(VertexDomain vertexFrom, List<EdgeDomain> connectedVertices) {
        rateGraph.put(vertexFrom, connectedVertices);
    }

    public int getSize() {
        return rateGraph.size();
    }

    /**
     * BSF (Breadth Search First) algorithm which uses a queue
     *
     * @param currencyFrom currency from where to start to look for a currency
     * @param currencyTo   currency to where we would like to achieve
     * @return big precision decimal with final concurrency
     */
    public BigDecimal searchCurrency(String currencyFrom, String currencyTo) {
        BigDecimal weight = new BigDecimal(1);
        if (currencyFrom.equals(currencyTo)) {
            return weight;
        }
        VertexDomain vertexDomain = new VertexDomain(currencyFrom);
        if (rateGraph.containsKey(vertexDomain)) {
            Queue<VertexDomain> queue = new LinkedList<>();
            queue.add(vertexDomain);
            // Marking visited ones is the way of following up the currencies of interest
            Map<String, BigDecimal> visited = new HashMap<>();
            while (!queue.isEmpty()) {
                // gets the first element of the queue and removes / uses it
                VertexDomain currentVertexDomain = queue.remove();
                // if current vertex is the one we are looking for, we stop
                if (currentVertexDomain.equals(new VertexDomain(currencyTo))) {
                    // we check how many visited vertex and we determine if there is
                    // a direct combination = USD - GBP without intermediate jumps is a direct combination
                    if (visited.size() > NO_DIRECT_COMBINATION) {
                        return calculateIntermediateCurrenciesValues(visited);
                    } else {
                        return visited.get(currencyTo);
                    }
                }
                List<EdgeDomain> edgeDomains = rateGraph.get(currentVertexDomain);
                for (EdgeDomain edgeDomain : edgeDomains) {
                    VertexDomain adjacentVertexDomain = edgeDomain.getDestination();
                    String adjacentVertexId = adjacentVertexDomain.getId();
                    if (!visited.containsKey(adjacentVertexId)) {
                        visited.put(adjacentVertexId, edgeDomain.getWeight());
                        queue.add(adjacentVertexDomain);
                    }
                }
            }
        }
        return weight;
    }

    /**
     * <p>Calculates necessary intermediate values to determine the final currency amount
     * Avoids the very last visited one, since we were working with a queue, the first ones
     * but the last one are the ones we need</p>
     *
     * @param visited Map of visited nodes, having as key its Currency identifier
     * @return BigDecimal with final currency from calculations
     */
    private BigDecimal calculateIntermediateCurrenciesValues(Map<String, BigDecimal> visited) {
        BigDecimal currencyAmount = new BigDecimal(1f);
        int size = visited.size(), i = 0;
        for (BigDecimal value : visited.values()) {
            if (i == size - 1) {
                return currencyAmount;
            }
            currencyAmount = currencyAmount.multiply(value);
            ++i;
        }
        return currencyAmount;
    }
}
