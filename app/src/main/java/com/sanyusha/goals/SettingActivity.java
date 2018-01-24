package com.sanyusha.goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;
public class SettingActivity extends AppCompatActivity {

    Button authorizationButton;
    private static String sTokenKey = "VK_ACCESS_TOKEN";
    private static String[] sMyScope = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.NOHTTPS};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_item1:
                    Intent intent = new Intent(getApplicationContext(), GoalListActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_item2:
                    intent = new Intent(getApplicationContext(), NewGoalActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_item3:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        setContentView(R.layout.activity_setting);
        VKSdk.authorize(sMyScope, true, false);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKUIHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void settingButton(View v) {
        Log.d("test", "privet");
    }
}
