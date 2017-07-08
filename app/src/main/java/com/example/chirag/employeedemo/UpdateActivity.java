package com.example.chirag.employeedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chirag.employeedemo.model.Employee;
import com.example.chirag.employeedemo.model.MainResponseAdd;
import com.example.chirag.employeedemo.model.MessageResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {

    private AppCompatEditText mEtName, mEtEmail, mEtPassword, mEtCity, mEtmobile;
    private AppCompatButton mBtSubmit;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private AppCompatRadioButton rbGender;
    private Employee employee;
    private int id;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mEtName = (AppCompatEditText) findViewById(R.id.et_name);
        mEtEmail = (AppCompatEditText) findViewById(R.id.et_email);
        mEtPassword = (AppCompatEditText) findViewById(R.id.et_password);
        mEtmobile = (AppCompatEditText) findViewById(R.id.et_mobile);
        mEtCity = (AppCompatEditText) findViewById(R.id.et_city);
        rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        mBtSubmit = (AppCompatButton) findViewById(R.id.bt_submit);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);


        if (getIntent() != null) {
            employee = getIntent().getExtras().getParcelable("Employee");
            mEtName.setText(employee.getName());
            mEtCity.setText(employee.getCity());
            mEtEmail.setText(employee.getEmail());
            mEtPassword.setText(employee.getPassword());
            mEtmobile.setText(employee.getMobile());
            id = employee.getId();
            if (employee.getGender() == "Male") {
                rbMale.setChecked(true);
                int selectGender = rgGender.getCheckedRadioButtonId();
                rbGender = (AppCompatRadioButton) findViewById(selectGender);
            } else {
                rbFemale.setChecked(true);
                int selectGender = rgGender.getCheckedRadioButtonId();
                rbGender = (AppCompatRadioButton) findViewById(selectGender);
            }
        }

        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://music.sparkenproduct.in/public/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EmployeeInterface employeeInterface = retrofit.create(EmployeeInterface.class);
                Call<MainResponseAdd> call = employeeInterface.UpdateEmployee(id, mEtName.getText().toString().trim(), mEtEmail.getText().toString().trim(),
                        mEtmobile.getText().toString().trim(), mEtPassword.getText().toString().trim(),
                        mEtCity.getText().toString().trim(), rbGender.getText().toString().trim());
                call.enqueue(new Callback<MainResponseAdd>() {
                    @Override
                    public void onResponse(Call<MainResponseAdd> call, Response<MainResponseAdd> response) {
                        if (response.isSuccessful()) {
                            MainResponseAdd mainResponseAdd = response.body();
                            if (mainResponseAdd != null) {
                                message = mainResponseAdd.getMessage();
                                Toast.makeText(UpdateActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        } else {

                            if (response.errorBody() != null) {
                                try {
                                    MessageResponse responseMessage = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                                    if (responseMessage != null)
                                        Toast.makeText(UpdateActivity.this, "" + responseMessage.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponseAdd> call, Throwable t) {

                    }
                });
            }
        });

    }
}
