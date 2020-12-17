package com.example.uptown.Adapters;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Enquiry;
import com.example.uptown.R;
import com.example.uptown.Services.EnquiryService;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class EnquiriesAdapter extends RecyclerView.Adapter<EnquiriesAdapter.ViewHolder> implements ResponseCallBack {

    private List<Enquiry> enquiries;
    private Context context;
    private EnquiryService enquiryService;
    Enquiry enquiry;
    int Position;

    public EnquiriesAdapter(List<Enquiry> enquiries, Context context) {
        this.enquiries = enquiries;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.enquiry_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Enquiry currentEnquiry=enquiries.get(position);
        String firstName=currentEnquiry.getName();
        char first=firstName.charAt(0);
        holder.initial.setText(String.valueOf(first));
        holder.name.setText(currentEnquiry.getName());
        holder.email.setText(currentEnquiry.getEmail());
        holder.phone.setText(String.valueOf(currentEnquiry.getPhone()));
        holder.propId.setText(String.valueOf(currentEnquiry.getProperty().getId()));
        holder.heading.setText(currentEnquiry.getProperty().getHeading());
        holder.comment.setText(currentEnquiry.getComment());
        enquiryService=new EnquiryService();
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Position=position;
                removeEnquiry(currentEnquiry.getId());

            }
        });
        holder.expandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.expandableView.getVisibility()== View.GONE){
                    TransitionManager.beginDelayedTransition(holder.expandView, new AutoTransition());
                    holder.expandableView.setVisibility(View.VISIBLE);
                }
                else {
                    TransitionManager.beginDelayedTransition(holder.expandView, new AutoTransition());
                    holder.expandableView.setVisibility(View.GONE);
                }
            }
        });

    }

    public void removeEnquiry(int enquiryId){
        enquiryService.deleteEnquiry(enquiryId,this);
    }

    @Override
    public int getItemCount() {
        return enquiries.size();
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody=(ResponseBody) response.body();
        if(responseBody.string().equals("Success")){
            enquiries.remove(Position);
            notifyDataSetChanged();
            Toast.makeText(context, "Successfully Removed", Toast.LENGTH_SHORT).show();

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


    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView initial;
        private TextView name;
        private TextView email;
        private  TextView phone;
        private  TextView propId;
        private TextView heading;
        private TextView comment;
        private LinearLayout expandView;
        private LinearLayout expandableView;
        private MaterialButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initial=itemView.findViewById(R.id.initial);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            phone=itemView.findViewById(R.id.contact);
            propId=itemView.findViewById(R.id.propertyId);
            heading=itemView.findViewById(R.id.description);
            comment=itemView.findViewById(R.id.comment);
            expandView=itemView.findViewById(R.id.expandViewBtn);
            expandableView=itemView.findViewById(R.id.expandedView);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);

        }
    }
}
