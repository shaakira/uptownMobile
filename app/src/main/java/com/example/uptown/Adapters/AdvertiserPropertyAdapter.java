package com.example.uptown.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Fragments.AdvertiserPropertyFragment.PendingProperty;
import com.example.uptown.Fragments.AdvertiserPropertyFragment.PublishedProperty;
import com.example.uptown.Fragments.AdvertiserPropertyFragment.RejectedProperty;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.PropertyService;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class AdvertiserPropertyAdapter extends ArrayAdapter<Property> implements ResponseCallBack {
    private List<Property> properties;
    Context ctx;
    int Position;
    Dialog dialog;
    PropertyService propertyService;
    private String type;
    Fragment fragment;

    public AdvertiserPropertyAdapter(List<Property> properties, Context context, String type, Fragment fragment) {
        super(context, R.layout.advertiser_property_layout, properties);
        this.properties = properties;
        this.ctx = context;
        this.type = type;
        this.fragment = fragment;
    }

    private static class ViewHolder {
        TextView heading;
        ImageView menu;
        TextView des;
        ImageView image;
        TextView price;
        TextView propId;
        TextView street;
        TextView city;
        TextView prov;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Property dataModel = getItem(position);
        ViewHolder viewHolder;
        View result = convertView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.advertiser_property_layout, parent, false);
            viewHolder.heading = convertView.findViewById(R.id.description);
            viewHolder.menu = convertView.findViewById(R.id.menu);
            viewHolder.des = convertView.findViewById(R.id.des);
            viewHolder.image = convertView.findViewById(R.id.image1);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.propId = convertView.findViewById(R.id.propId);
            viewHolder.street = convertView.findViewById(R.id.street);
            viewHolder.city = convertView.findViewById(R.id.city);
            viewHolder.prov = convertView.findViewById(R.id.prov);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        viewHolder.heading.setText(dataModel.getHeading());
        viewHolder.des.setText(dataModel.getDescription());
        viewHolder.price.setText(String.valueOf(dataModel.getRate()));
        viewHolder.propId.setText(String.valueOf(dataModel.getId()));
        viewHolder.street.setText(dataModel.getStreet());
        viewHolder.city.setText(dataModel.getCity());
        viewHolder.prov.setText(dataModel.getProvince());
        String url = RetrofitClient.Url() + "resources/Image/" + dataModel.getImage1();
        Picasso.get().load(url).fit()
                .centerCrop().into(viewHolder.image);
        propertyService = new PropertyService();
        dialog = new Dialog(getContext());
        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ctx, viewHolder.menu);
                if (type.equals("rejected")) {
                    popupMenu.getMenuInflater().inflate(R.menu.reject_popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.prop_delete) {
                                dialog.setContentView(R.layout.delete_property);
                                dialog.getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                MaterialButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                MaterialButton deleteBtn = dialog.findViewById(R.id.deleteBtn);
                                deleteBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Position = position;
                                        deleteProperty(dataModel.getId(), dataModel.getUser().getId());

                                    }
                                });
                                return true;
                            }
                            if (menuItem.getItemId() == R.id.prop_request) {
                                dialog.setContentView(R.layout.request_again_dialog);
                                dialog.getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                MaterialButton cancelBtn = dialog.findViewById(R.id.cancel);
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                MaterialButton reqBtn = dialog.findViewById(R.id.reqBtn);
                                reqBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Position = position;
                                        requestAgain(dataModel.getId());

                                    }
                                });
                                return true;
                            }
                            return false;
                        }
                    });
                } else {
                    popupMenu.getMenuInflater().inflate(R.menu.prop_popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.prop_delete) {
                                dialog.setContentView(R.layout.delete_property);
                                dialog.getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                MaterialButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                MaterialButton deleteBtn = dialog.findViewById(R.id.deleteBtn);
                                deleteBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Position = position;
                                        deleteProperty(dataModel.getId(), dataModel.getUser().getId());

                                    }
                                });
                                return true;
                            }
                            return false;
                        }
                    });
                }
                popupMenu.show();

            }
        });
        return result;
    }

    public void deleteProperty(int propertyId, int advertiserId) {
        propertyService.deleteProperty(propertyId, advertiserId, this);
    }

    public void requestAgain(int propId) {
        propertyService.requestAgain(propId, getRequestResponse());
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody = (ResponseBody) response.body();
        if (responseBody.string().equals("Success")) {
            dialog.dismiss();
            properties.remove(Position);
            notifyDataSetChanged();
            Toast.makeText(ctx, "Property deleted", Toast.LENGTH_SHORT).show();
            if (!properties.isEmpty()) {
                if (type.equals("pending")) {
                    PendingProperty pendingProperty = new PendingProperty();
                    fragment = pendingProperty;
                    pendingProperty.visibleList();
                } else if (type.equals("rejected")) {
                    RejectedProperty rejectedProperty = new RejectedProperty();
                    fragment = rejectedProperty;
                    rejectedProperty.visibleList();
                }
                else{
                    PublishedProperty publishedProperty=new PublishedProperty();
                    fragment=publishedProperty;
                    publishedProperty.visibleList();
                }

            }
            else{
                if (type.equals("pending")) {
                    PendingProperty pendingProperty = new PendingProperty();
                    fragment = pendingProperty;
                    pendingProperty.emptyList();
                } else if (type.equals("rejected")) {
                    RejectedProperty rejectedProperty = new RejectedProperty();
                    fragment = rejectedProperty;
                    rejectedProperty.emptyList();
                }
                else{
                    PublishedProperty publishedProperty=new PublishedProperty();
                    fragment=publishedProperty;
                    publishedProperty.emptyList();
                }
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

    public ResponseCallBack getRequestResponse() {
        ResponseCallBack appointmentResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {
                    dialog.dismiss();
                    properties.remove(Position);
                    notifyDataSetChanged();
                    Toast.makeText(ctx, "Requested Again", Toast.LENGTH_SHORT).show();
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
