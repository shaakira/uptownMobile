package com.example.uptown.Fragments.AppointmentFragments.Advertiser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.uptown.Adapters.AdvertiserUpcomingAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Appointment;
import com.example.uptown.R;
import com.example.uptown.Services.AppointmentService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;


public class AdvertiserUpcomingFragment extends Fragment implements ResponseCallBack {
    View view;
    Integer id;
    AppointmentService appointmentService;
    GridView gridView;
    LinearLayout empty;
    public AdvertiserUpcomingFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.advertiser_upcoming_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        appointmentService = new AppointmentService();
        id = prefs.getInt("id", 0);
        gridView=view.findViewById(R.id.acceptGrid);
        empty=view.findViewById(R.id.empty);
        getAppointments(id);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        };
    }

    public void getAppointments(int advertiserId){
        appointmentService.findUpcomingAppointments(advertiserId,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Appointment> appointments=(List<Appointment>) response.body();
        if(!appointments.isEmpty()){
            AdvertiserUpcomingAdapter adapter=new AdvertiserUpcomingAdapter(appointments,getContext());
            gridView.setAdapter(adapter);
            gridView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        else{
            gridView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
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
