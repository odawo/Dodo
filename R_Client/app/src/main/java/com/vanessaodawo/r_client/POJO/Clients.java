package com.vanessaodawo.r_client.POJO;

/**
 * Created by Vanessa on 24/05/2018.
 */

public class Clients {

    String c_fname, c_lname, c_email, c_tel, c_address, c_other;

    public Clients(String c_fname, String c_lname, String c_email, String c_tel, String c_address, String c_other) {
        this.c_fname = c_fname;
        this.c_lname = c_lname;
        this.c_email = c_email;
        this.c_tel = c_tel;
        this.c_address = c_address;
        this.c_other = c_other;
    }

    public Clients() {

    }

    public String getC_fname() {
        return c_fname;
    }

    public void setC_fname(String c_fname) {
        this.c_fname = c_fname;
    }

    public String getC_lname() {
        return c_lname;
    }

    public void setC_lname(String c_lname) {
        this.c_lname = c_lname;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_tel() {
        return c_tel;
    }

    public void setC_tel(String c_tel) {
        this.c_tel = c_tel;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_other() {
        return c_other;
    }

    public void setC_other(String c_other) {
        this.c_other = c_other;
    }
}
