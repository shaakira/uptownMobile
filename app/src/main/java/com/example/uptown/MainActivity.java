package com.example.uptown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.uptown.Fragments.AboutFragment;
import com.example.uptown.Fragments.AgentsFragment;
import com.example.uptown.Fragments.ProfileFragment;
import com.example.uptown.Fragments.PropertyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    String profile="profile";
    String about="about";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        Intent intent=getIntent();
        String value=intent.getStringExtra("direct");
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        if(profile.equals(value)){
            Fragment fragment = new ProfileFragment();
            loadFragment(fragment);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
        }
        else if(about.equals(value)){
            Fragment fragment = new AboutFragment();
            loadFragment(fragment);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_about);
        }
        else{
            loadFragment(new PropertyFragment());
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
            Integer id = prefs.getInt("id", 0);
            switch (item.getItemId()) {
                case R.id.bottom_nav_dashboard:
                    fragment = new PropertyFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bottom_nav_agents:
                    fragment = new AgentsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bottom_nav_about:
                    fragment = new AboutFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bottom_nav_profile:
                    if(id==0){
                        Intent intent=new Intent(MainActivity.this,LandingScreen.class);
                        startActivity(intent);
                    }
                    else {
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                    }


            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}