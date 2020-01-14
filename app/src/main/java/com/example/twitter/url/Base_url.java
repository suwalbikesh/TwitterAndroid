package com.example.twitter.url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Base_url {

    public static final String base_url = "http://10.0.2.2:1436/v1/";

    public static String token = "Bearer ";
    public static String imagePath = "http://10.0.2.2:1436/uploads/" ;

    public static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
