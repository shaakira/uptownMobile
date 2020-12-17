package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.UserService;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity implements ResponseCallBack {

    ImageView backBtn,image;
    UserService userService;
    EditText fName,lName,address,email;
    TextView save;
    Integer id;
    LinearLayout  delete,changePwd;
    Dialog dialog;
    Button deleteBtn, cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        userService = new UserService();
        id = prefs.getInt("id", 0);
        getUser(id);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        backBtn=findViewById(R.id.backbttn);
        save=findViewById(R.id.save);
        image=findViewById(R.id.image);
        dialog = new Dialog(this);
        delete=findViewById(R.id.delete);
        changePwd=findViewById(R.id.changePwd);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setFirstName(fName.getText().toString());
                user.setLastName(lName.getText().toString());
                user.setAddress(address.getText().toString());
                user.setEmail(email.getText().toString());
               updateUser(user,id);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile.super.onBackPressed();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.delete_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                cancelBtn=dialog.findViewById(R.id.cancelBtn);
                deleteBtn=dialog.findViewById(R.id.deleteBtn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteUser(id);

                    }
                });



            }
        });
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,ChangePasswordScreen.class);
                startActivity(intent);
            }
        });
    }
    public void getUser(int id) {
        userService.GetProfile(id, this);
    }
    public void updateUser(User user,int id) {userService.updateUser(user,id,getProfileResponse());
    }
    public void deleteUser(int userId){
        userService.deleteUser(userId,getDeleteUserResponse());
    }

    public ResponseCallBack getDeleteUserResponse(){
        ResponseCallBack profileResponseCallBack=new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody result= (ResponseBody) response.body();
                if(result.string().equals("Success")){
                    SharedPreferences logout=getSharedPreferences("shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor=logout.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
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
        return profileResponseCallBack;
    }

    public ResponseCallBack getProfileResponse(){
        ResponseCallBack profileResponseCallBack=new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody result= (ResponseBody) response.body();
                Toast.makeText(EditProfile.this, result.string(), Toast.LENGTH_SHORT).show();
              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("direct", "profile");
                startActivity(intent);

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
        return profileResponseCallBack;
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        User user = (User) response.body();

        if (user != null) {
            fName.setText(user.getFirstName());
            lName.setText(user.getLastName());
            address.setText(user.getAddress());
            email.setText(user.getEmail());
            String img=user.getImage();
            if(img!=null){
                String url= RetrofitClient.Url()+"resources/Image/"+img;
                Picasso.get().load(url).fit()
                        .centerCrop().into(image);


            }
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