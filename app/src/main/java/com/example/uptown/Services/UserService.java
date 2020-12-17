package com.example.uptown.Services;

import com.example.uptown.API.AuthInterface;
import com.example.uptown.API.UserInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.AuthRequest;
import com.example.uptown.DTO.Request.PasswordDTO;
import com.example.uptown.DTO.Response.AdminDashboardDTO;
import com.example.uptown.DTO.Response.AuthResponse;
import com.example.uptown.DTO.Response.ClientCountDTO;
import com.example.uptown.DTO.Response.UserDTO;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserService {
    RetrofitClient retrofitClient;
    UserInterface userInterface;

    public UserService() {
        //creating the connection with the API
        this.userInterface=retrofitClient.getRetrofitClientInstance().create(UserInterface.class);
    }

    public void GetProfile(int id, ResponseCallBack callback){
        Call<User> profileResponseCall = userInterface.getProfile(id);
        profileResponseCall.enqueue(new CustomizeCallBack<User>(callback));
    }
    public void updateUser(User user,int id, ResponseCallBack callBack){
        Call<ResponseBody> updateResponseCall=userInterface.updateUser(id,user);
        updateResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void getAgents( ResponseCallBack callback){
        Call<List<User>> userResponseCall = userInterface.getAgents();
        userResponseCall.enqueue(new CustomizeCallBack<List<User>>(callback));
    }
    public void getSingleAgent(int id, ResponseCallBack callback){
        Call<List<Property>> profileResponseCall = userInterface.getSingleAgent(id);
        profileResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callback));
    }
    public void logout(ResponseCallBack callBack){
        Call<ResponseBody> logoutResponseCall=userInterface.logout();
        logoutResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void deleteUser(int userId,ResponseCallBack callBack){
        Call<ResponseBody> deleteResponseCall=userInterface.deleteUser(userId);
        deleteResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void changePassword(PasswordDTO passwordDTO, int userId, ResponseCallBack callBack){
        Call<ResponseBody> changePassword=userInterface.changePassword(passwordDTO,userId);
        changePassword.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void getActiveUsers( ResponseCallBack callback){
        Call<List<User>> userResponseCall = userInterface.getActiveUsers();
        userResponseCall.enqueue(new CustomizeCallBack<List<User>>(callback));
    }
    public void getBlacklistedUsers( ResponseCallBack callback){
        Call<List<User>> userResponseCall = userInterface.getBlacklistedUsers();
        userResponseCall.enqueue(new CustomizeCallBack<List<User>>(callback));
    }
    public void blacklistUser(int userId,ResponseCallBack callBack){
        Call<ResponseBody> userResponseCall=userInterface.blacklistUser(userId);
        userResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void ActivateUser(int userId,ResponseCallBack callBack){
        Call<ResponseBody> userResponseCall=userInterface.activateUser(userId);
        userResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void getClientsCount( ResponseCallBack callback){
        Call<ClientCountDTO> userResponseCall = userInterface.getClientsCount();
        userResponseCall.enqueue(new CustomizeCallBack<ClientCountDTO>(callback));
    }
    public void getDashBoard( ResponseCallBack callback){
        Call<AdminDashboardDTO> userResponseCall = userInterface.getDashboard();
        userResponseCall.enqueue(new CustomizeCallBack<AdminDashboardDTO>(callback));
    }
}
