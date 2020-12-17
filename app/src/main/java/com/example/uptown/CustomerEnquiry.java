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
import com.example.uptown.Services.EnquiryService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class CustomerEnquiry extends AppCompatActivity  implements ResponseCallBack {
ImageView close;
Button send;
EditText name,email,phone,comment;
Integer propId;
EnquiryService enquiryService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enquiry);
        Intent intent = getIntent();
        propId = intent.getIntExtra("propertyId", 0);
        enquiryService=new EnquiryService();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        comment=findViewById(R.id.comment);
        close=findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerEnquiry.super.onBackPressed();
            }
        });
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.uptown.Model.Enquiry enquiry=new com.example.uptown.Model.Enquiry();
                enquiry.setName(name.getText().toString());
                enquiry.setEmail(email.getText().toString());
                enquiry.setPhone(Integer.parseInt(phone.getText().toString()));
                enquiry.setComment(comment.getText().toString());
                sendMessage(enquiry,propId);
            }
        });
    }
    public void sendMessage(com.example.uptown.Model.Enquiry enquiry,int id){ enquiryService.addEnquiry(id,enquiry,this);}
    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody result= (ResponseBody) response.body();
        if (result.string().equals("Success")) {
            Intent intent=new Intent(CustomerEnquiry.this,ThankYouActivity.class);
            intent.putExtra("propId",propId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in,R.anim.slide_up_out);
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