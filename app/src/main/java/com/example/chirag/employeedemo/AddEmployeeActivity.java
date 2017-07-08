package com.example.chirag.employeedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chirag.employeedemo.model.MainResponseAdd;
import com.example.chirag.employeedemo.model.MessageResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEmployeeActivity extends AppCompatActivity {

    private AppCompatEditText mEtName, mEtEmail, mEtPassword, mEtCity, mEtmobile;
    private RadioGroup rgGender;
    private AppCompatRadioButton rbGender;
    private AppCompatButton mBtSubmit;
    private String message;
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        mEtName = (AppCompatEditText) findViewById(R.id.et_name);
        mEtEmail = (AppCompatEditText) findViewById(R.id.et_email);
        mEtPassword = (AppCompatEditText) findViewById(R.id.et_password);
        mEtmobile = (AppCompatEditText) findViewById(R.id.et_mobile);
        mEtCity = (AppCompatEditText) findViewById(R.id.et_city);
        rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        mBtSubmit = (AppCompatButton) findViewById(R.id.bt_submit);

        int selectGender = rgGender.getCheckedRadioButtonId();
        rbGender = (AppCompatRadioButton) findViewById(selectGender);
        final String email = mEtEmail.getText().toString().trim();
        final String password = mEtPassword.getText().toString().trim();
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email.matches(emailPattern) && s.length() > 0)
                {
                    Toast.makeText(AddEmployeeActivity.this,"valid email address",Toast.LENGTH_SHORT).show();

                }

                else
                {
                    Toast.makeText(AddEmployeeActivity.this,"Invalid email address",Toast.LENGTH_SHORT).show();

                }
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.matches(emailPattern) && s.length() > 0){
                    Toast.makeText(AddEmployeeActivity.this, "Password is Strong", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddEmployeeActivity.this, "password must Contain 1 letter 1 number OR password is smal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEtEmail.getText().toString().trim())){
                    Toast.makeText(AddEmployeeActivity.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mEtName.getText().toString().trim())){
                    Toast.makeText(AddEmployeeActivity.this, "Enter the Name", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mEtmobile.getText().toString().trim())){
                    Toast.makeText(AddEmployeeActivity.this, "Enter the MobileNo", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mEtCity.getText().toString().trim())){
                    Toast.makeText(AddEmployeeActivity.this, "Enter the City", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mEtPassword.getText().toString().trim())){
                    Toast.makeText(AddEmployeeActivity.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                }


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://music.sparkenproduct.in/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeInterface employeeInterface = retrofit.create(EmployeeInterface.class);
        Call<MainResponseAdd> call = employeeInterface.AddEmployee(mEtName.getText().toString().trim(), mEtEmail.getText().toString().trim(),
                mEtmobile.getText().toString().trim(), mEtPassword.getText().toString().trim(),
                mEtCity.getText().toString().trim(), rbGender.getText().toString().trim());
        call.enqueue(new Callback<MainResponseAdd>() {
            @Override
            public void onResponse(Call<MainResponseAdd> call, Response<MainResponseAdd> response) {
                if (response.isSuccessful()){
                    MainResponseAdd mainResponseAdd = response.body();
                    if (mainResponseAdd!=null){
                        message = mainResponseAdd.getMessage();
                        Toast.makeText(AddEmployeeActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddEmployeeActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                }
                else{

                    if(response.errorBody()!=null){
                        try {
                            MessageResponse responseMessage=new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                       if(responseMessage!=null)
                            Toast.makeText(AddEmployeeActivity.this, ""+responseMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MainResponseAdd> call, Throwable t) {
            Log.i("Tag",t.getMessage());
            }
        });
             }
        });
    }
}
