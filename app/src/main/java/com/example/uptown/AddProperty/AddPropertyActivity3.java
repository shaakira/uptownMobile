package com.example.uptown.AddProperty;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddPropertyActivity3 extends AppCompatActivity {

    ImageView backBtn;
    Button nextBtn;
    TextInputEditText bed,bath,garage;
    CheckBox furnished,pool,ac,internet,hotWater,gym,sRoom,security,electricity;
    List<String>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_property3);
        Intent intent=getIntent();
        Property property=intent.getExtras().getParcelable("activity2");
        bed=findViewById(R.id.beds);
        bath=findViewById(R.id.baths);
        garage=findViewById(R.id.garage);
        furnished=findViewById(R.id.furnished);
        pool=findViewById(R.id.pool);
        ac=findViewById(R.id.ac);
        internet=findViewById(R.id.internet);
        hotWater=findViewById(R.id.hotWater);
        gym=findViewById(R.id.gym);
        sRoom=findViewById(R.id.sRoom);
        security=findViewById(R.id.security);
        electricity=findViewById(R.id.electricity);
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.next);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPropertyActivity3.super.onBackPressed();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(validate()) {
                    int Bath = Integer.parseInt(bath.getText().toString());
                    int Beds = Integer.parseInt(bed.getText().toString());
                    int Garage = Integer.parseInt(garage.getText().toString());
                    Intent intent = new Intent(AddPropertyActivity3.this, AddPropertyActivity4.class);
                    getCheckBox();
                    String featureCommaSeparated = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        featureCommaSeparated = String.join(",", list);
                    }
                    property.setBaths(Bath);
                    property.setRooms(Beds);
                    property.setGarage(Garage);
                    property.setFeatures(featureCommaSeparated);
                    intent.putExtra("activity3", property);
                    startActivity(intent);
                }
            }
        });
    }
    public Boolean validate(){
        boolean flag=true;
        if(bed.getText().toString().trim().length()==0){
            bed.setError("Please Enter Bed Count");
            flag=false;
        }
        if(bath.getText().toString().trim().length()==0){
            bath.setError("Please Enter Bath Count");
            flag=false;
        }
        if(garage.getText().toString().trim().length()==0){
            garage.setError("Please Garage Count");
            flag=false;
        }
        return flag;
    }
    public void getCheckBox(){
        if(furnished.isChecked()){
            list.add("Fully Furnished");
        }
        if(pool.isChecked()){
            list.add("Swimming Pool");
        }
        if(ac.isChecked()){
            list.add("AC Rooms");
        }
        if(internet.isChecked()){
            list.add("Internet");
        }
        if(hotWater.isChecked()){
            list.add("Hot Water");
        }
        if(gym.isChecked()){
            list.add("Gym");
        }
        if(sRoom.isChecked()){
            list.add("Servant's Room");
        }
        if(security.isChecked()){
            list.add("24 hours security");
        }
        if(electricity.isChecked()){
            list.add("3 phase electricity");
        }


    }
}