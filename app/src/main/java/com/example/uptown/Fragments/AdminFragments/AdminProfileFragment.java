package com.example.uptown.Fragments.AdminFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.UserService;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdminProfileFragment extends Fragment implements ResponseCallBack {
    UserService userService;
    ImageView image;
    TextView name,email,address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_admin_profile_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", MODE_PRIVATE);
        userService = new UserService();
        Integer id = prefs.getInt("id", 0);
        getProfile(id);
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        return view;
    }
    public void getProfile(int userId){
        userService.GetProfile(userId,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        User user=(User)response.body();
        if(user!=null){
            String img = user.getImage();
            if (img != null) {
                String url = RetrofitClient.Url() + "resources/Image/" + img;
                Picasso.get().load(url).fit().into(image);


            }
            String adminName=user.getFirstName()+" "+user.getLastName();
            name.setText(adminName);
            email.setText(user.getEmail());
            address.setText(user.getAddress());

        }

    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(getContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}