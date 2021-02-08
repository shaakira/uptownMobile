package com.example.uptown.RetrofitClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "http://192.168.1.7:8080/";
    private static Retrofit retrofit;

    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
//Singleton Design Pattern
    public static Retrofit getRetrofitClientInstance() {
        if (retrofit == null) {
            new RetrofitClient();
        }
        return retrofit;
    }

    public static String Url(){
        return BASE_URL;
    }

}
