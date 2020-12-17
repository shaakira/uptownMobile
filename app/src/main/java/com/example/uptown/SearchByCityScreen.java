package com.example.uptown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uptown.Adapters.MyPropertyAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.PropertySearchDTO;
import com.example.uptown.Model.Property;
import com.example.uptown.Services.PropertyService;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class SearchByCityScreen extends AppCompatActivity implements ResponseCallBack {
    MaterialButton explore;
    ImageView backBtn;
    TextView text;
    GridView gridView;
    PropertyService propertyService;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        propertyService = new PropertyService();
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        getProperties(city);


    }

    public void getProperties(String city) {
        propertyService.searchByCity(city, this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Property> properties = (List<Property>) response.body();
        if (!properties.isEmpty()) {
            setContentView(R.layout.activity_search_screen);
            gridView = findViewById(R.id.grid);
            backBtn = findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchByCityScreen.super.onBackPressed();
                }
            });
            MyPropertyAdapter adapter = new MyPropertyAdapter(properties, getApplicationContext());
            gridView.setAdapter(adapter);

        } else {
            setContentView(R.layout.emprty_search);
            explore = findViewById(R.id.explore);
            explore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchByCityScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            backBtn = findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchByCityScreen.super.onBackPressed();
                }
            });
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
