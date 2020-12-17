package com.example.uptown.Admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopAgentAdapter extends ArrayAdapter<User> {
    private List<User> agents;
    Context context;

    public TopAgentAdapter(List<User> data, Context ctx) {
        super(ctx, R.layout.top_agents, data);
        this.agents = data;
        this.context = ctx;
    }

    private static class ViewHolder {
        TextView name;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User dataModel = getItem(position);
        ViewHolder viewHolder;

        View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.top_agents, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.image = convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        viewHolder.name.setText(dataModel.getFirstName());
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.image);
        return result;
    }
}
