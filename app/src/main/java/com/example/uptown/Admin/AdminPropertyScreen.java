package com.example.uptown.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.PropertyService;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class AdminPropertyScreen extends AppCompatActivity implements ResponseCallBack {
    ImageView backBtn, image1, image2, image3, a_image;
    TextView heading, propStreet, propCity, propProv, price, priceType, propType, beds, baths, garage, name, uType, des;
    Integer id, propId;
    PropertyService propertyService;
    String type;
    MaterialButton reject, accept;
    LinearLayout btnContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_property_screen);
        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        Intent intent = getIntent();
        propId = intent.getIntExtra("propId", 0);
        type = intent.getStringExtra("type");
        propertyService = new PropertyService();
        getProperty(propId);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminPropertyScreen.super.onBackPressed();
            }
        });
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        a_image = findViewById(R.id.a_image);
        heading = findViewById(R.id.description);
        propStreet = findViewById(R.id.propStreet);
        propCity = findViewById(R.id.propCity);
        propProv = findViewById(R.id.propProv);
        price = findViewById(R.id.price);
        priceType = findViewById(R.id.priceType);
        propType = findViewById(R.id.propType);
        beds = findViewById(R.id.beds);
        baths = findViewById(R.id.baths);
        garage = findViewById(R.id.garage);
        name = findViewById(R.id.name);
        uType = findViewById(R.id.uType);
        des = findViewById(R.id.des);
        reject = findViewById(R.id.rejectBtn);
        accept = findViewById(R.id.acceptBtn);
        btnContainer = findViewById(R.id.btnContainer);
        if (type.equals("published")) {
            btnContainer.setVisibility(View.GONE);
        } else if (type.equals("pending")) {
            btnContainer.setVisibility(View.VISIBLE);
        }
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectProperty(propId);
               Intent intent1=new Intent(AdminPropertyScreen.this,AdminActivity.class);
                intent1.putExtra("direct","properties");
                finish();
                startActivity(intent1);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptProperty(propId);
                Intent intent1=new Intent(AdminPropertyScreen.this,AdminActivity.class);
                intent1.putExtra("direct","properties");
                startActivity(intent1);
            }
        });


    }

    public void getProperty(int id) {

        if (type.equals("published")) {
            propertyService.getProperty(id, this);
        } else if (type.equals("pending")) {
            propertyService.getPendingProperty(id, this);
        }
    }

    public void rejectProperty(int propId) {
        propertyService.rejectProperty(propId, getRejectResponse());
    }
    public void acceptProperty(int propId){
        propertyService.acceptProperty(propId,getAcceptedResponse());
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        Property property = (Property) response.body();
        if (property != null) {
            String url = RetrofitClient.Url() + "resources/Image/" + property.getImage1();
            String url2 = RetrofitClient.Url() + "resources/Image/" + property.getImage2();
            String url3 = RetrofitClient.Url() + "resources/Image/" + property.getImage3();
            Picasso.get().load(url).fit()
                    .centerCrop().into(image1);
            Picasso.get().load(url2).fit()
                    .centerCrop().into(image2);
            Picasso.get().load(url3).fit()
                    .centerCrop().into(image3);
            String img = property.getUser().getImage();
            if (img != null) {
                String urls = RetrofitClient.Url() + "resources/Image/" + img;
                Picasso.get().load(urls).fit()
                        .centerCrop().into(a_image);
            }
            String a_name = property.getUser().getFirstName() + " " + property.getUser().getLastName();
            name.setText(a_name);
            uType.setText(property.getUser().getuType());
            if (property.getRateType().equals("perDay")||property.getRateType().equals("Per Day")) {
                priceType.setText("per day");
            } else if (property.getRateType().equals("perMonth")||property.getRateType().equals("Per Month")) {
                priceType.setText("per month");

            } else if (property.getRateType().equals("perWeek")||property.getRateType().equals("Per Week")) {
                priceType.setText("per week");
            } else {
                priceType.setText("per year");
            }
            price.setText(String.valueOf(property.getRate()));
            propType.setText(property.getpType());
            beds.setText(String.valueOf(property.getRooms()));
            baths.setText(String.valueOf(property.getBaths()));
            garage.setText(String.valueOf(property.getGarage()));
            des.setText(property.getDescription());
            propStreet.setText(property.getStreet());
            propCity.setText(property.getCity());
            propProv.setText(property.getProvince());
            heading.setText(property.getHeading());
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(this, "Network Error Please check your Network Cconnection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public ResponseCallBack getRejectResponse() {
        ResponseCallBack propertyResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {

                    Toast.makeText(getApplicationContext(), "Appointment Rejected", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return propertyResponseCallBack;
    }
    public ResponseCallBack getAcceptedResponse() {
        ResponseCallBack propertyResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {

                    Toast.makeText(getApplicationContext(), "Appointment Rejected", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return propertyResponseCallBack;
    }
}