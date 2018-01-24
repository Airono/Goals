package com.sanyusha.goals;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.dialogs.VKCaptchaDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class VKontakteActivity extends AppCompatActivity {

    //Token auth
    private VKAccessToken access_token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);

        //Получаем токен
        access_token = VKAccessToken.tokenFromSharedPreferences(this, "VK_ACCESS_TOKEN");

        //Инициализируем
        VKSdk.initialize(new VKSdkListener() {
            @Override
            public void onCaptchaError(VKError captchaError) {
                new VKCaptchaDialog(captchaError).show();
                Log.d("onCaptchaError");
            }

            @Override
            public void onTokenExpired(VKAccessToken expiredToken) {
                Log.d("onTokenExpired");
                VKSdk.authorize();
            }

            @Override
            public void onAccessDenied(VKError authorizationError) {
                new AlertDialog.Builder(VKontakteActivity.this).setMessage(authorizationError.errorMessage).show();
            }

            @Override
            public void onReceiveNewToken(VKAccessToken token) {
                access_token = token;
                access_token.saveTokenToSharedPreferences(VKontakteActivity.this, "VK_ACCESS_TOKEN");
                getMeInfo();
            }
        }, 6307003, access_token);

        //Сохраняем активити
        VKUIHelper.onResume(this);

        //Запускаем авторизацию
        Log.w("access_token: " + access_token);
        VKSdk.authorize();
    }

    //Получение информации о юзере
    private void getMeInfo() {

        //Подготавливаем запрос
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONObject r = response.json.getJSONArray("response").getJSONObject(0);
                    // Здесь обрабатываем полученный response.
                    Log.d("response: " + r);
                    Intent intent = new Intent();
                    intent.putExtra("provider", "vkontakte");
                    intent.putExtra("access_token", access_token.accessToken);
                    intent.putExtra("uid", r.getString("id"));
                    intent.putExtra("email", "");
                    intent.putExtra("first_name", r.getString("first_name"));
                    intent.putExtra("last_name", r.getString("last_name"));
                    setResult(RESULT_OK, intent);
                } catch( JSONException e ) {
                    Log.e(e.getMessage(), e);
                }
                finish();
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("progress: " + progressType);
            }

            @Override
            public void onError(VKError error) {
                new AlertDialog.Builder(VKontakteActivity.this).setMessage(error.errorMessage).show();
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                // Неудачная попытка. В аргументах имеется номер попытки и общее их количество.
                Log.w("attemptFailed");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(getClass().getName() + ".onActivityResult and resultCode = " + resultCode + ", requestCode = " + requestCode);
        VKUIHelper.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_CANCELED ) {
            finish();
        }
    }
}