package com.example.uptown.API;

import com.example.uptown.Model.Wishlist;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WishListInterface {

    @GET("mobile/api/wishList/getWishList/{userId}")
    Call<List<Wishlist>> getWishLists(@Path("userId") int userId);

    @GET("mobile/api/wishList/removeWishList/{id}")
    Call<ResponseBody> remove(@Path("id") int id);

    @GET("mobile/api/wishList/wishList/{id}")
    Call<Wishlist> getWishList(@Path("id") int id);

    @GET("mobile/api/wishList/getWishListed/{propId}/{userId}")
    Call<Boolean> getWishListed(@Path("propId")int propId,@Path("userId") int userId);

    @POST("mobile/api/wishList/addWishList/{propId}/{userId}")
    Call<ResponseBody> addWishList(@Body Wishlist wishlist, @Path("propId")int propId, @Path("userId") int userId);
}
