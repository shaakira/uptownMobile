package com.example.uptown.CallBacks;


import com.example.uptown.DTO.Response.MessageResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomizeCallBack<T> implements Callback<T> {

    private ResponseCallBack responseCallBack;

    public CustomizeCallBack(ResponseCallBack responseCallBack) {
        this.responseCallBack = responseCallBack;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful() && response.errorBody()!=null) {
            String error = null;
            try {
                error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class).getMessageResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Customize callback not successfull "+error);
            responseCallBack.onError(error);
        } else if(response.body()!=null) {
            try {
                responseCallBack.onSuccess(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Customize callback successfull "+response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String errorMessage = t.getMessage();
        System.out.println("Customize callback failure message: " + errorMessage);
        responseCallBack.onError("Network error please try again later!" +errorMessage);
    }
}