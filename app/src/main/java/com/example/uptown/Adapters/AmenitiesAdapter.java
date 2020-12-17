package com.example.uptown.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.uptown.R;

import java.util.ArrayList;

public class AmenitiesAdapter extends ArrayAdapter<String> {
    public AmenitiesAdapter(Context context, ArrayList<String> amenities) {
        super(context, 0, amenities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String am = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.amenities, parent, false);
        }
        // Lookup view for data population
        TextView ament = (TextView) convertView.findViewById(R.id.ament);
        // Populate the data into the template view using the data object

        ament.setText(am);
        // Return the completed view to render on screen
        return convertView;
    }
}
