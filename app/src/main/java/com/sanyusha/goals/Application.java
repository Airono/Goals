package com.sanyusha.goals;

/**
 * Created by Alexandra on 02.02.2018.
 */

import com.vk.sdk.VKSdk;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}