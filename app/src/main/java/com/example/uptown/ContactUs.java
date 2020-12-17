package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Notifications;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.NotificationService;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity implements ResponseCallBack {
    ImageView backBtn;
    EditText name, email, subject, message;
    Button send;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        notificationService=new NotificationService();
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactUs.super.onBackPressed();
            }
        });
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notifications notifications=new Notifications();
                notifications.setName(name.getText().toString());
                notifications.setEmail(email.getText().toString());
                notifications.setSubject(subject.getText().toString());
                notifications.setMessage(subject.getText().toString());
                sendMessage(notifications);
            }
        });


    }
    public void sendMessage(Notifications notifications){notificationService.addNotification(notifications,this);}
    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody result= (ResponseBody) response.body();
        if (result.string().equals("Success")) {
            Toast.makeText(ContactUs.this, "Message successfully sent", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("direct", "about");
            startActivity(intent);

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