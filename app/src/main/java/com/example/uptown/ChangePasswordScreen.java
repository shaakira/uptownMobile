package com.example.uptown;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.GetChars;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Request.PasswordDTO;
import com.example.uptown.Services.UserService;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChangePasswordScreen extends AppCompatActivity implements ResponseCallBack {

    ImageView backBtn;
    EditText email, newPwd, confirmPwd;
    Button changeBtn, continueBtn;
    Integer id;
    UserService userService;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password_screen);
        view=findViewById(R.id.activity_change_password_screen);
        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        userService = new UserService();
        id = prefs.getInt("id", 0);
        backBtn = findViewById(R.id.backBtn);
        email = findViewById(R.id.email);
        newPwd = findViewById(R.id.newPwd);
        confirmPwd = findViewById(R.id.confirmPwd);
        changeBtn = findViewById(R.id.changeBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordScreen.super.onBackPressed();
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordDTO passwordDTO=new PasswordDTO();
                passwordDTO.setEmail(email.getText().toString());
                passwordDTO.setNewPassword(newPwd.getText().toString());
                passwordDTO.setConfirmPassword(confirmPwd.getText().toString());
                changePassword(passwordDTO,id);

            }
        });
    }

    public void changePassword(PasswordDTO passwordDTO,int userId){
        userService.changePassword(passwordDTO,userId,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody = (ResponseBody) response.body();

        if (responseBody.string().equals("password successfully updated")) {
            setContentView(R.layout.pawd_success_layout);
            continueBtn = findViewById(R.id.continueBtn);
            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChangePasswordScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(view, responseBody.string(), Snackbar.LENGTH_LONG);
            snackbar.setDuration(1000);
            snackbar.show();
            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.danger));
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
}