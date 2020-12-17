package com.example.uptown.AddProperty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddPropertyActivity2 extends AppCompatActivity {

    ImageView backBtn;
    Button nextBtn;
    AutoCompleteTextView priceType;
    TextInputEditText descripton,price,area;
    String rateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_property2);
        descripton=findViewById(R.id.description);
        price=findViewById(R.id.area);
        area=findViewById(R.id.price);
        Intent intent=getIntent();
        Property property=intent.getExtras().getParcelable("activity1");

        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.next);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPropertyActivity2.super.onBackPressed();
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPropertyActivity2.this, AddPropertyActivity3.class);

                if(validate()){
                    String Description=descripton.getText().toString();
                    Double Price=Double.parseDouble(price.getText().toString());
                    Float Area=Float.valueOf(area.getText().toString());
                    property.setDescription(Description);
                    property.setRate(Price);
                    property.setArea(Area);
                    property.setRateType(rateType);
                    intent.putExtra("activity2",property);
                    startActivity(intent);
                }
            }
        });
        priceType = findViewById(R.id.priceType);
        String[] types = {"Per Day", "Per Week", "Per Month", "Per Year"};
        ArrayAdapter typesAdapter = new ArrayAdapter(this, R.layout.option_item, types);
        priceType.setText(typesAdapter.getItem(0).toString(), false);
        priceType.setAdapter(typesAdapter);
        priceType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                rateType   = (String)parent.getItemAtPosition(position);
                //TODO Do something with the selected text
            }
        });
    }

    public Boolean validate(){
        boolean flag=true;
        if(descripton.getText().toString().trim().length()==0){
            descripton.setError("Please Enter Description");
            flag=false;
        }
        if(price.getText().toString().trim().length()==0){
            price.setError("Please Enter Price");
            flag=false;
        }
        if(area.getText().toString().trim().length()==0){
            area.setError("Please Enter Area");
            flag=false;
        }
        return flag;
    }
}