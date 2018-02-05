package com.sanyusha.goals;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Alexandra on 02.02.2018.
 */

public interface GoalsInterface {
    @GET("/targets")
    Call<List<Goal>> getTargets(@Query("uId") String uId, @Query("token") String token);

    @POST("/target/new")
    Call<ResponseBody> postTargets(@Query("uId") String id,
                                   @Query("token") String token,
                                   @Query("tId") Integer tId,
                                   @Query("title") String title,
                                   @Query("description") String description,
                                   @Query("type") String type,
                                   @Query("date") Integer date
                                       );
}
