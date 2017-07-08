package com.example.chirag.employeedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.chirag.employeedemo.adapter.EmployeeAdapter;
import com.example.chirag.employeedemo.model.Employee;
import com.example.chirag.employeedemo.model.MainResponse;
import com.example.chirag.employeedemo.model.MainResponseAdd;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    private FloatingActionButton mFbAddEmployee;
    private RecyclerView mRvEmployee;
    private String message;
    private int pos;
    private  EmployeeAdapter employeeAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvEmployee = (RecyclerView) findViewById(R.id.rv_employee);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_employee);
        mFbAddEmployee = (FloatingActionButton) findViewById(R.id.fb_add_employee);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvEmployee.setLayoutManager(layoutManager);
        employeeAdapter = new EmployeeAdapter(this, employeeArrayList, this);
        mRvEmployee.setAdapter(employeeAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRvEmployee.addItemDecoration(itemDecoration);
        mFbAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://music.sparkenproduct.in/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeInterface employeeInterface = retrofit.create(EmployeeInterface.class);
        Call<MainResponse> call = employeeInterface.MAIN_RESPONSE_CALL();
        call.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful()) {
                    MainResponse mainResponse = response.body();
                    if (mainResponse != null) {
                        employeeArrayList.addAll(mainResponse.getData());
                        employeeAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d("condition", "onLongClick: " + v.getId());
        if (v.getId() == R.id.ll_list) {
            pos = (int) v.getTag();
            String[] array = {"DELETE", "EDIT"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose")
                    .setItems(array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0:

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://music.sparkenproduct.in/public/api/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    EmployeeInterface employeeInterface = retrofit.create(EmployeeInterface.class);
//                                Call<MEmployee> call = webApiCall.deleteEmployeeById(position);

                                    Call<MainResponseAdd> call = employeeInterface.DeleteEmployee(employeeArrayList.get(pos).getId());
                                    call.enqueue(new Callback<MainResponseAdd>() {
                                        @Override
                                        public void onResponse(Call<MainResponseAdd> call, Response<MainResponseAdd> response) {
                                            if (response.isSuccessful()){
                                             employeeArrayList.remove(pos);
                                                employeeAdapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MainResponseAdd> call, Throwable t) {

                                        }
                                    });
                                    Log.i("TAG", "DELETED");

                                    Snackbar snackbar = Snackbar
                                            .make(coordinatorLayout, "Employee is deleted", Snackbar.LENGTH_LONG)
                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                }
                                            });
                                    snackbar.show();
                                    break;
                                case 1:

                                    Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                                    Employee employee = employeeArrayList.get(pos);
                                    intent.putExtra("Employee",employee);
                                    startActivity(intent);
                                    Log.i("TAG", "EDIT");
                                    break;
                            }
                        }
                    });
            builder.show();

        } else {
            Log.i("test", "onLongClick: ");
        }
        return true;
    }

}
