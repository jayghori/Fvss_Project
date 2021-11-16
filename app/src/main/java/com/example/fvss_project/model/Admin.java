package com.example.fvss_project.model;

public class Admin {

    String email;
    String name;
    String Address;
    String phoneNumber;
    String adminId;

    public Admin(String email, String name, String address, String phoneNumber, String adminId) {
        this.email = email;
        this.name = name;
        Address = address;
        this.phoneNumber = phoneNumber;
        this.adminId = adminId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
