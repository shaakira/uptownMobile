package com.example.uptown.Fragments.AppointmentFragments.Customer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uptown.Adapters.CustomerAcceptAdapter;
import com.example.uptown.Adapters.CustomerPendingAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Appointment;
import com.example.uptown.R;
import com.example.uptown.Services.AppointmentService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ScheduledFragment extends Fragment implements ResponseCallBack {
    View view;
    AppointmentService appointmentService;
    Integer id;
    GridView gridView;
    public ScheduledFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.scheduled_fragment,container,false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        appointmentService = new AppointmentService();
        id = prefs.getInt("id", 0);
        gridView = (GridView) view.findViewById(R.id.acceptGrid);
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
    public void getAppointments(int customerId){
        appointmentService.customerAcceptedAppointment(customerId,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Appointment> appointments=(List<Appointment>) response.body();
        if(!appointments.isEmpty()){
            CustomerAcceptAdapter adapter=new CustomerAcceptAdapter(appointments,getContext());
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
