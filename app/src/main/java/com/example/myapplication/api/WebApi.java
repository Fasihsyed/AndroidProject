package com.example.myapplication.api;

import com.example.myapplication.model.DataModel;
import com.example.myapplication.model.UserDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebApi {
    String BASE_URL ="http://192.168.1.104/FLMApi/api/" ;
    @POST("Login/Login")
    public Call<UserDetail> login(@Body UserDetail u);
   @POST("Login/SignUp")
   public Call<String> signup(@Body UserDetail u);

    @POST("Group/createNewGroup")
    Call<Void> createNewGroup(@Body  DataModel dataModel);

    @GET("Group/getGroup")
    public Call<ArrayList<DataModel>> getgroup();

}
