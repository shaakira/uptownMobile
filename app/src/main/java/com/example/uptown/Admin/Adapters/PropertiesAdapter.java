package com.example.uptown.Admin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.uptown.Admin.AdminPropertyScreen;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertiesAdapter extends ArrayAdapter<Property> {
    private List<Property> properties;
    Context context;
    String type;

    public PropertiesAdapter(List<Property> data, Context ctx,String type) {
        super(ctx, R.layout.pending_property_item, data);
        this.properties = data;
        this.context = ctx;
        this.type=type;
    }

    private static class ViewHolder {
        ImageView image;
        TextView price;
        TextView heading;
        TextView propCity;
        TextView propProv;
        LinearLayout view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Property dataModel = getItem(position);
        ViewHolder viewHolder;
        View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.pending_property_item, parent, false);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.image);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.heading = (TextView) convertView.findViewById(R.id.description);
            viewHolder.propCity = (TextView) convertView.findViewById(R.id.propCity);
            viewHolder.propProv = (TextView) convertView.findViewById(R.id.propProv);
            viewHolder.view=(LinearLayout)convertView.findViewById(R.id.view);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage1();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.image);
        viewHolder.price.setText(String.valueOf(dataModel.getRate()));
        viewHolder.heading.setText(dataModel.getHeading());
        viewHolder.propCity.setText(dataModel.getCity());
        viewHolder.propProv.setText(dataModel.getProvince());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AdminPropertyScreen.class);
                intent.putExtra("propId", dataModel.getId());
                intent.putExtra("type",type);
                getContext().startActivity(intent);
            }
        });
        return result;
    }
}
