package com.example.uptown.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.uptown.Model.Appointment;
import com.example.uptown.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerAcceptAdapter extends ArrayAdapter<Appointment> {

    private List<Appointment> appointments;
    Context ctx;

    public CustomerAcceptAdapter(List<Appointment> data, Context context) {
        super(context, R.layout.pending_layout, data);
        this.appointments = data;
        this.ctx = context;
    }

    private static class ViewHolder {
        TextView date;
        TextView time;
        TextView heading;
        CardView cardView;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Appointment dataModel = getItem(position);
        ViewHolder viewHolder;
        View result = convertView;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.scheduled_layout, parent, false);
            viewHolder.heading = (TextView) convertView.findViewById(R.id.description);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        String input_date = dataModel.getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("MMM dd yyyy");
        String finalDay = format2.format(dt1);
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
        DateFormat format5 = new SimpleDateFormat("dd MMMM");
        String dayss = format5.format(dt1);
        DateFormat format6 = new SimpleDateFormat("yyyy");
        String years = format6.format(dt1);
        DateFormat format4 = new SimpleDateFormat("EEE");
        String date = format4.format(dt1);


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.appointment_bottomsheet, parent, false);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                TextView days = bottomSheetDialog.findViewById(R.id.days);
                days.setText(dayss);
                TextView year = bottomSheetDialog.findViewById(R.id.year);
                year.setText(years);
                TextView times = bottomSheetDialog.findViewById(R.id.times);
                times.setText(newTime);
                TextView name = bottomSheetDialog.findViewById(R.id.name);
                name.setText(dataModel.getProperty().getUser().getFirstName());
                TextView email = bottomSheetDialog.findViewById(R.id.email);
                email.setText(dataModel.getProperty().getUser().getEmail());
                TextView heading = bottomSheetDialog.findViewById(R.id.head);
                heading.setText(dataModel.getProperty().getHeading());
                TextView loc = bottomSheetDialog.findViewById(R.id.loc);
                loc.setText(dataModel.getProperty().getCity());
                TextView sun = bottomSheetDialog.findViewById(R.id.sun);
                TextView mon = bottomSheetDialog.findViewById(R.id.mon);
                TextView tue = bottomSheetDialog.findViewById(R.id.tue);
                TextView wed = bottomSheetDialog.findViewById(R.id.wed);
                TextView thu = bottomSheetDialog.findViewById(R.id.thu);
                TextView fri = bottomSheetDialog.findViewById(R.id.fri);
                TextView sat = bottomSheetDialog.findViewById(R.id.sat);

                if (date.equals("Sun")) {
                    sun.setBackgroundResource(R.drawable.day_border);
                    sun.setPadding(20, 20, 20, 20);
                    sun.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                } else if (date.equals("Mon")) {
                    mon.setBackgroundResource(R.drawable.day_border);
                    mon.setPadding(20, 20, 20, 20);
                    mon.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                } else if (date.equals("Tue")) {
                    tue.setBackgroundResource(R.drawable.day_border);
                    tue.setPadding(20, 20, 20, 20);
                    tue.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                } else if (date.equals("Wed")) {
                    wed.setBackgroundResource(R.drawable.day_border);
                    wed.setPadding(20, 20, 20, 20);
                    wed.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                } else if (date.equals("Thu")) {
                    thu.setBackgroundResource(R.drawable.day_border);
                    thu.setPadding(20, 20, 20, 20);
                    thu.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                } else if (date.equals("Fri")) {
                    fri.setBackgroundResource(R.drawable.day_border);
                    fri.setPadding(20, 20, 20, 20);
                    fri.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                } else if (date.equals("Sat")) {
                    sat.setBackgroundResource(R.drawable.day_border);
                    sat.setPadding(20, 20, 20, 20);
                    sat.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
                }


            }
        });


        return result;
    }
}
