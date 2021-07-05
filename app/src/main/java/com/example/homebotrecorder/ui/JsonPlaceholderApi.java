package com.example.homebotrecorder.ui;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface JsonPlaceholderApi {
    @GET("/posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //    @Multipart
//    @POST("uploadImage")
//    Call<ResponseBody> uploadImage(@Part("file\"; fileName=\"myFile.png\" ") RequestBody requestBodyFile,
//                                   @Part("image") RequestBody requestBodyJson);
    @Multipart
    @POST("/")
    Call<Post> uploadAttachment(@Part MultipartBody.Part file);
    // You can add other parameters too
}
