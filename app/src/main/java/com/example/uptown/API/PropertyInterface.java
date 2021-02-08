package com.example.uptown.API;

import com.example.uptown.DTO.Request.PropertySearchDTO;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PropertyInterface {
    @Multipart
    @POST("mobile/api/property/addPropertyPost")
    Call<ResponseBody> addProperty(@Part List<MultipartBody.Part> image, @Part("property") Property property, @Part("id") int id);

    @GET("mobile/api/property/singleProperty/{id}")
    Call<Property> getProperty(@Path("id") int id);

    @GET("mobile/api/property/publishedProp")
    Call<List<Property>> getPublishedProperty();

    @POST("mobile/api/property/search")
    Call<List<Property>> searchProperty(@Body PropertySearchDTO property);

    @GET("mobile/api/property/search/{city}")
    Call<List<Property>> searchByCity(@Path("city") String city);

    @GET("mobile/api/property/myProperty/pending/{advertiserId}")
    Call<List<Property>> myPendingProperties(@Path("advertiserId") int advertiserId);

    @GET("mobile/api/property/myProperty/published/{advertiserId}")
    Call<List<Property>> myPublishedProperties(@Path("advertiserId") int advertiserId);

    @GET("mobile/api/property/myProperty/reject/{advertiserId}")
    Call<List<Property>> myRejectedProperties(@Path("advertiserId") int advertiserId);

    @GET("mobile/api/property/requestAgain/{propId}")
    Call<ResponseBody> requestAgain(@Path("propId") int propId);

    @GET("mobile/api/property/deleteProperty/{propId}/{advertiserId}")
    Call<ResponseBody> deleteProperty(@Path("propId") int propId, @Path("advertiserId") int advertiserId);

    @GET("mobile/api/property/admin/pending")
    Call<List<Property>> pendingProperties();

    @GET("mobile/api/property/admin/published")
    Call<List<Property>> publishedProperties();

    @GET("mobile/api/property/admin/acceptProperty/{propId}")
    Call<ResponseBody> acceptProperty(@Path("propId") int propId);

    @GET("mobile/api/property/admin/rejectProperty/{propId}")
    Call<ResponseBody> rejectProperty(@Path("propId") int propId);
    @GET("mobile/api/property/singleProperty/pending/{id}")
    Call<Property> getPendingProperty(@Path("id") int id);
}
