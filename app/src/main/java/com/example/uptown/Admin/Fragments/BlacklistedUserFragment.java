package com.example.uptown.Admin.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.uptown.Admin.Adapters.ClientsAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Fragments.AdminFragments.ClientsFragment;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.Services.UserService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class BlacklistedUserFragment extends Fragment implements ResponseCallBack {
    GridView gridView;
    UserService userService;
    Integer id;
    ResponseCallBack responseCallBack;

    public  BlacklistedUserFragment(ResponseCallBack responseCallBack){
        this.responseCallBack=responseCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_blacklisted_user_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        userService = new UserService();
        id = prefs.getInt("id", 0);
        gridView = view.findViewById(R.id.blacklistedUser);
        getUsers();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public void getUsers(){
        userService.getBlacklistedUsers(this);
    }
    @Override
    public void onSuccess(Response response) throws IOException {
        List<User> users = (List<User>) response.body();
        if (!users.isEmpty()) {
            ClientsAdapter adapter = new ClientsAdapter(users, getContext(),"blacklisted",responseCallBack);
            gridView.setAdapter(adapter);

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