package com.example.twitter.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.twitter.DashboardActivity;
import com.example.twitter.R;
import com.example.twitter.api.UsersAPI;
import com.example.twitter.models.User;
import com.example.twitter.server_responses.ImageResponse;
import com.example.twitter.server_responses.SignUpResponse;
import com.example.twitter.strictmode.StrictModeClass;
import com.example.twitter.url.Base_url;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class ProfileImageActivity extends AppCompatActivity {
    ImageView ProfileImage;
    Button btnProfileImageFinish;
    String username, phoneEmail, password, imagePath;
    private String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);

        ProfileImage = findViewById(R.id.ProfileImage);
        btnProfileImageFinish = findViewById(R.id.btnProfileImageFinish);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            username = bundle.getString("username");
            phoneEmail = bundle.getString("phoneEmail");
            password = bundle.getString("password");
        }

        CheckPermission();
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        btnProfileImageFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
                signUp();
            }
        });

    }
    private void CheckPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Choose Photo ");
        String[] pictureDialogItems = {
                "Select Gallery",
                "Capture Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                BrowseImage();
                                break;
                            case 1:
                                CaptureImage();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void BrowseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    public void CaptureImage() {
        Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if (intent.resolveActivity(ProfileImageActivity.this.getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 0) {
            Uri uri = data.getData();
            ProfileImage.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        }
        else if(requestCode == 1){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            ProfileImage.setImageBitmap(bitmap);
        }

    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UsersAPI usersAPI = Base_url.getInstance().create(UsersAPI.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous method
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signUp() {
        String email = "";

        User users = new User(phoneEmail, email, username, password, imageName);

        UsersAPI usersAPI = Base_url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> signUpCall = usersAPI.registerUser(users);

        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProfileImageActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (response.isSuccessful() && response.body().getStatus().equals("Successful")) {
                    Toast.makeText(ProfileImageActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    Base_url.token += response.body().getUsertoken();

                    if (!Base_url.token.equals("Bearer ")){
                        Intent intent = new Intent(ProfileImageActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(ProfileImageActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
