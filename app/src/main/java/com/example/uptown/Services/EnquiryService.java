package com.example.uptown.Services;

import com.example.uptown.API.EnquiryInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Enquiry;
import com.example.uptown.RetrofitClient.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class EnquiryService {
    RetrofitClient retrofitClient;
    EnquiryInterface enquiryInterface;

    public EnquiryService() {
        this.enquiryInterface=retrofitClient.getRetrofitClientInstance().create(EnquiryInterface.class);
    }
    public void addEnquiry(int id, Enquiry enquiry , ResponseCallBack callBack){
        Call<ResponseBody> enquiryResponseCall=enquiryInterface.addEnquiry(id,enquiry);
        enquiryResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }

    public void findAllEnquiries(int advertiserId,ResponseCallBack callBack){
        Call<List<Enquiry>> findAllEnquiriesResponseCall=enquiryInterface.findAllEnquiries(advertiserId);
        findAllEnquiriesResponseCall.enqueue(new CustomizeCallBack<List<Enquiry>>(callBack));
    }
    public void deleteEnquiry(int enquiryId,ResponseCallBack callBack){
        Call<ResponseBody> deleteResponseCall=enquiryInterface.deleteEnquiry(enquiryId);
        deleteResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void getEnquiry(int id,ResponseCallBack callBack){
        Call<Enquiry> enquiryCall=enquiryInterface.getEnquiry(id);
        enquiryCall.enqueue(new CustomizeCallBack<Enquiry>(callBack));
    }


}
