package com.example.uptown.AddProperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Login;
import com.example.uptown.MainActivity;
import com.example.uptown.Model.Property;
import com.example.uptown.R;
import com.example.uptown.Services.PropertyService;
import com.example.uptown.Services.UserService;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AddPropertyActivity4 extends AppCompatActivity implements ResponseCallBack {

    List<String> filePath;
    ImageView backBtn;
    MaterialButton upload;
    MultipartBody.Part image1;
    Button add;
    PropertyService propertyService;
    List<Uri> uriList;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_property4);
        propertyService=new PropertyService();
        Intent intent=getIntent();
        Property property=intent.getExtras().getParcelable("activity3");
        upload=findViewById(R.id.upload);
        add=findViewById(R.id.add);
        backBtn = findViewById(R.id.backBtn);
        filePath=new ArrayList<>();
        uriList=new ArrayList<>();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
                List<MultipartBody.Part> pp=new ArrayList<>();
                int id = prefs.getInt("id", 0);
                for (String paths:filePath) {
                    File file = new File(paths);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    image1  = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    pp.add(image1);
                }
                addProperty(pp,property,id);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPropertyActivity4.super.onBackPressed();
            }
        });
    }

    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==0 && resultCode == Activity.RESULT_OK) {
            if(data.getClipData()!=null){
                int count=data.getClipData().getItemCount();
                for (int i=0;i<count;i++){
                    Uri imageUri=data.getClipData().getItemAt(i).getUri();
                    uriList.add(imageUri);
                    String paths=getPath(this,imageUri);
                    filePath.add(paths);
                    Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                selectedImage = data.getData();
                uriList.add(selectedImage);
                Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addProperty(List<MultipartBody.Part> image,Property property,int id){
        propertyService.addProperty(image,property,id,this);
    }

    @Override
    public void onSuccess(Response response) throws IOException {
        try {
            ResponseBody responseBody = (ResponseBody) response.body();
            String result = responseBody.string();
            if (result != null) {
                if (result.equals("SucessFully Added Property")) {
                    //Intent
                    Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddPropertyActivity4.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //Display Error
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onError(String errorMessage) {
        if(errorMessage.equals("")){
            Toast.makeText(this, "Network Error Please check your Network Cconnection", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();}
        System.out.println(errorMessage);
    }

}

