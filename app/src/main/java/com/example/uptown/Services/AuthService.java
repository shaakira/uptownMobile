package com.example.uptown.Services;



import com.example.uptown.API.AuthInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.AuthRequest;
import com.example.uptown.DTO.Response.AuthResponse;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class AuthService {
    AuthInterface authInterface;
    RetrofitClient retrofitClient;

    public  AuthService(){
        this.authInterface=retrofitClient.getRetrofitClientInstance().create(AuthInterface.class);
    }

    public void Login(AuthRequest authRequest, ResponseCallBack callback){
        Call<AuthResponse> loginResponseCall = authInterface.Login(authRequest);
        loginResponseCall.enqueue(new CustomizeCallBack<AuthResponse>(callback));
    }

    public void Register(MultipartBody.Part image, User user, ResponseCallBack callback){
        Call<ResponseBody> RegisterResponseCall = authInterface.Register(image,user);
        RegisterResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callback));
    }

}
