package com.example.uptown.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uptown.Model.Appointment;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerPendingAdapter extends RecyclerView.Adapter<CustomerPendingAdapter.ViewHolder> {

    private List<Appointment> appointments;
    private Context ctx;

    public CustomerPendingAdapter(List<Appointment> data, Context context) {

        this.appointments = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Appointment dataModel = appointments.get(position);
        String input_date = dataModel.getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        viewHolder.day.setText(finalDay);
        viewHolder.date.setText(dataModel.getDate());
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
        viewHolder.fName.setText(dataModel.getProperty().getUser().getFirstName());
        viewHolder.lName.setText(dataModel.getProperty().getUser().getLastName());
        viewHolder.email.setText(dataModel.getProperty().getUser().getEmail());
        viewHolder.address.setText(dataModel.getProperty().getUser().getAddress());
        viewHolder.property.setText(dataModel.getProperty().getHeading());
        viewHolder.propertyId.setText(String.valueOf(dataModel.getProperty().getId()));
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getProperty().getUser().getImage();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.image);



    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public int getAppointmentAt(int position){
        Appointment appointment=appointments.get(position);
        return appointment.getId();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView date;
        TextView time;
        ImageView image;
        TextView fName;
        TextView lName;
        TextView email;
        TextView address;
        TextView property;
        TextView propertyId;
        RelativeLayout foreground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day =  itemView.findViewById(R.id.day);
            date =  itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            image =  itemView.findViewById(R.id.image);
            fName =  itemView.findViewById(R.id.firstName);
            lName =  itemView.findViewById(R.id.lastName);
            email =  itemView.findViewById(R.id.email);
            address =  itemView.findViewById(R.id.address);
            property =  itemView.findViewById(R.id.property);
            propertyId = itemView.findViewById(R.id.propId);
            foreground=itemView.findViewById(R.id.foreground);
        }
    }

}
