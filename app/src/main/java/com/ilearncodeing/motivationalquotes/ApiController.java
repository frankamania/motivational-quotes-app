package com.ilearncodeing.motivationalquotes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiController {

    private static Retrofit retrofit = null;

    public static Retrofit getpojoClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }


}
