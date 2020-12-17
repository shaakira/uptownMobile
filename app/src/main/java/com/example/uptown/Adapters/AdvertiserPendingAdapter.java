package com.example.uptown.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uptown.AdvertiserAppointments;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Appointment;
import com.example.uptown.R;
import com.example.uptown.Services.AppointmentService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class AdvertiserPendingAdapter extends ArrayAdapter<Appointment> implements ResponseCallBack {
    private List<Appointment> appointments;
    Context ctx;
    BottomSheetDialog bottomSheetDialog;
    AppointmentService appointmentService;
    int Position;

    public AdvertiserPendingAdapter(List<Appointment> appointments, Context context) {
        super(context, R.layout.advertiser_pending_layout, appointments);
        this.appointments = appointments;
        this.ctx = context;
    }



    private static class ViewHolder {
        TextView month;
        TextView day;
        TextView date;
        TextView time;
        TextView name;
        TextView heading;
        MaterialButton viewBtn;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Appointment dataModel = getItem(position);
        ViewHolder viewHolder;
        View result = convertView;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.advertiser_pending_layout, parent, false);
            viewHolder.month = convertView.findViewById(R.id.month);
            viewHolder.day = convertView.findViewById(R.id.day);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.heading = convertView.findViewById(R.id.description);
            viewHolder.viewBtn = convertView.findViewById(R.id.viewBtn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        String input_date = dataModel.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        try {
            dt1 = format.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format1 = new SimpleDateFormat("MMMM");
        String finalMonth = format1.format(dt1);
        DateFormat format2 = new SimpleDateFormat("dd");
        String finalDay = format2.format(dt1);
        DateFormat format3 = new SimpleDateFormat("EEE");
        String finalDate = format3.format(dt1);
        viewHolder.month.setText(finalMonth);
        viewHolder.day.setText(finalDate);
        viewHolder.date.setText(finalDay);
        String dateString3 = dataModel.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date3 = null;
        try {
            date3 = sdf.parse(dateString3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
        String newTime = sdf2.format(date3);
        viewHolder.time.setText(newTime);
        viewHolder.heading.setText(dataModel.getProperty().getHeading());
        viewHolder.name.setText(dataModel.getName());
        appointmentService=new AppointmentService();
        viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.pending_appointment_dialog, parent, false);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                Position=position;
                TextView d_name = bottomSheetDialog.findViewById(R.id.d_name);
                d_name.setText(dataModel.getName());
                TextView d_phone = bottomSheetDialog.findViewById(R.id.d_phone);
                d_phone.setText(String.valueOf(dataModel.getCustomerContact()));
                TextView d_email = bottomSheetDialog.findViewById(R.id.d_email);
                d_email.setText(dataModel.getEmail());
                TextView d_heading = bottomSheetDialog.findViewById(R.id.d_heading);
                d_heading.setText(dataModel.getProperty().getHeading());
                TextView d_note = bottomSheetDialog.findViewById(R.id.d_note);
                d_note.setText(dataModel.getNote());
                TextView d_initial = bottomSheetDialog.findViewById(R.id.d_initial);
                String initial = dataModel.getName();
                char i = initial.charAt(0);
                d_initial.setText(String.valueOf(i));
                TextView d_time = bottomSheetDialog.findViewById(R.id.d_time);
                d_time.setText(newTime);
                String input_date = dataModel.getDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date dt1 = null;
                try {
                    dt1 = format.parse(input_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format1 = new SimpleDateFormat("EEE, dd MMM");
                String newDate = format1.format(dt1);
                TextView d_date=bottomSheetDialog.findViewById(R.id.d_date);
                d_date.setText(newDate);
                MaterialButton rejectBtn=bottomSheetDialog.findViewById(R.id.rejectBtn);
                MaterialButton acceptBtn=bottomSheetDialog.findViewById(R.id.acceptBtn);
                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acceptAppointment(dataModel.getId());
                        bottomSheetDialog.dismiss();
                    }
                });
                rejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rejectAppointment(dataModel.getId());
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });

        return result;
    }
    public void acceptAppointment(int appointmentId){
        appointmentService.acceptAppointment(appointmentId,this);
    }
    public void rejectAppointment(int appointmentId){
        appointmentService.rejectAppointment(appointmentId,getRejectedResponse());
    }
    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody=(ResponseBody) response.body();
        if(responseBody.string().equals("Success")){

                appointments.remove(Position);
                notifyDataSetChanged();
                Toast.makeText(ctx, "Appointment Accepted", Toast.LENGTH_SHORT).show();

            if(appointments.isEmpty()){
                Intent intent1 = new Intent(getContext(), AdvertiserAppointments.class);
                getContext().startActivity(intent1);
            }

        }
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(ctx, "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
    public ResponseCallBack getRejectedResponse() {
        ResponseCallBack appointmentResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {
                    appointments.remove(Position);
                    notifyDataSetChanged();
                    Toast.makeText(ctx, "Appointment Rejected", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(ctx, "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return appointmentResponseCallBack;
    }
}
