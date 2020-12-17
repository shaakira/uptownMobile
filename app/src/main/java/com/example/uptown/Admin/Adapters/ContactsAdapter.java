package com.example.uptown.Admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Fragments.AdminFragments.ContactsFragment;
import com.example.uptown.Model.Notifications;
import com.example.uptown.R;
import com.example.uptown.Services.NotificationService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ContactsAdapter extends ArrayAdapter<Notifications> implements ResponseCallBack {
    private List<Notifications> notifications;
    Context context;
    BottomSheetDialog bottomSheetDialog;
    NotificationService notificationService;
    int Position;
    ContactsFragment fragment;

    public ContactsAdapter(List<Notifications> data, Context ctx,ContactsFragment fragment) {
        super(ctx, R.layout.contact_item, data);
        this.notifications = data;
        this.context = ctx;
        this.fragment=fragment;
    }

    private static class ViewHolder {
        TextView initial;
        TextView name;
        TextView subject;
        LinearLayout dialogView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Notifications dataModel = getItem(position);
        ViewHolder viewHolder;
        View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contact_item, parent, false);
            viewHolder.initial = convertView.findViewById(R.id.d_initial);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.subject = convertView.findViewById(R.id.subject);
            viewHolder.dialogView = convertView.findViewById(R.id.dialogView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        result = convertView;
        String c_name = dataModel.getName();
        char c = c_name.charAt(0);
        viewHolder.initial.setText(String.valueOf(c));
        viewHolder.name.setText(dataModel.getName());
        viewHolder.subject.setText(dataModel.getSubject());
        notificationService=new NotificationService();
        viewHolder.dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.contact_dialog, parent, false);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                Position = position;
                TextView d_initial = bottomSheetDialog.findViewById(R.id.d_initial);
                String c_name = dataModel.getName();
                char c = c_name.charAt(0);
                d_initial.setText(String.valueOf(c));
                TextView d_name = bottomSheetDialog.findViewById(R.id.d_name);
                d_name.setText(dataModel.getName());
                TextView d_email = bottomSheetDialog.findViewById(R.id.d_email);
                d_email.setText(dataModel.getEmail());
                TextView d_subject = bottomSheetDialog.findViewById(R.id.d_subject);
                d_subject.setText(dataModel.getSubject());
                TextView d_message = bottomSheetDialog.findViewById(R.id.d_message);
                d_message.setText(dataModel.getMessage());
                MaterialButton cancel = bottomSheetDialog.findViewById(R.id.cancelBtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                MaterialButton delete = bottomSheetDialog.findViewById(R.id.deleteBtn);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteNotification(dataModel.getId());
                        bottomSheetDialog.dismiss();

                    }
                });
            }
        });
        return result;
    }

    public void deleteNotification(int id) {
        notificationService.deleteNotification(id, this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody = (ResponseBody) response.body();
        if (responseBody.string().equals("Success")) {
            notifications.remove(Position);
            notifyDataSetChanged();
            Toast.makeText(getContext(), "Notification deleted", Toast.LENGTH_SHORT).show();
            if(notifications.isEmpty()){
                fragment.emptyList();
            }
            else {
               fragment.visibleList();

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
}
