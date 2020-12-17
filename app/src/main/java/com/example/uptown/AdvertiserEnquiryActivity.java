package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uptown.Adapters.EnquiriesAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Enquiry;
import com.example.uptown.Services.EnquiryService;
import com.example.uptown.Services.WishListService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class AdvertiserEnquiryActivity extends AppCompatActivity implements ResponseCallBack {

    Integer id;
    ImageView backBtn;
    EnquiryService enquiryService;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_advertiser_enquiry);
        SharedPreferences prefs = getSharedPreferences("shared", Context.MODE_PRIVATE);
        enquiryService = new EnquiryService();
        id = prefs.getInt("id", 0);
        getEnquiries(id);
        recyclerView=findViewById(R.id.enquiriesList);
        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdvertiserEnquiryActivity.super.onBackPressed();
            }
        });

    }
    public void getEnquiries(int advertiserId){
        enquiryService.findAllEnquiries(advertiserId,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Enquiry> enquiries=(List<Enquiry>) response.body();
        if(!enquiries.isEmpty()){
            EnquiriesAdapter adapter=new EnquiriesAdapter(enquiries,AdvertiserEnquiryActivity.this);
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