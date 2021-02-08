package com.example.uptown.Fragments.AppointmentFragments.Customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uptown.Adapters.CustomerPendingAdapter;
import com.example.uptown.Adapters.DeleteAdapter;
import com.example.uptown.Adapters.PropertyAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Response.CustomerAppointmentDTO;
import com.example.uptown.Model.Appointment;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.Services.AppointmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PendingFragment extends Fragment implements ResponseCallBack {

    View view;
    AppointmentService appointmentService;
    Integer id;
    RecyclerView gridView, backGround;
    TextView accept, decline, pending, all;

    public PendingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        appointmentService = new AppointmentService();
        id = prefs.getInt("id", 0);
        gridView = (RecyclerView) view.findViewById(R.id.pendingGrid);
        backGround = view.findViewById(R.id.backGround);
        accept = view.findViewById(R.id.accept);
        decline = view.findViewById(R.id.decline);
        pending = view.findViewById(R.id.pending);
        all = view.findViewById(R.id.all);
        getAppointments(id);
        getAppointmentsCount(id);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
        ;
    }

    public void getAppointments(int customerId) {
        appointmentService.customerPendingAppointments(customerId, this);
    }

    public void deleteAppointment(int appointmentId) {
        appointmentService.deleteAppointment(appointmentId, getAppointmentDeleteResponse());
    }

    public void getAppointmentsCount(int customerId) {
        appointmentService.getAppointmentsCount(customerId, getAppointmentCountResponse());
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        List<Appointment> appointments = (List<Appointment>) response.body();
        if (!appointments.isEmpty()) {
            List<Appointment> aList = new ArrayList<>(appointments);
            CustomerPendingAdapter adapter = new CustomerPendingAdapter(appointments, getContext());
            gridView.setAdapter(adapter);
            gridView.setHasFixedSize(true);
            gridView.setLayoutManager(new LinearLayoutManager(getContext()));
            DeleteAdapter deleteAdapter = new DeleteAdapter(aList, getContext());
            backGround.setAdapter(deleteAdapter);
            backGround.setHasFixedSize(true);
            backGround.setLayoutManager(new LinearLayoutManager(getContext()));


            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    deleteAppointment(adapter.getAppointmentAt(viewHolder.getAdapterPosition()));
                    appointments.remove(viewHolder.getAdapterPosition());
                    aList.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                    deleteAdapter.notifyDataSetChanged();

                }
            }).attachToRecyclerView(gridView);

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

    public ResponseCallBack getAppointmentDeleteResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {
                    Toast.makeText(getContext(), "Successfully appointment deleted", Toast.LENGTH_SHORT).show();

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
        return userResponseCallBack;
    }

    public ResponseCallBack getAppointmentCountResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                CustomerAppointmentDTO appointmentDTO = (CustomerAppointmentDTO) response.body();
                int allCount=appointmentDTO.getAcceptedCount()+appointmentDTO.getDeclinedCount()+appointmentDTO.getPendingCount();
                accept.setText(String.valueOf(appointmentDTO.getAcceptedCount()));
                decline.setText(String.valueOf(appointmentDTO.getDeclinedCount()));
                pending.setText(String.valueOf(appointmentDTO.getPendingCount()));
                all.setText(String.valueOf(allCount));

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
        return userResponseCallBack;
    }
}
