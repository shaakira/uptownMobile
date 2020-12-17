package com.example.uptown.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.SingleProperty;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPropertyAdapter extends ArrayAdapter<Property> {
    private List<Property> properties;
    Context ctx;

    public MyPropertyAdapter(List<Property> data, Context context) {
        super(context, R.layout.agent_property, data);
        this.properties = data;
        this.ctx = context;
    }

    private static class ViewHolder {
        ImageView propImage;
        TextView heading;
        TextView price;
        TextView area;
        TextView beds;
        TextView baths;
        TextView garage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Property dataModel = getItem(position);
        ViewHolder viewHolder;
        View result = convertView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.agent_property, parent, false);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.area = (TextView) convertView.findViewById(R.id.area);
            viewHolder.heading = (TextView) convertView.findViewById(R.id.description);
            viewHolder.beds = (TextView) convertView.findViewById(R.id.beds);
            viewHolder.propImage = (ImageView) convertView.findViewById(R.id.propImage);
            viewHolder.baths = (TextView) convertView.findViewById(R.id.baths);
            viewHolder.garage = (TextView) convertView.findViewById(R.id.garage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        viewHolder.price.setText(String.valueOf(dataModel.getRate()));
        viewHolder.heading.setText(dataModel.getHeading());
        viewHolder.area.setText(String.valueOf(dataModel.getArea()));
        viewHolder.baths.setText(String.valueOf(dataModel.getBaths()));
        viewHolder.beds.setText(String.valueOf(dataModel.getRooms()));
        viewHolder.garage.setText(String.valueOf(dataModel.getGarage()));
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage1();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.propImage);
        viewHolder.propImage.setOnClickListener((view -> {
            Intent intent = new Intent(getContext(), SingleProperty.class);
            intent.putExtra("propId", dataModel.getId());
            getContext().startActivity(intent);
        }));
        return result;

    }

}
