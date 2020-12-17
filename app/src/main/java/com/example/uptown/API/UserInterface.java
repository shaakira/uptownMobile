package com.example.uptown.API;

import com.example.uptown.DTO.Request.PasswordDTO;
import com.example.uptown.DTO.Response.AdminDashboardDTO;
import com.example.uptown.DTO.Response.ClientCountDTO;
import com.example.uptown.DTO.Response.UserDTO;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserInterface {
    @GET("mobile/api/user/profile/{id}")
    Call<User> getProfile(@Path("id") int id);

    @POST("mobile/api/user/updateUser/{id}")
    Call<ResponseBody> updateUser(@Path("id") int id, @Body User user);

    @GET("mobile/api/user/agents")
    Call<List<User>> getAgents();

    @GET("mobile/api/user/agentSingle/{id}")
    Call<List<Property>> getSingleAgent(@Path("id") int id);

    @GET("mobile/api/user/logout")
    Call<ResponseBody> logout();

    @GET("mobile/api/user/deleteUser/{userId}")
    Call<ResponseBody> deleteUser(@Path("userId") int userId);

    @POST("mobile/api/user/changePassword/{userId}")
    Call<ResponseBody> changePassword(@Body PasswordDTO passwordDTO, @Path("userId") int userId);

    @GET("mobile/api/user/clients/active")
    Call<List<User>> getActiveUsers();

    @GET("mobile/api/user/clients/blacklisted")
    Call<List<User>> getBlacklistedUsers();

    @GET("mobile/api/user/clients/blacklistUser/{userId}")
    Call<ResponseBody> blacklistUser(@Path("userId") int userId);

    @GET("mobile/api/user/clients/activateUser/{userId}")
    Call<ResponseBody> activateUser(@Path("userId") int userId);

    @GET("mobile/api/user/clients")
    Call<ClientCountDTO> getClientsCount();

    @GET("mobile/api/user/admin/dashboard")
    Call<AdminDashboardDTO> getDashboard();
}
