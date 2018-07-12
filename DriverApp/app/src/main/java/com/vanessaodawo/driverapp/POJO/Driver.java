package com.vanessaodawo.driverapp.POJO;

import com.google.firebase.auth.FirebaseUser;

public class Driver {

    String fname, lname, email;
    int tel;

    public Driver(String fname, String lname, String email, int tel) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.tel = tel;
    }

    public Driver() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }
}
