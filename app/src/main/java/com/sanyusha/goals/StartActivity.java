package com.sanyusha.goals;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;

/**
 * Created by Alexandra on 27.01.2018.
 */

public class StartActivity extends Activity {

    private static String sTokenKey = "VK_ACCESS_TOKEN";
    private static String[] sMyScope = new String[]{VKScope.NOHTTPS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d("test", "poka");

        VKUIHelper.onCreate(this);

        VKSdkListener sdkListener = new VKSdkListener() {
            @Override
            public void onCaptchaError(VKError captchaError) {

            }

            @Override
            public void onTokenExpired(VKAccessToken expiredToken) {

            }

            @Override
            public void onAccessDenied(VKError authorizationError) {

            }

            @Override
            public void onReceiveNewToken(VKAccessToken newToken) {
                Log.d("test", newToken.userId);
            }
        };

        VKSdk.initialize(sdkListener, "6307003", VKAccessToken.tokenFromSharedPreferences(this, sTokenKey));

    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    public void authorizationButton(View v) {
        VKSdk.authorize(sMyScope, true, false);
        Log.d("test", VKSdk.getAccessToken().email);
    }

}
