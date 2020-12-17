package com.example.uptown.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uptown.Adapters.AgentAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.Services.UserService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class AgentsFragment extends Fragment implements ResponseCallBack {

    UserService userService;
    Integer id;
    GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_agents,container,false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        userService = new UserService();
        id = prefs.getInt("id", 0);
        gridView = (GridView) view.findViewById(R.id.agentsGrid);
        getAgents();
        return view;

    }
    public void getAgents(){
        userService.getAgents(this);
    }
    @Override
    public void onSuccess(Response response) throws IOException {
        List<User> agents=(List<User>) response.body();
        if(!agents.isEmpty()){
            AgentAdapter adapter=new AgentAdapter(agents,getContext());
            gridView.setAdapter(adapter);
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
