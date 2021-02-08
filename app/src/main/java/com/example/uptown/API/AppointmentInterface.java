package com.example.uptown.API;

import com.example.uptown.DTO.Response.CustomerAppointmentDTO;
import com.example.uptown.Model.Appointment;
import com.example.uptown.Model.Enquiry;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppointmentInterface {

    @POST("mobile/api/appointment/makeAppointment/{propId}/{userId}")
    Call<ResponseBody> makeAppointment(@Body Appointment appointment, @Path("propId") int propId, @Path("userId") int userId);

    @GET("mobile/api/appointment/customerAppointments/pending/{customerId}")
    Call<List<Appointment>> customerPendingAppointments(@Path("customerId") int customerId);

    @GET("mobile/api/appointment/customerAppointments/accepted/{customerId}")
    Call<List<Appointment>> customerAcceptedAppointment(@Path("customerId") int customerId);

    @GET("mobile/api/appointment/customerAppointments/deleteAppointment/{appointmentId}")
    Call<ResponseBody> deleteAppointment(@Path("appointmentId") int appointmentId);

    @GET("mobile/api/appointment/advertiserAppointment/pending/{advertiserId}")
    Call<List<Appointment>> findPendingAppointments(@Path("advertiserId") int advertiserId);

    @GET("mobile/api/appointment/advertiserAppointment/accepted/{advertiserId}")
    Call<List<Appointment>> findUpcomingAppointments(@Path("advertiserId") int advertiserId);

    @GET("mobile/api/appointment/acceptAppointment/{id}")
    Call<ResponseBody> acceptAppointment(@Path("id") int id);

    @GET("mobile/api/appointment/rejectAppointment/{id}")
    Call<ResponseBody> rejectAppointment(@Path("id") int id);

    @GET("mobile/api/appointment/completedAppointment/{id}")
    Call<ResponseBody> completedAppointment(@Path("id") int id);

    @GET("mobile/api/appointment/cancelAppointment/{id}")
    Call<ResponseBody> cancelAppointment(@Path("id") int id);

    @GET("mobile/api/appointment/customerAppointments/getAppointmentsCount/{customerId}")
    Call<CustomerAppointmentDTO> getAppointmentsCount(@Path("customerId") int customerId);

}
