package com.example.uptown.Services;

import com.example.uptown.API.WishListInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Wishlist;
import com.example.uptown.RetrofitClient.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class WishListService {

    RetrofitClient retrofitClient;
    WishListInterface wishListInterface;

    public WishListService() {
        this.wishListInterface=retrofitClient.getRetrofitClientInstance().create(WishListInterface.class);
    }
    public void getWishLists(int userId, ResponseCallBack callback){
        Call<List<Wishlist>> wishListResponseCall = wishListInterface.getWishLists(userId);
        wishListResponseCall.enqueue(new CustomizeCallBack<List<Wishlist>>(callback));
    }
    public void remove(int id,ResponseCallBack callBack){
        Call<ResponseBody> removeResponseCall=wishListInterface.remove(id);
        removeResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void getWishList(int id,ResponseCallBack callBack){
        Call<Wishlist> wishlistCall=wishListInterface.getWishList(id);
        wishlistCall.enqueue(new CustomizeCallBack<Wishlist>(callBack));
    }
    public void getWishListed(int propId,int userId,ResponseCallBack callBack){
        Call<Boolean> wishListedCall=wishListInterface.getWishListed(propId,userId);
        wishListedCall.enqueue(new CustomizeCallBack<Boolean>(callBack));
    }
    public void addWishList(Wishlist wishlist,int propId,int userId,ResponseCallBack callBack){
        Call<ResponseBody> addWishList=wishListInterface.addWishList(wishlist,propId,userId);
        addWishList.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
}
