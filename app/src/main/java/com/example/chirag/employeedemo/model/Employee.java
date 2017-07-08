package com.example.chirag.employeedemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee implements Parcelable {

@SerializedName("id")
@Expose
private int id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
@SerializedName("password")
@Expose
private String password;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("city")
@Expose
private String city;
@SerializedName("is_Active")
@Expose
private String isActive;
@SerializedName("is_deleted")
@Expose
private String isDeleted;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getIsActive() {
return isActive;
}

public void setIsActive(String isActive) {
this.isActive = isActive;
}

public String getIsDeleted() {
return isDeleted;
}

public void setIsDeleted(String isDeleted) {
this.isDeleted = isDeleted;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.mobile);
        dest.writeString(this.gender);
        dest.writeString(this.city);
        dest.writeString(this.isActive);
        dest.writeString(this.isDeleted);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Employee() {
    }

    protected Employee(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.mobile = in.readString();
        this.gender = in.readString();
        this.city = in.readString();
        this.isActive = in.readString();
        this.isDeleted = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}