package com.ilearncodeing.motivationalquotes;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Httprequest {

    @GET
    Call<Categorys> getcategorys(@Url String url);

    @GET
    Call<Quotes> getquotes(@Url String url);

}

