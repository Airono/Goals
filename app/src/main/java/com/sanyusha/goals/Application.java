package com.sanyusha.goals;

/**
 * Created by Alexandra on 02.02.2018.
 */

import android.content.Context;

import com.vk.sdk.VKSdk;

public class Application extends android.app.Application {

    private static Context context;

    public static Context getAppContext() {
        return Application.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        Application.context = getApplicationContext();
    }
}