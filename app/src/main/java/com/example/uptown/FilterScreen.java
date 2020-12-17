package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.uptown.DTO.Request.PropertySearchDTO;
import com.google.android.material.textfield.TextInputEditText;

public class FilterScreen extends AppCompatActivity {

    ImageView closeBtn;
    AutoCompleteTextView type, beds, baths, garage, city;
    TextInputEditText heading;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_filter_screen);
        closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterScreen.super.onBackPressed();
            }
        });
        type = findViewById(R.id.type);
        String[] types = {"House", "Villa", "Apartment", "Bungalow"};
        ArrayAdapter typesAdapter = new ArrayAdapter(this, R.layout.option_item, types);
        type.setText(typesAdapter.getItem(0).toString(), false);
        type.setAdapter(typesAdapter);
        city = findViewById(R.id.city);
        String[] cities = {"Colombo", "Negombo", "Kandy", "Pelawatta", "Galle", "Kurunegala"};
        ArrayAdapter cityAdapter = new ArrayAdapter(this, R.layout.option_item, cities);
        city.setText(cityAdapter.getItem(0).toString(), false);
        city.setAdapter(cityAdapter);
        beds = findViewById(R.id.beds);
        String[] bed = {"1", "2", "3", "4", "5"};
        ArrayAdapter bedAdapter = new ArrayAdapter(this, R.layout.option_item, bed);
        beds.setText(bedAdapter.getItem(0).toString(), false);
        beds.setAdapter(bedAdapter);
        baths = findViewById(R.id.baths);
        String[] bath = {"1", "2", "3", "4"};
        ArrayAdapter bathAdapter = new ArrayAdapter(this, R.layout.option_item, bath);
        baths.setText(bathAdapter.getItem(0).toString(), false);
        baths.setAdapter(bathAdapter);
        garage = findViewById(R.id.garage);
        String[] garages = {"1", "2", "3", "4"};
        ArrayAdapter garageAdapter = new ArrayAdapter(this, R.layout.option_item, garages);
        garage.setText(garageAdapter.getItem(0).toString(), false);
        garage.setAdapter(garageAdapter);
        heading = findViewById(R.id.description);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterScreen.this, SearchScreen.class);
                PropertySearchDTO property = new PropertySearchDTO();
                property.setHeading(heading.getText().toString());
                property.setCity(city.getText().toString());
                property.setRooms(Integer.parseInt(beds.getText().toString()));
                property.setBaths(Integer.parseInt(baths.getText().toString()));
                property.setGarage(Integer.parseInt(garage.getText().toString()));
                property.setpType(type.getText().toString());
                intent.putExtra("Property",property);
                startActivity(intent);
            }
        });


    }
}