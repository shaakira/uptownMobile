package com.example.uptown.Services;

import com.example.uptown.API.AppointmentInterface;
import com.example.uptown.API.EnquiryInterface;
import com.example.uptown.CallBacks.CustomizeCallBack;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.DTO.Response.ClientCountDTO;
import com.example.uptown.DTO.Response.CustomerAppointmentDTO;
import com.example.uptown.Model.Appointment;
import com.example.uptown.Model.Enquiry;
import com.example.uptown.RetrofitClient.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class AppointmentService {

    RetrofitClient retrofitClient;
    AppointmentInterface appointmentInterface;

    public AppointmentService() {
        this.appointmentInterface=retrofitClient.getRetrofitClientInstance().create(AppointmentInterface.class);
    }
    public void makeAppointment(Appointment appointment,int propId,int userId, ResponseCallBack callBack){
        Call<ResponseBody> appointmentResponseCall=appointmentInterface.makeAppointment(appointment,propId,userId);
        appointmentResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void customerPendingAppointments(int customerId,ResponseCallBack responseCallBack){
        Call<List<Appointment>> pendingAppointments=appointmentInterface.customerPendingAppointments(customerId);
        pendingAppointments.enqueue(new CustomizeCallBack<List<Appointment>>(responseCallBack));
    }

    public void customerAcceptedAppointment(int customerId,ResponseCallBack responseCallBack){
        Call<List<Appointment>> pendingAppointments=appointmentInterface.customerAcceptedAppointment(customerId);
        pendingAppointments.enqueue(new CustomizeCallBack<List<Appointment>>(responseCallBack));
    }
    public void deleteAppointment(int appointmentId, ResponseCallBack callBack){
        Call<ResponseBody> appointmentResponseCall=appointmentInterface.deleteAppointment(appointmentId);
        appointmentResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void findPendingAppointments(int advertiserId,ResponseCallBack responseCallBack){
        Call<List<Appointment>> pendingAppointments=appointmentInterface.findPendingAppointments(advertiserId);
        pendingAppointments.enqueue(new CustomizeCallBack<List<Appointment>>(responseCallBack));
    }
    public void findUpcomingAppointments(int advertiserId,ResponseCallBack responseCallBack){
        Call<List<Appointment>> pendingAppointments=appointmentInterface.findUpcomingAppointments(advertiserId);
        pendingAppointments.enqueue(new CustomizeCallBack<List<Appointment>>(responseCallBack));
    }
    public void acceptAppointment(int id, ResponseCallBack callBack){
        Call<ResponseBody> appointmentResponseCall=appointmentInterface.acceptAppointment(id);
        appointmentResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void rejectAppointment(int id, ResponseCallBack callBack){
        Call<ResponseBody> appointmentResponseCall=appointmentInterface.rejectAppointment(id);
        appointmentResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void completedAppointment(int id, ResponseCallBack callBack){
        Call<ResponseBody> appointmentResponseCall=appointmentInterface.completedAppointment(id);
        appointmentResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }public void cancelAppointment(int id, ResponseCallBack callBack){
        Call<ResponseBody> appointmentResponseCall=appointmentInterface.cancelAppointment(id);
        appointmentResponseCall.enqueue(new CustomizeCallBack<ResponseBody>(callBack));
    }
    public void getAppointmentsCount(int customerId, ResponseCallBack callback){
        Call<CustomerAppointmentDTO> appointmentResponseCall = appointmentInterface.getAppointmentsCount(customerId);
        appointmentResponseCall.enqueue(new CustomizeCallBack<CustomerAppointmentDTO>(callback));
    }
}
