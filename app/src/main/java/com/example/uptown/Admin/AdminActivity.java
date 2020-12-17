package com.example.uptown.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Fragments.AdminFragments.AdminProfileFragment;
import com.example.uptown.Fragments.AdminFragments.ClientsFragment;
import com.example.uptown.Fragments.AdminFragments.ContactsFragment;
import com.example.uptown.Fragments.AdminFragments.DashboardFragment;
import com.example.uptown.Fragments.AdminFragments.PropertiesFragment;
import com.example.uptown.MainActivity;
import com.example.uptown.R;
import com.example.uptown.Services.UserService;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ResponseCallBack {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Integer id;
    LinearLayout dash_header, nav_header;
    TextView title;
    Dialog dialog;
    Button logoutBtn, cancelBtn;
    UserService userService;
    String properties = "properties";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        Intent intent = getIntent();
        String value = intent.getStringExtra("direct");
        SharedPreferences prefs = getSharedPreferences("shared", Context.MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        userService = new UserService();
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.admin_nav_logout).setVisible(false);
        menu.findItem(R.id.admin_nav_profile).setVisible(false);
        if (id != 0) {
            menu.findItem(R.id.admin_nav_logout).setVisible(true);
            menu.findItem(R.id.admin_nav_profile).setVisible(true);
        }
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        dash_header = findViewById(R.id.dash_header);
        nav_header = findViewById(R.id.nav_header);
        if (dash_header.getVisibility() == View.GONE) {
            dash_header.setVisibility(View.VISIBLE);
            nav_header.setVisibility(View.GONE);
        }

        title = findViewById(R.id.title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);

        if (properties.equals(value)) {

            navigationView.setCheckedItem(R.id.admin_nav_properties);
            Fragment fragment = new PropertiesFragment();
            loadFragment(fragment);
            setHeader("Properties");

        } else{
            navigationView.setCheckedItem(R.id.admin_nav_dashboard);
            Fragment fragment = new DashboardFragment();
            loadFragment(fragment);
        }
        dialog = new Dialog(AdminActivity.this);


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            Intent i=new Intent(getApplicationContext(),AdminActivity.class);
//           i.putExtra("direct","properties");
            startActivity(i);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.admin_nav_dashboard:
                fragment = new DashboardFragment();
                loadFragment(fragment);
                dash_header.setVisibility(View.VISIBLE);
                nav_header.setVisibility(View.GONE);
                break;
            case R.id.admin_nav_properties:
                fragment = new PropertiesFragment();
                loadFragment(fragment);
                setHeader("Properties");
                break;
            case R.id.admin_nav_clients:
                fragment = new ClientsFragment();
                loadFragment(fragment);
                setHeader("Clients");
                break;
            case R.id.admin_nav_contacts:
                fragment = new ContactsFragment();
                loadFragment(fragment);
                setHeader("Contacts Notification");
                break;
            case R.id.admin_nav_profile:
                fragment = new AdminProfileFragment();
                loadFragment(fragment);
                setHeader("Profile");
                break;
            case R.id.admin_nav_logout:
                drawerLayout.closeDrawer(GravityCompat.START);
                dialog.setContentView(R.layout.logout_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                logoutBtn = dialog.findViewById(R.id.logoutBtn);
                cancelBtn = dialog.findViewById(R.id.cancelBtn);
                logoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout();
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                ;
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setHeader(String header) {
        dash_header.setVisibility(View.GONE);
        nav_header.setVisibility(View.VISIBLE);
        title.setText(header);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void logout() {
        userService.logout(this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody result = (ResponseBody) response.body();
        if (result.string().equals("Success")) {
            SharedPreferences logout = getSharedPreferences("shared", MODE_PRIVATE);
            SharedPreferences.Editor editor = logout.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(this, "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}