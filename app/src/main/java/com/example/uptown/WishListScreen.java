package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uptown.Adapters.PropertyAdapter;
import com.example.uptown.Adapters.WishListAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.Wishlist;
import com.example.uptown.Services.WishListService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class WishListScreen extends AppCompatActivity implements ResponseCallBack {

    RecyclerView recyclerView;
    WishListService wishListService;
    Integer id;
    ImageView backBtn, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wish_list_screen);
        recyclerView = findViewById(R.id.recyclerView);
        SharedPreferences prefs = getSharedPreferences("shared", Context.MODE_PRIVATE);
        wishListService = new WishListService();
        id = prefs.getInt("id", 0);
        getWishList(id);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WishListScreen.super.onBackPressed();
            }
        });
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishListScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getWishList(int userId) {
        wishListService.getWishLists(userId, this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Wishlist> wishlists = (List<Wishlist>) response.body();
        if (!wishlists.isEmpty()) {
//            StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            WishListAdapter adapter = new WishListAdapter(wishlists, WishListScreen.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(this, "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}