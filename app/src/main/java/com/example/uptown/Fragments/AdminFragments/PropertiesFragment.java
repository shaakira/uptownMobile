package com.example.uptown.Fragments.AdminFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uptown.Adapters.ViewPagerAdapter;
import com.example.uptown.Admin.Fragments.ActiveUserFragment;
import com.example.uptown.Admin.Fragments.BlacklistedUserFragment;
import com.example.uptown.Admin.Fragments.PendingPropertyFragment;
import com.example.uptown.Admin.Fragments.PublishedPropertyFragment;
import com.example.uptown.R;
import com.google.android.material.tabs.TabLayout;

public class PropertiesFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_properties_fragment, container, false);
        tabLayout = view.findViewById(R.id.tabId);
        viewPager = view.findViewById(R.id.viewPagerId);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new PendingPropertyFragment(), "Pending Properties");
        adapter.AddFragment(new PublishedPropertyFragment(), "Published Properties");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}