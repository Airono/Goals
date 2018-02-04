package com.sanyusha.goals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexandra on 02.02.2018.
 */

public class GoalsBuilder {
    static final String baseUrl = "https://magauran.herokuapp.com/";
    public static GoalsInterface getApi() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GoalsInterface client = retrofit.create(GoalsInterface.class);
        return client;
    }

}
