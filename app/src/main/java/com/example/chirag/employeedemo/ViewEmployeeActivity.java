package com.example.chirag.employeedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.example.chirag.employeedemo.model.Employee;
import com.example.chirag.employeedemo.model.MainResponseById;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewEmployeeActivity extends AppCompatActivity {

    private AppCompatTextView mTvName,mTvEmail,mTvPassword,mTvGender,mTvCity,mTvmobile,mTvIsActive,mTvIsDeleted,mTvCreatedAt,mTvUpdatedAt;
    private int employeeId;
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);
        mTvName = (AppCompatTextView) findViewById(R.id.tv_name);
        mTvEmail = (AppCompatTextView) findViewById(R.id.tv_email);
        mTvPassword = (AppCompatTextView) findViewById(R.id.tv_password);
        mTvmobile = (AppCompatTextView) findViewById(R.id.tv_phone_no);
        mTvCity = (AppCompatTextView) findViewById(R.id.tv_city);
        mTvGender = (AppCompatTextView) findViewById(R.id.tv_gender);
        mTvCreatedAt = (AppCompatTextView) findViewById(R.id.tv_created_at);
        mTvUpdatedAt = (AppCompatTextView) findViewById(R.id.tv_updated_at);
        mTvIsDeleted = (AppCompatTextView) findViewById(R.id.tv_is_deleted);
        mTvIsActive = (AppCompatTextView) findViewById(R.id.tv_is_active);

        Intent intent = getIntent();
        if (intent!=null){
            employeeId = intent.getExtras().getInt("EmployeeId");
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://music.sparkenproduct.in/public/api/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        EmployeeInterface employeeInterface = retrofit.create(EmployeeInterface.class);
        Call<MainResponseById> call = employeeInterface.EmployeeById(employeeId);
        call.enqueue(new Callback<MainResponseById>() {
            @Override
            public void onResponse(Call<MainResponseById> call, Response<MainResponseById> response) {
                if (response.isSuccessful()){
                    MainResponseById mainResponseById = response.body();
                    if (mainResponseById!=null){
                        Employee employee = mainResponseById.getEmployee();
                        mTvName.setText(employee.getName());
                        mTvEmail.setText(employee.getEmail());
                        mTvPassword.setText(employee.getPassword());
                        mTvCity.setText(employee.getCity());
                        mTvmobile.setText(employee.getMobile());
                        mTvGender.setText(employee.getGender());
                        mTvCreatedAt.setText(employee.getCreatedAt());
                        mTvUpdatedAt.setText(employee.getUpdatedAt());
                        mTvIsActive.setText(employee.getIsActive());
                        mTvIsDeleted.setText(employee.getIsDeleted());
                    }
                }
            }

            @Override
            public void onFailure(Call<MainResponseById> call, Throwable t) {

            }

                 });

    }
}
