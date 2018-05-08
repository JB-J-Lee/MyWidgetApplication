package com.myjb.dev.util;

import android.app.Application;
import android.os.StrictMode;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.leakcanary.LeakCanary;

import okhttp3.OkHttpClient;

public class DebugTools {
    public static void enableStrictMode() {
        if (!Utils.aboveGingerbread()) {
            return;
        }

        StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog();

        StrictMode.VmPolicy.Builder vmPolicyBuilder =
                new StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .penaltyLog();

        if (Utils.aboveHoneycomb()) {
            threadPolicyBuilder.penaltyFlashScreen();
            //TODO
//            vmPolicyBuilder.setClassInstanceLimit(Activity.class, 1)
        }

        StrictMode.setThreadPolicy(threadPolicyBuilder.build());
        StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }

    public static void disbleStrictMode() {
        StrictMode.ThreadPolicy threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(threadPolicyBuilder);
    }

    public static void enableStetho(final Application application) {
        Stetho.initializeWithDefaults(application);

//        Stetho.initialize(Stetho.newInitializerBuilder(application)
//                .enableDumpapp(new DumperPluginsProvider() {
//                    @Override
//                    public Iterable<DumperPlugin> get() {
//                        return new Stetho.DefaultDumperPluginsBuilder(application).finish();
//                    }
//                }).enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
//                .build());
    }

    public static void enableStetho(Application application, boolean withOkHttp) {
        enableStetho(application);

        if (withOkHttp) {
            OkHttpClient stethoInterceptingClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        }
    }

    public static void enableLeakCanary(Application application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
    }
}
