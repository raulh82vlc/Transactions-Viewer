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

package com.raulh82vlc.TransactionsViewer.domain.datasources.json;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raulh82vlc.TransactionsViewer.domain.exceptions.CustomException;
import com.raulh82vlc.TransactionsViewer.domain.models.Rate;
import com.raulh82vlc.TransactionsViewer.domain.models.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Declared read operations from the JSON API interface
 *
 * @author Raul Hernandez Lopez
 */
public class JSONOperationsImpl implements JSONOperations<Rate, Transaction> {

    private static final String TAG = JSONOperationsImpl.class.getSimpleName();
    private Gson mGson;

    @Inject
    JSONOperationsImpl() {
        if (mGson == null) {
            synchronized (this) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
    }

    @Override
    public List<Rate> getRatesList(Context context, String path) throws CustomException {
        String json;
        try {
            json  = readFileAsString(context, path);
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        if (json.isEmpty()) {
            return Collections.emptyList();
        } else {
            Type listType = new TypeToken<ArrayList<Rate>>() {
            }.getType();
            return mGson.fromJson(json, listType);
        }
    }

    @Override
    public List<Transaction> getTransactionsList(Context context, String path) throws CustomException {
        String json;
        try {
            json  = readFileAsString(context, path);
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }

        if (json.isEmpty()) {
            return Collections.emptyList();
        } else {
            Type listType = new TypeToken<ArrayList<Transaction>>()
            {}.getType();
            return mGson.fromJson(json, listType);
        }
    }

    /**
     * This reads from a certain file
     * which was stored within assets folder of the project
     *
     * @param ctx Context where we are
     * @return String the final string from the JSON once parsed
     */
    private String readFileAsString(Context ctx, String path) throws IOException {
        AssetManager assetManager = ctx.getAssets();
        InputStream is = assetManager.open(path);
        String ret = convertStreamToString(is);
        return ret;
    }

    /**
     * Converts Stream to String
     *
     * @param is Input stream
     * @return String the final string from the JSON once parsed
     */
    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        try {
            appendLinesFromJSON(sb, reader);
        } catch(IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            reader.close();
        }
        Log.d(TAG, "JSON loaded.");

        return sb.toString();
    }

    private void appendLinesFromJSON(StringBuilder sb, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }
}
