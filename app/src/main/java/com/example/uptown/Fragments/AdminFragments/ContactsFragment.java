package com.example.uptown.Fragments.AdminFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uptown.Admin.Adapters.ContactsAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Notifications;
import com.example.uptown.R;
import com.example.uptown.Services.NotificationService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ContactsFragment extends Fragment implements ResponseCallBack {
    Integer id;
    NotificationService notificationService;
    GridView gridView;
    LinearLayout empty;

    public ContactsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_contacts_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        notificationService=new NotificationService();
        gridView=view.findViewById(R.id.contactsGrid);
        empty=view.findViewById(R.id.empty);
        getNotifications();
        return view;
    }
    public void getNotifications(){
        notificationService.findAllNotifications(this);
    }
    @Override
    public void onSuccess(Response response) throws IOException {
        List<Notifications> notifications = (List<Notifications>) response.body();
        if (!notifications.isEmpty()) {
            ContactsAdapter adapter = new ContactsAdapter(notifications, getContext(),ContactsFragment.this);
            gridView.setAdapter(adapter);
            visibleList();
        }else{
          emptyList();
        }
    }

    public void visibleList(){
        gridView.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
    }
    public void emptyList(){
        gridView.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
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