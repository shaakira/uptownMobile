package com.example.uptown.CallBacks;

import java.io.IOException;

import retrofit2.Response;

public interface ResponseCallBack {
        void onSuccess(Response response) throws IOException;
        void onError(String errorMessage);
}
