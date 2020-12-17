package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.Adapters.MyPropertyAdapter;
import com.example.uptown.Adapters.PropertyAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.UserService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class SingleAgent extends AppCompatActivity implements ResponseCallBack {

    ImageView backBtn,image,call;
    TextView name,email;
    GridView gridView;
    UserService userService;
    Integer agentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_agent);
        userService = new UserService();
        Intent intent = getIntent();
        agentId = intent.getIntExtra("agentId", 0);
        getUser(agentId);
        getAgentProperty(agentId);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        image=findViewById(R.id.image);
        backBtn=findViewById(R.id.backBtn);
        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:776068444"));
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleAgent.super.onBackPressed();
            }
        });
        gridView=findViewById(R.id.propGrid);

    }
    public void getUser(int id) {
        userService.GetProfile(id, this);
    }
    public void getAgentProperty(int id){userService.getSingleAgent(id,getPropertyResponse());}
    public ResponseCallBack getPropertyResponse(){
        ResponseCallBack profileResponseCallBack=new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {

                List<Property> properties=(List<Property>) response.body();
                if(!properties.isEmpty()){
                    MyPropertyAdapter adapter=new MyPropertyAdapter(properties,getApplicationContext());
                    gridView.setAdapter(adapter);
                }
;
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
            name.setText(user.getFirstName());
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