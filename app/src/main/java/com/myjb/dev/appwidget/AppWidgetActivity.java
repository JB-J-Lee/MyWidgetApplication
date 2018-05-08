/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myjb.dev.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.myjb.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppWidgetActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    enum TRANSFER {
        INTENT,
        PREFERENCE
    }

    private final TRANSFER TRANSFER_METHOD = TRANSFER.PREFERENCE;

    @BindView(R.id.appwidget_list)
    ListView listview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_appwidget);

        ButterKnife.bind(this);

        /**
         * Comment by JB.
         *
         * android.R.layout.simple_list_item_single_choice.
         * android.R.layout.simple_list_item_multiple_choice.
         */
        listview.setAdapter(new ArrayAdapter<>(this, R.layout.item_appwidget_list, R.id.text, getResources().getStringArray(R.array.cheese)));
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(AppWidgetActivity.this, AppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        String item = ((TextView) view.findViewById(R.id.text)).getText().toString();

        /**
         * Comment by JB.
         *
         * Update data using SharedPreferences rather than Intent.
         * If you pass it to memory, you can not cope with the problem of booting.
         */
        if (TRANSFER_METHOD == TRANSFER.INTENT) {
            intent.putExtra(AppWidget.EXTRA, item);

            /**
             * It is necessary to update AppWidget with Intent.
             * Preferences are not required.
             */
            int[] widgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, AppWidget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
        } else {
            SharedPreferences.Editor prefEditor = getSharedPreferences(AppWidget.APPWIDGET, MODE_PRIVATE).edit();
            prefEditor.putString(AppWidget.EXTRA, item);
            prefEditor.apply();
        }

        sendBroadcast(intent);
        finish();
    }
}