package com.sanyusha.goals.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sanyusha.goals.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import static android.content.ContentValues.TAG;

/**
 * Created by Alexandra on 27.01.2018.
 */

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "test";
    String preferences_name = "isFirstTime";
    private static String[] sMyScope = new String[]{VKScope.GROUPS};
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firstTime();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                Intent intent = new Intent(getApplicationContext(), GoalListActivity.class);
                startActivity(intent);

                // User passed Authorization
                Log.d("test", res.accessToken);
                Log.d("test", res.userId);

                //sPref = getPreferences(MODE_PRIVATE);
                sPref = getSharedPreferences("vk", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("access_token", res.accessToken);
                ed.putString("user_id", res.userId);
                ed.apply();

            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void authorizationButton(View v) {
        VKSdk.login(this, sMyScope);
    }

    public  void  firstTime(){

        SharedPreferences sharedTime = getSharedPreferences(preferences_name,0);
        if (sharedTime.getBoolean("firstTime",true))
        {
            Log.d(TAG, "onCreate: in StartActivity");
            sharedTime.edit().putBoolean("firstTime",false).apply();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), GoalListActivity.class);
            startActivity(intent);
        }

    }

}