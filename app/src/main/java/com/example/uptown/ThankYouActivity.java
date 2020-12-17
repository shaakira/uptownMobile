package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class ThankYouActivity extends AppCompatActivity {

    Timer timer;
    ImageView imageView;
    Integer propId;
    Runnable swapImage = new Runnable() {
        @Override
        public void run() {
           imageView.setVisibility(View.INVISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_thank_you);
        imageView=findViewById(R.id.rock);
        Intent intent = getIntent();
        propId = intent.getIntExtra("propId", 0);
        imageView.postDelayed(swapImage, 500);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(ThankYouActivity.this,SingleProperty.class);
                intent.putExtra("propId",propId);
                startActivity(intent);
            }
        },2000);


    }
}