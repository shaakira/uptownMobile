package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.uptown.Admin.AdminActivity;

public class SplashScreen extends AppCompatActivity {

    private  static int SPLASH_SCREEN=2000;
    Animation topAnim, bottomAnim;
    CardView logo;
    TextView logo1, logo2, slogan;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences prefs = getSharedPreferences("shared", Context.MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        logo1 = findViewById(R.id.logo1);
        logo2 = findViewById(R.id.logo2);
        slogan = findViewById(R.id.slogan);
        logo.setAnimation(topAnim);
        logo1.setAnimation(bottomAnim);
        logo2.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(id==18){
                    Intent intent=new Intent(SplashScreen.this,
                            AdminActivity.class);

                    Pair[] pairs=new Pair[2];
                    pairs[0]=new Pair<View,String>(logo1,"logo1");
                    pairs[1]=new Pair<View,String>(logo2,"logo2");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                }
                else{
                    Intent intent=new Intent(SplashScreen.this,
                            MainActivity.class);

                    Pair[] pairs=new Pair[2];
                    pairs[0]=new Pair<View,String>(logo1,"logo1");
                    pairs[1]=new Pair<View,String>(logo2,"logo2");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                }


            }
        },SPLASH_SCREEN);

    }
}