package com.sanyusha.goals.network;

import com.sanyusha.goals.models.Goal;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Alexandra on 02.02.2018.
 */

public interface GoalsInterface {

    @GET("/targets")
    Call<ArrayList<Goal>> getTargets(@Query("uId") String uId,
                                     @Query("token") String token);

    @POST("/target/new")
    Call<ResponseBody> postTargets(@Query("uId") String id,
                                   @Query("token") String token,
                                   @Query("title") String title,
                                   @Query("description") String description,
                                   @Query("type") int type,
                                   @Query("date") long date
    );

    @DELETE("/target")
    Call<ResponseBody> deleteTarget(@Query("uId") String uId,
                                    @Query("tId") String tId,
                                    @Query("token") String token
    );

    @PUT("/target/archive")
    Call<ResponseBody> moveToArchive(@Query("uId") String uId,
                                     @Query("tId") String tId,
                                     @Query("token") String token
    );

    @PUT("/archive/target")
    Call<ResponseBody> moveFromArchive(@Query("uId") String uId,
                                       @Query("tId") String tId,
                                       @Query("token") String token
    );


    @GET("/archive")
    Call<ArrayList<Goal>> getArchiveTargets(@Query("uId") String uId,
                                            @Query("token") String token
    );

}

