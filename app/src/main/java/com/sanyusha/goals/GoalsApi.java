package com.sanyusha.goals;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandra on 02.02.2018.
 */

public class GoalsApi {
    private static GoalsInterface goalsInterface;

    public GoalsApi() {
        goalsInterface = GoalsBuilder.getApi();
    }

    public void getTargets(String id, String token) {
        goalsInterface.getTargets(id, token).enqueue(new Callback<List<Goal>>() {
            @Override
            public void onResponse(Call<List<Goal>> call, Response<List<Goal>> response) {
                Log.d("test", "All is OK");
            }

            @Override
            public void onFailure(Call<List<Goal>> call, Throwable t) {
                Log.d("test", "All is BAD");
            }
        });
    }
}
