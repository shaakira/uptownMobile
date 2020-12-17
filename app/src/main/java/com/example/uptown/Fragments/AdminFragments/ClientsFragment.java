package com.example.uptown.Fragments.AdminFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.Adapters.ViewPagerAdapter;
import com.example.uptown.Admin.Fragments.ActiveUserFragment;
import com.example.uptown.Admin.Fragments.BlacklistedUserFragment;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Response.ClientCountDTO;
import com.example.uptown.R;
import com.example.uptown.Services.UserService;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import retrofit2.Response;


public class ClientsFragment extends Fragment implements ResponseCallBack {
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView active,blacklisted;
    static UserService userService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_clients_fragment, container, false);
        userService=new UserService();
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new ActiveUserFragment(this), "Active users");
        adapter.AddFragment(new BlacklistedUserFragment(this), "Blacklisted users");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        active=view.findViewById(R.id.activeNum);
        blacklisted=view.findViewById(R.id.bl_Num);
        getClientsCounts(this);

        return view;
    }
    public static void getClientsCounts(ResponseCallBack responseCallBack){
        userService.getClientsCount(responseCallBack);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        };
    }

    public void update(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }


    @Override
    public void onSuccess(Response response) throws IOException {
        ClientCountDTO clientCountDTO=(ClientCountDTO)response.body();
        if(clientCountDTO!=null){
            active.setText(String.valueOf(clientCountDTO.getActiveUserCount()));
            blacklisted.setText(String.valueOf(clientCountDTO.getBlacklistedUserCount()));
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