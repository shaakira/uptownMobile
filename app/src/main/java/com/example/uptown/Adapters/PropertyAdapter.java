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

public class PropertyAdapter extends ArrayAdapter<Property> {
    private List<Property> properties;
    Context ctx;

    public PropertyAdapter(List<Property> data, Context context) {
        super(context, R.layout.property_list, data);
        this.properties = data;
        this.ctx = context;
    }

    private static class ViewHolder {
        ImageView propImage;
        TextView heading;
        TextView price;
        TextView location;
        TextView province;
        TextView propType;
        ImageView shareBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Property dataModel = getItem(position);
        ViewHolder viewHolder;
        View result = convertView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.property_list, parent, false);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.heading = (TextView) convertView.findViewById(R.id.description);
            viewHolder.propType = (TextView) convertView.findViewById(R.id.propType);
            viewHolder.location = (TextView) convertView.findViewById(R.id.propCity);
            viewHolder.province = (TextView) convertView.findViewById(R.id.propProvince);
            viewHolder.propImage = (ImageView) convertView.findViewById(R.id.propImage);
            viewHolder.shareBtn = (ImageView) convertView.findViewById(R.id.send);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        viewHolder.price.setText(String.valueOf(dataModel.getRate()));
        viewHolder.propType.setText(dataModel.getpType());
        viewHolder.heading.setText(dataModel.getHeading());
        viewHolder.location.setText(dataModel.getCity());
        viewHolder.province.setText(dataModel.getProvince());
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage1();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.propImage);
        viewHolder.propImage.setOnClickListener((view -> {
            Intent intent = new Intent(getContext(), SingleProperty.class);
            intent.putExtra("propId", dataModel.getId());
            getContext().startActivity(intent);
        }));
        return convertView;

    }
}
