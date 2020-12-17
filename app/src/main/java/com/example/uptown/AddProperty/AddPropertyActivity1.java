package com.example.uptown.AddProperty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddPropertyActivity1 extends AppCompatActivity {


    ImageView backBtn;
    Button nextBtn;
    TextView title;
    AutoCompleteTextView type;
    String selection;
    TextInputEditText heading,street,city,province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_property);
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.next);
        title = findViewById(R.id.title);
        heading=findViewById(R.id.heading);
        street=findViewById(R.id.street);
        city=findViewById(R.id.city);
        province=findViewById(R.id.province);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPropertyActivity1.super.onBackPressed();
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Heading=heading.getText().toString();
                String Street=street.getText().toString();
                String City=city.getText().toString();
                String Province=province.getText().toString();
                if(validate(Heading,Street,Province,City)) {
                    Intent intent = new Intent(AddPropertyActivity1.this, AddPropertyActivity2.class);
                    Property property = new Property(Heading, Street, Province, City, selection);
                    intent.putExtra("activity1", property);
                    startActivity(intent);
                }
            }
        });
        type = findViewById(R.id.type);
        String[] types = {"House", "Villa", "Apartment", "Bungalow"};
        ArrayAdapter typesAdapter = new ArrayAdapter(this, R.layout.option_item, types);
        type.setText(typesAdapter.getItem(0).toString(), false);
        type.setAdapter(typesAdapter);
        type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                selection   = (String)parent.getItemAtPosition(position);
                //TODO Do something with the selected text
            }
        });
    }

    public Boolean validate(String Heading,String Street,String Province,String City){
        boolean flag=true;
        if(Heading.isEmpty()){
            heading.setError("Please Enter Heading");
            flag=false;
        }
        if(Street.isEmpty()){
            street.setError("Please Enter Street");
            flag=false;
        }
        if(Province.isEmpty()){
            province.setError("Please Enter Province");
            flag=false;
        }
        if (City.isEmpty()){
            city.setError("Please Enter City");
            flag=false;
        }

        return flag;
    }

}