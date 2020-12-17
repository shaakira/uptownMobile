package com.example.uptown.Admin.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.ColorInt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uptown.Admin.Fragments.ActiveUserFragment;
import com.example.uptown.Admin.Fragments.BlacklistedUserFragment;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Fragments.AdminFragments.ClientsFragment;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.UserService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ClientsAdapter extends ArrayAdapter<User> implements ResponseCallBack {

    private List<User> users;
    Context context;
    String type;
    UserService userService;
    BottomSheetDialog bottomSheetDialog;
    int Position;
    ResponseCallBack responseCallBack;

    public ClientsAdapter(List<User> data, Context ctx, String type,ResponseCallBack responseCallBack) {
        super(ctx, R.layout.users_item, data);
        this.users = data;
        this.context = ctx;
        this.type = type;
        this.responseCallBack=responseCallBack;
    }


    private static class ViewHolder {
        TextView name;
        ImageView image;
        LinearLayout u_item;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User dataModel = getItem(position);
        ViewHolder viewHolder;

        View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.users_item, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.u_item = convertView.findViewById(R.id.u_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        String u_name = dataModel.getFirstName() + " " + dataModel.getLastName();
        viewHolder.name.setText(u_name);
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.image);
        userService = new UserService();
        viewHolder.u_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.active_user_dialog, parent, false);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                Position = position;
                ImageView d_image = bottomSheetDialog.findViewById(R.id.d_image);
                String url2 = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage();
                Picasso.get().load(url2).fit()
                        .centerCrop().into(d_image);
                TextView d_name = bottomSheetDialog.findViewById(R.id.d_name);
                String n = dataModel.getFirstName() + " " + dataModel.getLastName();
                d_name.setText(n);
                TextView d_uName = bottomSheetDialog.findViewById(R.id.d_uName);
                d_uName.setText(dataModel.getUserName());
                TextView d_uType = bottomSheetDialog.findViewById(R.id.d_uType);
                d_uType.setText(dataModel.getuType());
                TextView d_uEmail = bottomSheetDialog.findViewById(R.id.d_uEmail);
                d_uEmail.setText(dataModel.getEmail());
                TextView d_uAddress = bottomSheetDialog.findViewById(R.id.d_uAddress);
                d_uAddress.setText(dataModel.getAddress());
                MaterialButton blacklist = bottomSheetDialog.findViewById(R.id.blBtn);
                MaterialButton activate = bottomSheetDialog.findViewById(R.id.aBtn);
                if (type.equals("active")) {
                    blacklist.setVisibility(View.VISIBLE);
                    activate.setVisibility(View.GONE);

                } else {
                    blacklist.setVisibility(View.GONE);
                    activate.setVisibility(View.VISIBLE);

                }
                blacklist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Position = position;
                        blacklistUser(dataModel.getId());
                        ClientsFragment.getClientsCounts(responseCallBack);

                    }
                });
                activate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Position = position;
                        activateUser(dataModel.getId());
                        ClientsFragment.getClientsCounts(responseCallBack);
                    }
                });


            }
        });
        return result;
    }

    public void blacklistUser(int userId) {
        userService.blacklistUser(userId, this);
    }

    public void activateUser(int userId) {
        userService.ActivateUser(userId, getRequestResponse());
    }


    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody = (ResponseBody) response.body();
        if (responseBody.string().equals("Success")) {
            bottomSheetDialog.dismiss();
            users.remove(Position);
            notifyDataSetChanged();
            Toast.makeText(context, "Blacklisted User", Toast.LENGTH_SHORT).show();
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

    public ResponseCallBack getRequestResponse() {
        ResponseCallBack activateResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {
                    bottomSheetDialog.dismiss();
                    users.remove(Position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Activated User", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(context, "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return activateResponseCallBack;
    }
}
