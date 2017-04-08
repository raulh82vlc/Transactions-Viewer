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

package com.raulh82vlc.TransactionsViewer.di.modules;

import com.raulh82vlc.TransactionsViewer.di.scopes.ActivityScope;
import com.raulh82vlc.TransactionsViewer.domain.interactors.GetRatesListInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.GetRatesListInteractorImpl;
import com.raulh82vlc.TransactionsViewer.domain.interactors.GetTransactionListInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.GetTransactionListInteractorImpl;
import com.raulh82vlc.TransactionsViewer.domain.interactors.SavedTransactionListInteractorImpl;
import com.raulh82vlc.TransactionsViewer.domain.interactors.SavedTransactionsListInteractor;
import com.raulh82vlc.TransactionsViewer.ui.presentation.RatesPresenter;
import com.raulh82vlc.TransactionsViewer.ui.presentation.RatesPresenterImpl;
import com.raulh82vlc.TransactionsViewer.ui.presentation.TransactionsPresenter;
import com.raulh82vlc.TransactionsViewer.ui.presentation.TransactionsPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Module which provides all user required artifacts
 * (presenter, interactors, repository, datasources or mappers)
 * in order to use them in a decoupled way
 *
 * @author Raul Hernandez Lopez
 */
@Module
public class ProductsListModule {

    @Provides
    @ActivityScope
    GetRatesListInteractor provideGetRatesListInteractor(GetRatesListInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    RatesPresenter provideRatesPresenter(RatesPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    GetTransactionListInteractor provideGetTransactionListInteractor(GetTransactionListInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    SavedTransactionsListInteractor provideSaveTransactionsListInteractor(SavedTransactionListInteractorImpl
                                                                                  interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    TransactionsPresenter provideTransactionsPresenter(TransactionsPresenterImpl presenter) {
        return presenter;
    }
}
