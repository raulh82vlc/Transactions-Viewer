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

package com.raulh82vlc.TransactionsViewer.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.raulh82vlc.TransactionsViewer.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Base Activity for all Activities
 *
 * @author Raul Hernandez Lopez
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Optional
    @InjectView(R.id.toolbar)
    protected Toolbar mToolbar;
    private ProgressDialog mDialog;

    protected abstract void setInitialTitle();

    protected abstract void setToolbarInitialisation();

    protected abstract int getLayoutId();

    protected abstract Activity getActivity();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(getActivity());
    }

    /**
     * Shows {@link android.app.ProgressDialog}
     */
    public void showLoaderWithTitleAndDescription(String tittleMessage, String descriptionMessage) {
        if (!isFinishing()) {
            if (mDialog == null) {
                mDialog = ProgressDialog.show(this, tittleMessage, descriptionMessage, true);
            } else {
                mDialog.show();
            }
        }
    }

    /**
     * Hides {@link android.app.ProgressDialog}
     */
    public void hideLoader() {
        if (mDialog != null
                && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // SET TOOLBAR
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}
