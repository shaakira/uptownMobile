package com.example.uptown.API;

import com.example.uptown.Model.Enquiry;
import com.example.uptown.Model.Notifications;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationInterface {
    @POST("mobile/api/notification/addNotification")
    Call<ResponseBody> addNotification( @Body Notifications notifications);

    @GET("mobile/api/notification/notificationList")
    Call<List<Notifications>> findAllNotifications();

    @GET("mobile/api/notification/deleteNotification/{id}")
    Call<ResponseBody> deleteNotification(@Path("id") int id);
}
