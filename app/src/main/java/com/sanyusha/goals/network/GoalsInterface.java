package com.sanyusha.goals.network;

import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Alexandra on 02.02.2018.
 */

public interface GoalsInterface {
    @GET("/targets")
    Call<ArrayList<Goal>> getTargets(@Query("uId") String uId, @Query("token") String token);

    @POST("/target/new")
    Call<ResponseBody> postTargets(@Query("uId") String id,
                                   @Query("token") String token,
                                   @Query("tId") Integer tId,
                                   @Query("title") String title,
                                   @Query("description") String description,
                                   @Query("type") String type,
                                   @Query("date") long date
                                       );
}
