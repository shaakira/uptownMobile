package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.uptown.Adapters.IntroViewAdapter;
import com.example.uptown.Model.ScreenItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewAdapter introViewAdapter;
    TabLayout tabLayout;
    int position=0;
    Button startedBtn;
    Animation btnAnim;
    TextView skip,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);
        tabLayout=findViewById(R.id.tabLayout);
        startedBtn=findViewById(R.id.startedBtn);

        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Say goodbye to the stress of managing your property and maximise your investment with our professional property management.", R.drawable.l3,"LANDLORDS"));
        mList.add(new ScreenItem("Search through the current listings of our properties throughout the Uptown and find one of you-both short and long-term lease available.", R.drawable.l2,"FIND A RENTAL"));
        mList.add(new ScreenItem("We value our tenants and provide best quality services.\n\n", R.drawable.l1,"TENANT"));

        screenPager = findViewById(R.id.screenPager);
        introViewAdapter = new IntroViewAdapter(this, mList);
        screenPager.setAdapter(introViewAdapter);

        tabLayout.setupWithViewPager(screenPager);
        startedBtn.setVisibility(View.INVISIBLE);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        skip=findViewById(R.id.skipTxt);
        next=findViewById(R.id.nextText);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=screenPager.getCurrentItem();
                if(position<mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position==mList.size()-1){
                    loadLastScreen();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position=mList.size()-1;
                screenPager.setCurrentItem(position);
                loadLastScreen();

            }
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1){
                    loadLastScreen();
                }
                else{

                    startedBtn.setVisibility(View.INVISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    skip.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        startedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private void  loadLastScreen(){
        next.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.INVISIBLE);
        startedBtn.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        startedBtn.setAnimation(btnAnim);
    }
}