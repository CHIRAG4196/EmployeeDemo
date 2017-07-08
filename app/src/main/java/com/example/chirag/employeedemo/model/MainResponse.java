package com.example.chirag.employeedemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainResponse {

@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Employee> data = null;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Employee> getData() {
return data;
}

public void setData(List<Employee> data) {
this.data = data;
}

}