package com.example.uptown.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uptown.Model.ScreenItem;
import com.example.uptown.R;

import java.util.List;

public class IntroViewAdapter extends PagerAdapter {
Context mContext;
List<ScreenItem> screenItemList;


    public IntroViewAdapter(Context mContext, List<ScreenItem> screenItemList) {
        this.mContext = mContext;
        this.screenItemList = screenItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen =inflater.inflate(R.layout.layout_screen,null);

        ImageView introImage=layoutScreen.findViewById(R.id.introImage);
        TextView description=layoutScreen.findViewById(R.id.introText);
        TextView headingText=layoutScreen.findViewById(R.id.headingText);

        headingText.setText(screenItemList.get(position).getHeading());
        description.setText(screenItemList.get(position).getDescription());
        introImage.setImageResource(screenItemList.get(position).getImage());
        container.addView(layoutScreen);
        return  layoutScreen;
    }

    @Override
    public int getCount() {
        return screenItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }
}
