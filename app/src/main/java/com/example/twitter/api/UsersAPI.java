package com.example.twitter.api;

import com.example.twitter.models.Posts;
import com.example.twitter.models.User;
import com.example.twitter.server_responses.ImageResponse;
import com.example.twitter.server_responses.SignUpResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UsersAPI {
    @POST("users/registration")
    Call<SignUpResponse> registerUser(@Body User users);

    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("imageUpload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("users/me")
    Call<User> getUserDetails(@Header("Authorization")String token);

    @GET("users/posts")
    Call<List<Posts>> getAllPost();
}
