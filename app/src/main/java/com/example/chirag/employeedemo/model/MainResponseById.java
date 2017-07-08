package com.example.chirag.employeedemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainResponseById {

@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Employee employee;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}