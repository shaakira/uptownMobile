package com.example.uptown.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.SingleAgent;
import com.example.uptown.SingleProperty;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AgentAdapter extends ArrayAdapter<User> {
    private List<User> agents;
    Context ctx;

    public AgentAdapter(List<User> agents, Context ctx) {
        super(ctx, R.layout.agents_list,agents);
        this.agents = agents;
        this.ctx = ctx;
    }
    private static class ViewHolder{
        ImageView image;
        TextView fName;
        TextView lName;
        TextView email;
        TextView phone;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User dataModel = getItem(position);
        ViewHolder viewHolder;
        View result = convertView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.agents_list, parent, false);
            viewHolder.fName = (TextView) convertView.findViewById(R.id.firstName);
            viewHolder.lName = (TextView) convertView.findViewById(R.id.lastName);
            viewHolder.email = (TextView) convertView.findViewById(R.id.email);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.phone);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.agentPic);
            convertView.setTag(viewHolder);
            result = convertView;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.fName.setText(dataModel.getFirstName());
        viewHolder.lName.setText(dataModel.getLastName());
        viewHolder.email.setText(dataModel.getEmail());
//        viewHolder.phone.setText(dataModel.getPhone());
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.image);
        viewHolder.image.setOnClickListener((view -> {
            Intent intent = new Intent(getContext(), SingleAgent.class);
            intent.putExtra("agentId", dataModel.getId());
            getContext().startActivity(intent);
        }));
        return convertView;

    }
}
