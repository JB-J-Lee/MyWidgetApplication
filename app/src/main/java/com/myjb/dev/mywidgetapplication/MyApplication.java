package com.myjb.dev.mywidgetapplication;

import android.app.Application;

import com.myjb.dev.BuildConfig;
import com.myjb.dev.util.DebugTools;

import io.palaima.debugdrawer.timber.data.LumberYard;
import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            DebugTools.enableStrictMode();
            DebugTools.enableStetho(this);
            DebugTools.enableLeakCanary(this);
        }

        LumberYard lumberYard = LumberYard.getInstance(this);
        lumberYard.cleanUp();

        Timber.plant(lumberYard.tree());
        Timber.plant(new Timber.DebugTree());
    }
}
