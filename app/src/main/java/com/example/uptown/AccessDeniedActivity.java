package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class AccessDeniedActivity extends AppCompatActivity {

    MaterialButton gotBtn,contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_denied);

        gotBtn=findViewById(R.id.goBtn);
        contactBtn=findViewById(R.id.contactBtn);
        gotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccessDeniedActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccessDeniedActivity.this,MainActivity.class);
                intent.putExtra("direct", "about");
                startActivity(intent);
            }
        });

    }
}