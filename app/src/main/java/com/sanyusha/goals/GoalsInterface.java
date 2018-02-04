package com.sanyusha.goals;

import java.util.List;

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
    Call<Response<String>> postTargets(@Field("uId") String id,
                                       @Field("token") String token,
                                       @Field("tId") Integer tId,
                                       @Field("title") String title,
                                       @Field("description") String description,
                                       @Field("type") String type,
                                       @Field("date") Integer date
                                       );
}
