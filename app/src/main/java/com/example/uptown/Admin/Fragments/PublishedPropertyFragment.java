package com.example.uptown.Admin.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.uptown.Admin.Adapters.PropertiesAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.Services.PropertyService;
import com.example.uptown.Services.UserService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class PublishedPropertyFragment extends Fragment implements ResponseCallBack {

    GridView gridView;
    PropertyService propertyService;
    Integer id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_published_property_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        propertyService=new PropertyService();
        id = prefs.getInt("id", 0);
        gridView=view.findViewById(R.id.publishedGrid);
        getProperties();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        };
    }
    public void getProperties(){
        propertyService.publishedProperties(this);
    }
    @Override
    public void onSuccess(Response response) throws IOException {
        List<Property> properties=(List<Property>) response.body();
        if(!properties.isEmpty()){
            PropertiesAdapter adapter= new PropertiesAdapter(properties,getContext(),"published");
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
}