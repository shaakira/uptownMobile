package com.example.uptown.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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

public class AdvertiserUpcomingAdapter extends ArrayAdapter<Appointment> implements ResponseCallBack {
    private List<Appointment> appointments;
    Context ctx;
    BottomSheetDialog bottomSheetDialog;
    AppointmentService appointmentService;
    Dialog dialog,dialog1;
    int Position;

    public AdvertiserUpcomingAdapter(List<Appointment> appointments, Context context) {
        super(context, R.layout.advertiser_upcoming_layout, appointments);
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
        MaterialButton editBtn;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Appointment dataModel = getItem(position);
       ViewHolder viewHolder;
        View result = convertView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.advertiser_upcoming_layout, parent, false);
            viewHolder.month = convertView.findViewById(R.id.month);
            viewHolder.day = convertView.findViewById(R.id.day);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.heading = convertView.findViewById(R.id.description);
            viewHolder.editBtn = convertView.findViewById(R.id.editBtn);
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
        dialog = new Dialog(getContext());
        dialog1= new Dialog(getContext());
        viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(ctx,viewHolder.editBtn);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_view:
                                bottomSheetDialog = new BottomSheetDialog(getContext());
                                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.upcoming_appointment_dialog, parent, false);
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
                                return true;
                            case R.id.popup_cancel:
                                dialog1.dismiss();
                                dialog.setContentView(R.layout.cancel_appointment_popup);
                                dialog.getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                MaterialButton cancel=dialog.findViewById(R.id.cancel);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                MaterialButton cancelBtn=dialog.findViewById(R.id.cancelBtn);
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Position=position;
                                        cancelAppointment(dataModel.getId());
                                    }
                                });
                                return true;

                            case R.id.popup_done:
                                dialog.dismiss();
                                dialog1.setContentView(R.layout.done_appointment_popup);
                                dialog1.getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog1.show();
                                MaterialButton cancel1=dialog1.findViewById(R.id.cancel);
                                cancel1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                    }
                                });
                                MaterialButton doneBtn=dialog1.findViewById(R.id.doneBtn);
                                doneBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Position=position;
                                        moveToDone(dataModel.getId());
                                    }
                                });

                                return true;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return result;
    }
    public void cancelAppointment(int appointmentId){
        appointmentService.cancelAppointment(appointmentId,this);
    }
    public void moveToDone(int appointmentId){
        appointmentService.completedAppointment(appointmentId,getDoneResponse());
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody=(ResponseBody) response.body();
        if(responseBody.string().equals("Success")){
            dialog.dismiss();
            appointments.remove(Position);
            notifyDataSetChanged();
            Toast.makeText(ctx, "Appointment Canceled", Toast.LENGTH_SHORT).show();
            if(appointments.isEmpty()){
                Intent intent1 = new Intent(getContext(), AdvertiserAppointments.class);
                getContext().startActivity(intent1);
            }
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
    public ResponseCallBack getDoneResponse() {
        ResponseCallBack appointmentResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {
                    dialog1.dismiss();
                    appointments.remove(Position);
                    notifyDataSetChanged();
                    Toast.makeText(ctx, "Successfully moved to done", Toast.LENGTH_SHORT).show();
                }

                if(appointments.isEmpty()){
                    Intent intent1 = new Intent(getContext(), AdvertiserAppointments.class);
                    getContext().startActivity(intent1);
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
