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
                for (Goal goal: response.body()) {
                    Log.d("test", goal.getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Goal>> call, Throwable t) {
                Log.d("test", "All is BAD");
            }
        });
    }

    public void postTargets(String id, String token, Goal goal) {
        goalsInterface.postTargets(id, token, goal.gettId(), goal.getTitle(),
                goal.getDescription(), goal.getType(), goal.getDate())
                .enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, Response<Response<String>> response) {
                Log.d("test", "success");
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable throwable) {
                Log.d("test", "post error");
            }
        });
    }
}
