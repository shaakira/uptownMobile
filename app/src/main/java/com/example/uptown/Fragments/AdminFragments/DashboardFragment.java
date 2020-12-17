package com.example.uptown.Fragments.AdminFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.graphics.vector.SolidFill;
import com.example.uptown.Admin.Adapters.ContactsAdapter;
import com.example.uptown.Admin.Adapters.TopAgentAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Response.AdminDashboardDTO;
import com.example.uptown.R;
import com.example.uptown.Services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class DashboardFragment extends Fragment implements ResponseCallBack {

    AnyChartView userChart;
    String[] userTypes = {"customer", "agent", "owner"};
    int[] counts;
    TextView propCount,agentCount,adCount;
    GridView gridView;
    UserService userService;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_fragment, container, false);
        userService=new UserService();
        userChart = view.findViewById(R.id.userChart);
        propCount=view.findViewById(R.id.propCount);
        agentCount=view.findViewById(R.id.agentCount);
        adCount=view.findViewById(R.id.adCount);
        gridView=view.findViewById(R.id.agentsGrid);
        getContent();

        return view;
    }

    public void getContent(){
        userService.getDashBoard(this);
    }


    public void setupUserChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < userTypes.length; i++) {
            dataEntries.add(new ValueDataEntry(userTypes[i], counts[i]));

        }
        pie.data(dataEntries);
        pie.palette().itemAt(0,new SolidFill("#2eca6a",1));
        pie.palette().itemAt(1,new SolidFill("#000000",1));
        pie.palette().itemAt(2,new SolidFill("#4a4a4a",1));
        userChart.setChart(pie);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        AdminDashboardDTO adminDashboardDTO=(AdminDashboardDTO)response.body();
        if(adminDashboardDTO!=null){
            propCount.setText(String.valueOf(adminDashboardDTO.getPropCount()));
            agentCount.setText(String.valueOf(adminDashboardDTO.getAgentCount()));
            int advertiser=adminDashboardDTO.getAgentCount()+adminDashboardDTO.getOwnerCount();
            adCount.setText(String.valueOf(advertiser));
            counts= new int[]{adminDashboardDTO.getCustomerCount(), adminDashboardDTO.getAgentCount(), adminDashboardDTO.getOwnerCount()};
            setupUserChart();
            if(!adminDashboardDTO.getTopAgents().isEmpty()){
                TopAgentAdapter adapter = new TopAgentAdapter(adminDashboardDTO.getTopAgents(), getContext());
                gridView.setAdapter(adapter);

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