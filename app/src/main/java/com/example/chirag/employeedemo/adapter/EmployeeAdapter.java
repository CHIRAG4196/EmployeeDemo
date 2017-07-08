package com.example.chirag.employeedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.chirag.employeedemo.R;
import com.example.chirag.employeedemo.ViewEmployeeActivity;
import com.example.chirag.employeedemo.model.Employee;

import java.util.ArrayList;

/**
 * Created by chirag on 28-Jun-17.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Employee> employeeArrayList;
    private View.OnLongClickListener onLongClickListener;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList, View.OnLongClickListener onLongClickListener) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
        this.onLongClickListener = onLongClickListener;
    }



    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_employee,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.ViewHolder holder, int position) {
       final Employee employee = employeeArrayList.get(position);
        holder.mTvName.setText(employee.getName());
        holder.mTvEmail.setText(employee.getEmail());
        holder.mLlEmployee.setTag(position);
        holder.mLlEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewEmployeeActivity.class);
                intent.putExtra("EmployeeId",employee.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvName,mTvEmail;
        private LinearLayout mLlEmployee;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvName = (AppCompatTextView) itemView.findViewById(R.id.tv_name);
            mTvEmail = (AppCompatTextView) itemView.findViewById(R.id.tv_email);
            mLlEmployee = (LinearLayout) itemView.findViewById(R.id.ll_list);
            mLlEmployee.setOnLongClickListener(onLongClickListener);
        }
    }
}
