package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.Admin.AdminActivity;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.AuthRequest;
import com.example.uptown.DTO.Response.AuthResponse;
import com.example.uptown.Services.AuthService;

import java.io.IOException;

import retrofit2.Response;

public class Login extends AppCompatActivity implements ResponseCallBack {
Button login;
EditText username,password;
ImageView backBtn;
TextView signUpBtn;
public AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        authService=new AuthService();
        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        backBtn=findViewById(R.id.backBtn);
        signUpBtn=findViewById(R.id.signUpBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String USERNAME=username.getText().toString();
                String PASSWORD=password.getText().toString();
                AuthRequest authRequest = new AuthRequest(USERNAME, PASSWORD);
                doLogin(authRequest);

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.super.onBackPressed();
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }

    public void doLogin(AuthRequest authRequest)
    {

        authService.Login(authRequest,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        AuthResponse user =(AuthResponse) response.body();
        if (user != null && user.getStatus().equals("active")) {
            SharedPreferences.Editor editor = getSharedPreferences("shared", MODE_PRIVATE).edit();
            editor.putInt("id",user.getId());
            editor.apply();
            if (user.getuType().equals("agent")) {
                //Intent agent page

                Toast.makeText(this, "Agent", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
            else if(user.getuType().equals("owner")){
                //intent owner page
                Toast.makeText(this, "Owner", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
            else if(user.getuType().equals("customer")){
                //intent owner page
                Intent intent=new Intent(Login.this,OnBoardingActivity.class);
                startActivity(intent);
                Toast.makeText(this, "customer", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Admin", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Login.this, AdminActivity.class);
                startActivity(intent);

            }
        }
        if (user != null && user.getStatus().equals("blacklist")) {
            Intent intent=new Intent(Login.this,AccessDeniedActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onError(String errorMessage) {
        if(errorMessage.equals("")){
            Toast.makeText(this, "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please Check Your Login Credentials and Try Again", Toast.LENGTH_SHORT).show();}
    }
}