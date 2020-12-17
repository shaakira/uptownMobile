package com.example.uptown.Services;

import com.example.uptown.API.PropertyInterface;
import com.example.uptown.API.UserInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.PropertySearchDTO;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PropertyService {
    RetrofitClient retrofitClient;
    PropertyInterface propertyInterface;


    public PropertyService() {
        //creating the connection with the API
        this.propertyInterface = retrofitClient.getRetrofitClientInstance().create(PropertyInterface.class);
    }
    public void addProperty(List<MultipartBody.Part> image, Property property, int id, ResponseCallBack callback) {
        Call<ResponseBody> propertyResponseCall = propertyInterface.addProperty(image,property,id);
        propertyResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callback));
    }


    public void getProperty(int id, ResponseCallBack callback) {
        Call<Property> profileResponseCall = propertyInterface.getProperty(id);
        profileResponseCall.enqueue(new CustomizeCallBack<Property>(callback));
    }

    public void getPublishedProperty(ResponseCallBack callback) {
        Call<List<Property>> propertyResponseCall = propertyInterface.getPublishedProperty();
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callback));
    }

    public void searchProperty(PropertySearchDTO propertySearchDTO, ResponseCallBack callBack) {
        Call<List<Property>> propertyResponseCall = propertyInterface.searchProperty(propertySearchDTO);
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callBack));
    }

    public void searchByCity(String city, ResponseCallBack callBack) {
        Call<List<Property>> propertyResponseCall = propertyInterface.searchByCity(city);
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callBack));
    }

    public void myPendingProperties(int advertiserId, ResponseCallBack callback) {
        Call<List<Property>> propertyResponseCall = propertyInterface.myPendingProperties(advertiserId);
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callback));
    }

    public void myPublishedProperties(int advertiserId, ResponseCallBack callback) {
        Call<List<Property>> propertyResponseCall = propertyInterface.myPublishedProperties(advertiserId);
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callback));
    }

    public void myRejectedProperties(int advertiserId, ResponseCallBack callback) {
        Call<List<Property>> propertyResponseCall = propertyInterface.myRejectedProperties(advertiserId);
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callback));
    }

    public void requestAgain(int propId, ResponseCallBack callBack) {
        Call<ResponseBody> propertyResponseCall = propertyInterface.requestAgain(propId);
        propertyResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }

    public void deleteProperty(int propId, int advertiserId, ResponseCallBack callBack) {
        Call<ResponseBody> propertyResponseCall = propertyInterface.deleteProperty(propId, advertiserId);
        propertyResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }

    public void pendingProperties(ResponseCallBack callBack) {
        Call<List<Property>> propertyResponseCall = propertyInterface.pendingProperties();
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callBack));
    }

    public void publishedProperties(ResponseCallBack callBack) {
        Call<List<Property>> propertyResponseCall = propertyInterface.publishedProperties();
        propertyResponseCall.enqueue(new CustomizeCallBack<List<Property>>(callBack));
    }
    public void acceptProperty(int propId, ResponseCallBack callBack) {
        Call<ResponseBody> propertyResponseCall = propertyInterface.acceptProperty(propId);
        propertyResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void rejectProperty(int propId, ResponseCallBack callBack) {
        Call<ResponseBody> propertyResponseCall = propertyInterface.rejectProperty(propId);
        propertyResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }

    public void getPendingProperty(int id, ResponseCallBack callback) {
        Call<Property> propertyResponseCall = propertyInterface.getPendingProperty(id);
        propertyResponseCall.enqueue(new CustomizeCallBack<Property>(callback));
    }
}
