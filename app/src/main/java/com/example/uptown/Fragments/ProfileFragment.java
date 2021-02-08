package com.example.uptown.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uptown.AdvertiserAppointments;
import com.example.uptown.AdvertiserEnquiryActivity;
import com.example.uptown.AdvertiserProperties;
import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.CustomerAppointments;
import com.example.uptown.DTO.Response.UserDTO;
import com.example.uptown.EditProfile;
import com.example.uptown.MainActivity;
import com.example.uptown.Model.User;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.UserService;
import com.example.uptown.WishListScreen;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements ResponseCallBack, GoogleApiClient.OnConnectionFailedListener {

    UserService userService;
    TextView firstName, lastName, address, email, username, fName, lName, editProfile;
    ImageView settings, image;
    LinearLayout logout, wishList, appointments, enquiries, advertiserAppointments, myProperties;
    Dialog dialog;
    Button logoutBtn, cancelBtn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", MODE_PRIVATE);
        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();


            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        userService = new UserService();
        Integer id = prefs.getInt("id", 0);
        getProfile(id);
        dialog = new Dialog(getContext());
        firstName = view.findViewById(R.id.fName);
        lastName = view.findViewById(R.id.lName);
        address = view.findViewById(R.id.location);
        email = view.findViewById(R.id.emailAddress);
        username = view.findViewById(R.id.uName);
        fName = view.findViewById(R.id.name1);
        lName = view.findViewById(R.id.name2);
        settings = view.findViewById(R.id.settings);
        image = view.findViewById(R.id.dp);
        editProfile = view.findViewById(R.id.editProfile);

        return view;

    }

    public void getProfile(int id) {
        userService.GetProfile(id, this);
    }

    public void logout() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (status.isSuccess()) {
                                //  gotoMainActivity();
                            } else {
                                //  Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        userService.logout(getLogoutResponse());

    }

    @Override
    public void onSuccess(Response response) throws IOException {
        User user = (User) response.body();
        if (user != null) {
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            address.setText(user.getAddress());
            email.setText(user.getEmail());
            username.setText(user.getUserName());
            fName.setText(user.getFirstName());
            lName.setText(user.getLastName());
            String img = user.getImage();
            if (img != null) {
                String url = RetrofitClient.Url() + "resources/Image/" + img;
                Picasso.get().load(url).fit()
                        .centerCrop().into(image);


            }

            settings.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (user.getuType().equals("agent") || user.getuType().equals("owner")) {
                                                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                                                    View view1 = getLayoutInflater().inflate(R.layout.settings_dialog, null);
                                                    bottomSheetDialog.setContentView(view1);
                                                    bottomSheetDialog.show();
                                                    enquiries = bottomSheetDialog.findViewById(R.id.enquiries);
                                                    enquiries.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            Intent intent = new Intent(getContext(), AdvertiserEnquiryActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    advertiserAppointments = bottomSheetDialog.findViewById(R.id.myAppointments);
                                                    advertiserAppointments.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            Intent intent = new Intent(getContext(), AdvertiserAppointments.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    myProperties = bottomSheetDialog.findViewById(R.id.myProperties);
                                                    myProperties.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            Intent intent = new Intent(getContext(), AdvertiserProperties.class);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                    logout = bottomSheetDialog.findViewById(R.id.logout);
                                                    logout.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            dialog.setContentView(R.layout.logout_popup);
                                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                            dialog.show();
                                                            Window window = dialog.getWindow();
                                                            window.setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                            logoutBtn = dialog.findViewById(R.id.logoutBtn);
                                                            cancelBtn = dialog.findViewById(R.id.cancelBtn);
                                                            logoutBtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    logout();
                                                                }
                                                            });
                                                            cancelBtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    dialog.dismiss();
                                                                }
                                                            });

                                                        }
                                                    });


                                                } else {
                                                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                                                    View view1 = getLayoutInflater().inflate(R.layout.customer_settings, null);
                                                    bottomSheetDialog.setContentView(view1);
                                                    bottomSheetDialog.show();
                                                    logout = bottomSheetDialog.findViewById(R.id.logout);
                                                    logout.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            dialog.setContentView(R.layout.logout_popup);
                                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                            dialog.show();
                                                            Window window = dialog.getWindow();
                                                            window.setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                            logoutBtn = dialog.findViewById(R.id.logoutBtn);
                                                            cancelBtn = dialog.findViewById(R.id.cancelBtn);
                                                            logoutBtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    logout();
                                                                }
                                                            });
                                                            cancelBtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                        }
                                                    });
                                                    wishList = bottomSheetDialog.findViewById(R.id.wishList);
                                                    wishList.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            Intent intent = new Intent(getContext(), WishListScreen.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    appointments = bottomSheetDialog.findViewById(R.id.appointments);
                                                    appointments.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            bottomSheetDialog.dismiss();
                                                            Intent intent = new Intent(getContext(), CustomerAppointments.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }

                                            }
                                        }
            );
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), EditProfile.class);
                    startActivity(intent);
                }
            });
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

    public ResponseCallBack getLogoutResponse() {
        ResponseCallBack profileResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseBody result = (ResponseBody) response.body();
                if (result.string().equals("Success")) {
                    SharedPreferences logout = getActivity().getSharedPreferences("shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = logout.edit();
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "logout", Toast.LENGTH_SHORT).show();
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
        };
        return profileResponseCallBack;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
