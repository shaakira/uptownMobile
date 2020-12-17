package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.uptown.Adapters.ViewPagerAdapter;
import com.example.uptown.Fragments.AppointmentFragments.Advertiser.AdvertiserPendingFragment;
import com.example.uptown.Fragments.AppointmentFragments.Advertiser.AdvertiserUpcomingFragment;
import com.google.android.material.tabs.TabLayout;

public class AdvertiserAppointments extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_advertiser_appointments);
        tabLayout = findViewById(R.id.tabId);
        viewPager = findViewById(R.id.viewPagerId);
        backBtn = findViewById(R.id.backBtn);
        //adding fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new AdvertiserPendingFragment(), "Pending");
        adapter.AddFragment(new AdvertiserUpcomingFragment(), "Upcoming");


        //set adapter
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdvertiserAppointments.super.onBackPressed();
            }
        });
    }
}