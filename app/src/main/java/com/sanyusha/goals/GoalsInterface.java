package com.sanyusha.goals;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alexandra on 02.02.2018.
 */

public interface GoalsInterface {
    @GET("/targets")
    Call<List<Goal>> getTargets(@Query("uId") String uId, @Query("token") String token);
}
