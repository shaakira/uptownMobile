package com.example.uptown.Services;

import com.example.uptown.API.NotificationInterface;
import com.example.uptown.API.UserInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Notifications;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class NotificationService {
    RetrofitClient retrofitClient;
    NotificationInterface notificationInterface;

    public NotificationService() {
        //creating the connection with the API
        this.notificationInterface = retrofitClient.getRetrofitClientInstance().create(NotificationInterface.class);
    }
    public void addNotification(Notifications notifications, ResponseCallBack callBack){
        Call<ResponseBody> updateResponseCall=notificationInterface.addNotification(notifications);
        updateResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void findAllNotifications(ResponseCallBack callBack){
        Call<List<Notifications>> notificationResponseCall=notificationInterface.findAllNotifications();
        notificationResponseCall.enqueue(new CustomizeCallBack<List<Notifications>>(callBack));
    }
    public void deleteNotification(int id, ResponseCallBack callBack){
        Call<ResponseBody> deleteResponseCall=notificationInterface.deleteNotification(id);
        deleteResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }

}
