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

/**
 * <p>Edge from the Domain layer,
 * which is composed by two vertex, one for the source, one for the destination with its related weight
 * </p>
 *
 * @author Raul Hernandez Lopez.
 */

public class EdgeDomain {
    private final VertexDomain source;
    private final VertexDomain destination;
    private final BigDecimal weight;

    public EdgeDomain(VertexDomain source, VertexDomain destination, BigDecimal weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public VertexDomain getDestination() {
        return destination;
    }

    public VertexDomain getSource() {
        return source;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }
}
