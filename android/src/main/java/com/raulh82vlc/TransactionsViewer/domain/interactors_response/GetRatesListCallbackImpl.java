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

import com.raulh82vlc.TransactionsViewer.domain.interactors.GetRatesListInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.mappers.RatesListModelDataMapper;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.ui.presentation.RatesPresenter;

import java.util.List;

/**
 * Get Rates list by means of its callback, communicating towards its view
 *
 * @author Raul Hernandez Lopez.
 */
public class GetRatesListCallbackImpl implements GetRatesListInteractor.GetRatesListCallback {

    private final RatesPresenter.View mView;
    private final RatesListModelDataMapper ratesListModelDataMapper;


    public GetRatesListCallbackImpl(RatesPresenter.View view,
                             RatesListModelDataMapper ratesListModelDataMapper) {
        mView = view;
        this.ratesListModelDataMapper = ratesListModelDataMapper;
    }

    @Override
    public void onGetRatesListOK(List<Rate> rateList) {
        if (mView.isReady()) {
            mView.loadedRates(ratesListModelDataMapper.transform(rateList));
        }
    }

    @Override
    public void onGetRatesListKO(String error) {
        if (mView.isReady()) {
            mView.errorGettingRates(error);
        }
    }
}
