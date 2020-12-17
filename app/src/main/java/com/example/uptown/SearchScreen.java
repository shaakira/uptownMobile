package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.Adapters.MyPropertyAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.PropertySearchDTO;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.PropertyService;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class SearchScreen extends AppCompatActivity implements ResponseCallBack {

    MaterialButton explore;
    ImageView backBtn;
    TextView text;
    GridView gridView;
    PropertyService propertyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        propertyService=new PropertyService();
        PropertySearchDTO propertySearchDTO=(PropertySearchDTO)getIntent().getSerializableExtra("Property");
        if(propertySearchDTO!=null){
            getProperties(propertySearchDTO);
        }


    }
    public void getProperties(PropertySearchDTO propertySearchDTO){
        propertyService.searchProperty(propertySearchDTO,this);
    }
    @Override
    public void onSuccess(Response response) throws IOException {
        List<Property> properties=(List<Property>) response.body();
        if(!properties.isEmpty()){
            setContentView(R.layout.activity_search_screen);
            gridView=findViewById(R.id.grid);
            backBtn=findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchScreen.super.onBackPressed();
                }
            });
            MyPropertyAdapter adapter=new MyPropertyAdapter(properties,getApplicationContext());
            gridView.setAdapter(adapter);

        }
        else{
            setContentView(R.layout.emprty_search);
            explore=findViewById(R.id.explore);
            explore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(SearchScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            });
            backBtn=findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchScreen.super.onBackPressed();
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