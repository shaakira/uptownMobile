package com.example.uptown.API;

import com.example.uptown.Model.Enquiry;
import com.example.uptown.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EnquiryInterface {

    @POST("mobile/api/enquiry/addEnquiry/{id}")
    Call<ResponseBody> addEnquiry(@Path("id") int id, @Body Enquiry enquiry);

    @GET("mobile/api/enquiry/findAll/{advertiserId}")
    Call<List<Enquiry>> findAllEnquiries(@Path("advertiserId") int advertiserId);

    @GET("mobile/api/enquiry/deleteEnquiry/{enquiryId}")
    Call<ResponseBody> deleteEnquiry(@Path("enquiryId") int enquiryId);

    @GET("mobile/api/enquiry/getEnquiry/{id}")
    Call<Enquiry> getEnquiry(@Path("id") int id);


}
