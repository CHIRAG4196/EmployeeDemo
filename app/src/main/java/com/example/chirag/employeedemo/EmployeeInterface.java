package com.example.chirag.employeedemo;

import com.example.chirag.employeedemo.model.MainResponse;
import com.example.chirag.employeedemo.model.MainResponseAdd;
import com.example.chirag.employeedemo.model.MainResponseById;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by chirag on 28-Jun-17.
 */

public interface EmployeeInterface {

    @GET("getAllEmployee")
    Call<MainResponse> MAIN_RESPONSE_CALL();

    @GET("getEmployee/{EmployeeId}")
    Call<MainResponseById> EmployeeById(@Path("EmployeeId") int EmployeeId);

    @FormUrlEncoded
    @POST("addEmployee")
    Call<MainResponseAdd> AddEmployee(@Field("name") String name, @Field("email") String email,
                                      @Field("mobile") String mobile, @Field("password") String password,
                                      @Field("city") String city, @Field("gender") String gender);

    @GET("deleteEmployee/{EmployeeId}")
    Call<MainResponseAdd> DeleteEmployee(@Path("EmployeeId") int EmployeeId);

    @FormUrlEncoded
    @POST("updateEmployee")
    Call<MainResponseAdd> UpdateEmployee(@Field("id") int id,@Field("name") String name, @Field("email") String email,
                                      @Field("mobile") String mobile, @Field("password") String password,
                                      @Field("city") String city, @Field("gender") String gender);




}
