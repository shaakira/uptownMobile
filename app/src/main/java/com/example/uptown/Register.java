package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Response.AuthResponse;
import com.example.uptown.Model.User;
import com.example.uptown.Services.AuthService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class Register extends AppCompatActivity implements ResponseCallBack {
    Button register;
    EditText fName, lName, username, password, address, type, email;
    CheckBox customer, owner, agent;
    TextView signInLink;
    ImageView backBtn,proPic;
    CardView imageUpload;
    public AuthService authService;
    Uri selectedImage;
    MultipartBody.Part body;
    public static final int GET_FROM_GALLERY = 3;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        authService = new AuthService();
        register = findViewById(R.id.register);
        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        customer = findViewById(R.id.customer);
        owner = findViewById(R.id.owner);
        agent = findViewById(R.id.agent);
        signInLink = findViewById(R.id.signInLink);
        backBtn = findViewById(R.id.back_button);
        imageUpload=findViewById(R.id.imageUpload);
        proPic=findViewById(R.id.proPic);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.firstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.lastName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FNAME="",LNAME="",EMAIL="",USERNAME="" ,PASSWORD=null;
                if (awesomeValidation.validate()) {
                    FNAME = fName.getText().toString();
                    LNAME = lName.getText().toString();
                    EMAIL = email.getText().toString();
                    USERNAME = username.getText().toString();
                    PASSWORD = password.getText().toString();


                    String ADDRESS = address.getText().toString();

                    String TYPE = "";
                    if (customer.isChecked()) {
                        TYPE = "customer";
                    } else if (owner.isChecked()) {
                        TYPE = "owner";
                    } else if (agent.isChecked()) {
                        TYPE = "agent";
                    }
                    User user = new User(USERNAME, FNAME, LNAME, EMAIL, ADDRESS, TYPE, PASSWORD);
//                if(body==null){
//                    Toast.makeText(Register.this, "Upload an Image to Register", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    doRegister(user, body);
//                }
                    String path = getRealPathFromURI(selectedImage);
                    File file = new File(path);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    doRegister(user, body);


                }
            }
        });
        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.super.onBackPressed();
            }
        });
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(
                        Intent.ACTION_PICK);

                intent.setType("image/*");
                startActivityForResult(intent, 0);
//                startActivityForResult(new Intent
//                        (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
//                        GET_FROM_GALLERY);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==0 && resultCode == Activity.RESULT_OK) {
            selectedImage= data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                proPic.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private String getRealPathFromURI(Uri uri){
        String[] projection={MediaStore.Images.Media.DATA};
        CursorLoader loader=new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor=loader.loadInBackground();
        int column_idx=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(column_idx);
        cursor.close();
        return  result;
    }

    public void doRegister(User user, MultipartBody.Part image){
        authService.Register(image,user,this);
    }

    @Override
    public void onSuccess(Response response) {
        try {
            AuthResponse user = (AuthResponse) response.body();
            // String result = responseBody.string();
            if (user != null) {
                SharedPreferences.Editor editor = getSharedPreferences("shared", MODE_PRIVATE).edit();
                editor.putInt("id",user.getId());
                editor.apply();
                if (user.getuType().equals("agent")) {
                    //Intent agent page

                    Toast.makeText(this, "Agent", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(user.getuType().equals("owner")){
                    //intent owner page
                    Toast.makeText(this, "Owner", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(user.getuType().equals("customer")){
                    //intent owner page
                    Intent intent=new Intent(Register.this,OnBoardingActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "customer", Toast.LENGTH_SHORT).show();
                }

                //  Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            } else {
                //Display Error
                Toast.makeText(Register.this, "Invalid Try Again", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){

        }
    }

    @Override
    public void onError(String errorMessage) {
        if(errorMessage.equals("")){
            Toast.makeText(this, "Network Error Please check your Network Cconnection", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();}
    }
}