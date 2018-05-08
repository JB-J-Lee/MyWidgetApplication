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

package com.myjb.dev.debug;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mattprecious.telescope.EmailDeviceInfoLens;
import com.mattprecious.telescope.EmailLens;
import com.mattprecious.telescope.TelescopeLayout;
import com.myjb.dev.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.actions.ActionsModule;
import io.palaima.debugdrawer.actions.ButtonAction;
import io.palaima.debugdrawer.actions.SpinnerAction;
import io.palaima.debugdrawer.actions.SwitchAction;
import io.palaima.debugdrawer.commons.BuildModule;
import io.palaima.debugdrawer.commons.DeviceModule;
import io.palaima.debugdrawer.commons.NetworkModule;
import io.palaima.debugdrawer.commons.SettingsModule;
import io.palaima.debugdrawer.fps.FpsModule;
import io.palaima.debugdrawer.logs.LogsModule;
import io.palaima.debugdrawer.network.quality.NetworkQualityModule;
import io.palaima.debugdrawer.scalpel.ScalpelModule;
import io.palaima.debugdrawer.timber.TimberModule;
import jp.wasabeef.takt.Seat;
import jp.wasabeef.takt.Takt;

public class DebugActivity extends AppCompatActivity {

    private DebugDrawer debugDrawer;

    @BindView(R.id.telescope)
    TelescopeLayout telescopeView;

    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_debug);

        ButterKnife.bind(this);

        enableDebugDrawable();
        enableTelescope();
    }

    private void enableDebugDrawable() {
        SwitchAction switchAction = new SwitchAction("Test switch", new SwitchAction.Listener() {
            @Override
            public void onCheckedChanged(boolean value) {
                Toast.makeText(DebugActivity.this, "Switch checked", Toast.LENGTH_LONG).show();
            }
        });

        ButtonAction buttonAction = new ButtonAction("Test button", new ButtonAction.Listener() {
            @Override
            public void onClick() {
                Toast.makeText(DebugActivity.this, "Button clicked", Toast.LENGTH_LONG).show();
            }
        });

        SpinnerAction<String> spinnerAction = new SpinnerAction<>(
                Arrays.asList("First", "Second", "Third"),
                new SpinnerAction.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(String value) {
                        Toast.makeText(DebugActivity.this, "Spinner item selected - " + value, Toast.LENGTH_LONG).show();
                    }
                }
        );

        debugDrawer = new DebugDrawer.Builder(this).
                modules(new ActionsModule(switchAction, buttonAction, spinnerAction)
                        , new FpsModule(Takt.stock(getApplication()).seat(Seat.CENTER))
                        , new LogsModule()
                        , new BuildModule()
                        , new DeviceModule()
                        , new NetworkModule()
                        , new NetworkQualityModule(this)
                        , new ScalpelModule(this)
                        , new TimberModule()
                        //4.4 Crash Issue
//                        , new SettingsModule()
                ).build();
    }

    private void enableTelescope() {
        TelescopeLayout.cleanUp(this); // Clean up any old screenshots.

        telescopeView.setLens(new EmailDeviceInfoLens(this, "Help me", "jblee@infrawareglobal.com"));
        telescopeView.setPointerCount(3);
    }
}
