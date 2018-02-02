package com.sanyusha.goals;

import retrofit2.Retrofit;

/**
 * Created by Alexandra on 02.02.2018.
 */

public class GoalsBuilder {
    static final String baseUrl = "https://magauran.herokuapp.com/";
    public static GoalsInterface getApi() {

        //здесь нужно прописать gson

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();

        GoalsInterface client = retrofit.create(GoalsInterface.class);
        return client;
    }

}
