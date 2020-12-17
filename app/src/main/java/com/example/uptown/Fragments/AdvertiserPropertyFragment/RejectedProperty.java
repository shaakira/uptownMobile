package com.example.uptown.Fragments.AdvertiserPropertyFragment;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uptown.Adapters.AdvertiserPropertyAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.Services.PropertyService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class RejectedProperty extends Fragment implements ResponseCallBack {

    View view;
    Integer id;
    GridView gridView;
    PropertyService propertyService;
    LinearLayout empty;

    public RejectedProperty() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_rejected_property, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        propertyService = new PropertyService();
        id = prefs.getInt("id", 0);
        gridView = view.findViewById(R.id.rp_Grid);
        empty = view.findViewById(R.id.empty);
        getProperties(id);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        };
    }
    public void getProperties(int advertiserID) {
        propertyService.myRejectedProperties(advertiserID, this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Property> properties = (List<Property>) response.body();
        if (!properties.isEmpty()) {
            AdvertiserPropertyAdapter adapter = new AdvertiserPropertyAdapter(properties, getContext(),"rejected",RejectedProperty.this);
            gridView.setAdapter(adapter);
           visibleList();
        } else {
         emptyList();
        }
    }
    public void visibleList() {
        gridView.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
    }

    public void emptyList() {
        gridView.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
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