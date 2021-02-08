package com.example.uptown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.uptown.Adapters.AmenitiesAdapter;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Appointment;
import com.example.uptown.Model.Property;
import com.example.uptown.Model.User;
import com.example.uptown.Model.Wishlist;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.AppointmentService;
import com.example.uptown.Services.PropertyService;
import com.example.uptown.Services.UserService;
import com.example.uptown.Services.WishListService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class SingleProperty extends AppCompatActivity implements ResponseCallBack {

    PropertyService propertyService;
    UserService userService;
    AppointmentService appointmentService;
    WishListService wishListService;
    ImageSlider imageSlider;
    ImageView image, backBtn, chat, wishListImg;
    TextInputLayout txtName;
    TextView fName, lName, uType, rateType, price, propType, beds, baths, garage, des, street, city, prov, conDate, conTime;
    Integer id;
    Integer propId;
    GridView gridView;
    Button makeAppointment, addAppointment, confrim,cancelBtn;
    Calendar calendar;
    EditText date, time, email, name, note, phone;
    TimePickerDialog picker;
    Dialog dialog;
    User user1;
    BottomSheetDialog bottomSheetDialog;
    Integer agentId;
    String value="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_property);
        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        id = prefs.getInt("id", 0);
        Intent intent = getIntent();
        propId = intent.getIntExtra("propId", 0);
        String val=intent.getStringExtra("direct");
        propertyService = new PropertyService();
        userService = new UserService();
        appointmentService = new AppointmentService();
        wishListService = new WishListService();
        getProperty(propId);
        user1 = new User();
        getUser(id);
        getWishListed(propId, id);
        imageSlider = findViewById(R.id.slider);
        image = findViewById(R.id.image);
        fName = findViewById(R.id.firstName);
        uType = findViewById(R.id.uType);
        lName = findViewById(R.id.lastName);
        rateType = findViewById(R.id.rateType);
        price = findViewById(R.id.price);
        propType = findViewById(R.id.propType);
        beds = findViewById(R.id.beds);
        baths = findViewById(R.id.baths);
        garage = findViewById(R.id.garage);
        des = findViewById(R.id.des);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        prov = findViewById(R.id.prov);
        gridView = findViewById(R.id.amenitiesGrid);
        backBtn = findViewById(R.id.backBtn);
        wishListImg = findViewById(R.id.wishListImg);
        backBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(value.equals(val)){
                    SingleProperty.super.onBackPressed();
                }
                else{
                    Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent1);

                }

            }
        }));
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), CustomerEnquiry.class);
                intent1.putExtra("propertyId", propId);
                startActivity(intent1);
            }
        });

        dialog = new Dialog(this);
        makeAppointment = findViewById(R.id.makeAppointment);
        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 0) {
                    Intent intent1 = new Intent(SingleProperty.this, LandingScreen.class);
                    startActivity(intent1);
                } else {

                    bottomSheetDialog = new BottomSheetDialog(SingleProperty.this);
                    View view1 = getLayoutInflater().inflate(R.layout.appointment_layout, null);
                    bottomSheetDialog.setContentView(view1);

                    calendar = Calendar.getInstance();
                    date = bottomSheetDialog.findViewById(R.id.date);
                    time = bottomSheetDialog.findViewById(R.id.time);
                    name = bottomSheetDialog.findViewById(R.id.name);
                    email = bottomSheetDialog.findViewById(R.id.email);
                    note = bottomSheetDialog.findViewById(R.id.note);
                    phone = bottomSheetDialog.findViewById(R.id.phone);
                    name.setText(user1.getFirstName());
                    email.setText(user1.getEmail());
                    addAppointment = bottomSheetDialog.findViewById(R.id.addAppointment);
                    bottomSheetDialog.show();
                    DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateLabel();
                        }

                    };

                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DatePickerDialog(SingleProperty.this, dates, calendar
                                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    time.setInputType(InputType.TYPE_NULL);
                    time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar cldr = Calendar.getInstance();
                            int hour = cldr.get(Calendar.HOUR_OF_DAY);
                            int minutes = cldr.get(Calendar.MINUTE);
                            // time picker dialog
                            picker = new TimePickerDialog(SingleProperty.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                            time.setText(sHour + ":" + sMinute);
                                        }
                                    }, hour, minutes, true);
                            picker.show();
                        }
                    });
                    addAppointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (empty()) {

                                dialog.setContentView(R.layout.confirm_layout);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                conDate = dialog.findViewById(R.id.conDate);
                                conTime = dialog.findViewById(R.id.conTime);
                                confrim = dialog.findViewById(R.id.confirm);
                                cancelBtn=dialog.findViewById(R.id.cancelBtn);
                                String date4 = date.getText().toString();
                                DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
                                Date date3 = null;
                                try {
                                    date3 = originalFormat.parse(date4);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String formattedDate = targetFormat.format(date3);
                                conDate.setText(formattedDate);
                                conTime.setText(time.getText().toString());
                                dialog.show();
                                Window window = dialog.getWindow();
                                window.setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                confrim.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Appointment appointment = new Appointment();
                                        appointment.setName(name.getText().toString());
                                        appointment.setEmail(email.getText().toString());
                                        appointment.setCustomerContact(Integer.parseInt(phone.getText().toString()));
                                        appointment.setDate(date.getText().toString());
                                        appointment.setTime(time.getText().toString());
                                        appointment.setNote(note.getText().toString());
                                        setAppointment(propId, id, appointment);


                                    }
                                });
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                            }
                        }
                    });
                }

            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(calendar.getTime()));
    }

    public void getProperty(int id) {
        propertyService.getProperty(id, this);
    }

    public void getUser(int id) {
        userService.GetProfile(id, getUserResponse());
    }

    public void setAppointment(int propId, int userId, Appointment appointment) {
        appointmentService.makeAppointment(appointment, propId, userId, getAppointmentResponse());
    }

    public void getWishListed(int propId, int userId) {
        wishListService.getWishListed(propId, userId, getWishListedResponse());
    }

    public void addWishList(Wishlist wishlist, int propId, int userId) {
        wishListService.addWishList(wishlist, propId, userId, getAddWishListResponse());
    }

    public ResponseCallBack getAddWishListResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Success")) {
                    finish();
                    startActivity(getIntent());
                }

            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return userResponseCallBack;
    }

    public ResponseCallBack getRemoveWishListResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody.string().equals("Successfully Removed")) {
                    finish();
                    startActivity(getIntent());
                }

            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return userResponseCallBack;
    }

    public ResponseCallBack getWishListedResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Boolean listed = (Boolean) response.body();

                if (listed) {
                    wishListImg.setImageResource(R.drawable.like2);
                } else {
                    wishListImg.setImageResource(R.drawable.like);
                    wishListImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (id == 0) {
                                Intent intent1 = new Intent(SingleProperty.this, LandingScreen.class);
                                startActivity(intent1);
                            } else {
                                Wishlist wishlist = new Wishlist();
                                addWishList(wishlist, propId, id);
                            }
                        }
                    });


                }
            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return userResponseCallBack;
    }

    public ResponseCallBack getUserResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                User user = (User) response.body();

                if (user != null) {
                    user1 = user;
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return userResponseCallBack;
    }

    public ResponseCallBack getAppointmentResponse() {
        ResponseCallBack userResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody responseBody = (ResponseBody) response.body();

                if (responseBody.string().equals("Success")) {
                    dialog.dismiss();
                    bottomSheetDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Successfully made an appointment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage.equals("")) {
                    Toast.makeText(getApplicationContext(), "Network Error Please check your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return userResponseCallBack;
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        Property property = (Property) response.body();
        if (property != null) {
            agentId = property.getUser().getId();
            String url = RetrofitClient.Url() + "resources/Image/" + property.getImage1();
            String url2 = RetrofitClient.Url() + "resources/Image/" + property.getImage2();
            String url3 = RetrofitClient.Url() + "resources/Image/" + property.getImage3();
            List<SlideModel> slideModels = new ArrayList<>();
            slideModels.add(new SlideModel(url, null));
            slideModels.add(new SlideModel(url2, null));
            slideModels.add(new SlideModel(url3, null));
            imageSlider.setImageList(slideModels, true);
            String img = property.getUser().getImage();
            if (img != null) {
                String urls = RetrofitClient.Url() + "resources/Image/" + img;
                Picasso.get().load(urls).fit()
                        .centerCrop().into(image);


            }
            fName.setText(property.getUser().getFirstName());
            lName.setText(property.getUser().getLastName());
            uType.setText(property.getUser().getuType());
            if (property.getRateType().equals("perDay")) {
                rateType.setText("Daily");
            } else if (property.getRateType().equals("perMonth")) {
                rateType.setText("Monthly");

            } else if (property.getRateType().equals("perWeek")) {
                rateType.setText("Weekly");
            } else {
                rateType.setText("Annually");
            }
            price.setText(String.valueOf(property.getRate()));
            propType.setText(property.getpType());
            beds.setText(String.valueOf(property.getRooms()));
            baths.setText(String.valueOf(property.getBaths()));
            garage.setText(String.valueOf(property.getGarage()));
            des.setText(property.getDescription());
            street.setText(property.getStreet());
            city.setText(property.getCity());
            prov.setText(property.getProvince());
            String[] am = property.getFeatures().split(",");
            ArrayList<String> f = new ArrayList<>(Arrays.asList(am));
            AmenitiesAdapter adapter = new AmenitiesAdapter(this, f);
            gridView.setAdapter(adapter);


        }
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(this, "Network Error Please check your Network Cconnection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean empty() {
        boolean flag = true;
        if ((TextUtils.isEmpty(name.getText().toString())) && (TextUtils.isEmpty(email.getText().toString())) &&
                (TextUtils.isEmpty(date.getText().toString())) && (TextUtils.isEmpty(time.getText().toString())) && (TextUtils.isEmpty(note.getText().toString()))
                && (TextUtils.isEmpty(phone.getText().toString()))) {


            flag = false;
        } else if ((TextUtils.isEmpty(name.getText().toString())) || (TextUtils.isEmpty(email.getText().toString())) ||
                (TextUtils.isEmpty(date.getText().toString())) || (TextUtils.isEmpty(time.getText().toString())) || (TextUtils.isEmpty(note.getText().toString()))
                || (TextUtils.isEmpty(phone.getText().toString()))) {
            if ((TextUtils.isEmpty(name.getText().toString()))) {
                txtName.setError("name cannot be empty");
                flag = false;
            } else if ((TextUtils.isEmpty(email.getText().toString()))) {
                email.setError("email cannot be empty");
                flag = false;
            } else if (phone.getText().toString().isEmpty()) {
                phone.setError("phone cannot be empty");
                flag = false;
            } else if ((TextUtils.isEmpty(date.getText().toString()))) {
                date.setError("date cannot be empty");
                flag = false;
            } else if ((TextUtils.isEmpty(time.getText().toString()))) {
                time.setError("time cannot be empty");
                flag = false;
            } else if ((TextUtils.isEmpty(note.getText().toString()))) {
                note.setError("note cannot be empty");
                flag = false;
            }

        }


        return flag;
    }
}