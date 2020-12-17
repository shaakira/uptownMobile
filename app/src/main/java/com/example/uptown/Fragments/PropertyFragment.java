package com.example.uptown.Fragments;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.uptown.Adapters.PropertyAdapter;
import com.example.uptown.AddProperty.AddPropertyActivity1;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.FilterScreen;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.SearchByCityScreen;
import com.example.uptown.Services.PropertyService;
import com.example.uptown.Services.UserService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class PropertyFragment extends Fragment implements ResponseCallBack {

    ImageView add, expandBtn;
    PropertyService propertyService;
    UserService userService;
    Integer id;
    GridView gridView;
    LinearLayout filter;
    SearchView searchView;
    Dialog dialog;
    CardView expandCard;
    LinearLayout cardView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_properties, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        propertyService = new PropertyService();
        userService = new UserService();
        getProfile(id);
        SearchView(view);
        dialog = new Dialog(getContext());
        add = view.findViewById(R.id.addProp);
        gridView = (GridView) view.findViewById(R.id.propertyGrid);
        getProperties();
        filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FilterScreen.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create(filter, "filter"));
                startActivity(intent, options.toBundle());
            }
        });


        return view;
    }

    public void getProperties() {
        propertyService.getPublishedProperty(this);
    }

    public void getProfile(int id) {
        userService.GetProfile(id, getUserResponse());
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Property> properties = (List<Property>) response.body();
        if (!properties.isEmpty()) {
            PropertyAdapter adapter = new PropertyAdapter(properties, getContext());
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


    public ResponseCallBack getUserResponse() {
        ResponseCallBack profileResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                User user = (User) response.body();
                if (user != null && id!=0) {
                    if (user.getuType().equals("customer") || user.getuType().equals("admin")) {
                        onAddClick();
                    }
                    if (user.getuType().equals("agent") || user.getuType().equals("owner")) {
                       add.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent = new Intent(getContext(), AddPropertyActivity1.class);
                               startActivity(intent);
                           }
                       });
                    }
                } else {
                    onAddClick();
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
        };
        return profileResponseCallBack;
    }

    public void SearchView(View view) {
        searchView = view.findViewById(R.id.search);
        searchView.setQueryHint("City");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent intent = new Intent(getContext(), SearchByCityScreen.class);
                intent.putExtra("city", s);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    public void onAddClick() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.add_property_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                expandBtn = dialog.findViewById(R.id.expandBtn);
                expandCard = dialog.findViewById(R.id.expandableView);
                cardView = dialog.findViewById(R.id.cardView);
                expandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (expandCard.getVisibility() == View.GONE) {
                            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                            expandCard.setVisibility(View.VISIBLE);
                            expandBtn.setImageResource(R.drawable.upload);

                        } else {
                            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                            expandCard.setVisibility(View.GONE);
                            expandBtn.setImageResource(R.drawable.expand_arrow);
                        }
                    }
                });

            }
        });
    }
}
