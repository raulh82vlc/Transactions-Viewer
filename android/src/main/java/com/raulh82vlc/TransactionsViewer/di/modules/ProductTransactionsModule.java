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
import com.raulh82vlc.TransactionsViewer.domain.interactors.ComputeTransactionsInteractor;
import com.raulh82vlc.TransactionsViewer.domain.interactors.ComputeTransactionsInteractorImpl;
import com.raulh82vlc.TransactionsViewer.ui.presentation.ComputingTransactionsPresenter;
import com.raulh82vlc.TransactionsViewer.ui.presentation.ComputingTransactionsPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Module with all related presenters and interactors for Product Transactions</p>
 *
 * @author Raul Hernandez Lopez
 */
@Module
public class ProductTransactionsModule {
    @Provides
    @ActivityScope
    ComputingTransactionsPresenter provideComputingTransactionsPresenter(ComputingTransactionsPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    ComputeTransactionsInteractor provideComputeTransactionsComputedInteractor(ComputeTransactionsInteractorImpl
                                                                                       interactor) {
        return interactor;
    }
}

