package com.example.uptown.API;



import com.example.uptown.DTO.Request.AuthRequest;
import com.example.uptown.DTO.Response.AuthResponse;
import com.example.uptown.Model.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthInterface {
    @Multipart
    @POST("mobile/api/auth/signUp")
    Call<AuthResponse> Register(@Part  MultipartBody.Part image, @Part("user") User user);



    @POST("mobile/api/auth/postLogin")
    Call<AuthResponse> Login(@Body AuthRequest authRequest);


    @POST("mobile/api/auth/GooglesignUp")
    Call<AuthResponse> GoogleLogin(@Body User user);

}
