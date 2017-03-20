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
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.RateUI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * As a Mapper, means it is a converter between different domains
 *
 * @author Raul Hernandez Lopez
 */
@ActivityScope
public class RatesListModelDataMapper {

    @Inject
    RatesListModelDataMapper() {

    }

    /**
     * Transforms a List of {@link Rate} into a List of Rates
     *
     * @param ratesList to be transformed.
     * @return Graph composed by a dictionary of rate to others
     */
    public List<RateUI> transform(List<Rate> ratesList) {
        if (ratesList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        int size = ratesList.size();
        List<RateUI> listOfRates = new ArrayList<>(size);
        if (size > 0) {
            listOfRates = new ArrayList<>(size);
            for (Rate rate : ratesList) {
                listOfRates.add(new RateUI(rate.getFromCurrency(), rate.getToCurrency(), rate.getRate()));
            }
        }
        return listOfRates;
    }
}
