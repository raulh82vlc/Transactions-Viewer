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

import com.raulh82vlc.TransactionsViewer.domain.datasources.json.JSONDataSourceImpl;
import com.raulh82vlc.TransactionsViewer.domain.datasources.json.JSONOperations;
import com.raulh82vlc.TransactionsViewer.domain.datasources.json.JSONOperationsImpl;
import com.raulh82vlc.TransactionsViewer.domain.repository.DataRepository;
import com.raulh82vlc.TransactionsViewer.domain.repository.JSONRepositoryImpl;
import com.raulh82vlc.TransactionsViewer.domain.repository.datasources.JSONDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Repository module which provides the different specific implementations as
 * well as the {@link JSONDataSource} which retrieves info from the JSON files</p>
 *
 * @author Raul Hernandez Lopez
 */

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    DataRepository provideRepository(JSONRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    JSONDataSource provideJSONDataSource(JSONDataSourceImpl dataSource) {
        return dataSource;
    }

    @Provides
    @Singleton
    JSONOperations provideJSONOperations(JSONOperationsImpl jsonOperations) {
        return jsonOperations;
    }
}
