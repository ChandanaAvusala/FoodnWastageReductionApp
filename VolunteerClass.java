package com.ravi.fit.donatefood;


public class VolunteerClass {
    private String v_name;
    private String v_mobile;
    private String v_city;
    private String v_password;
    private String v_email;
    private String v_address;
    private int v_id;

    public VolunteerClass(String v_name, String v_mobile, String v_email, String v_password, String v_city, String v_address) {
        this.v_name = v_name;
        this.v_mobile = v_mobile;
        this.v_city = v_city;
        this.v_password = v_password;
        this.v_email = v_email;
        this.v_address = v_address;
    }

    public VolunteerClass(String city, String password) {
        this.v_city = city;
        this.v_password = password;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_mobile() {
        return v_mobile;
    }

    public void setV_mobile(String v_mobile) {
        this.v_mobile = v_mobile;
    }

    public String getV_city() {
        return v_city;
    }

    public void setV_city(String v_city) {
        this.v_city = v_city;
    }

    public String getV_password() {
        return v_password;
    }

    public void setV_password(String v_password) {
        this.v_password = v_password;
    }

    public String getV_email() {
        return v_email;
    }

    public void setV_email(String v_email) {
        this.v_email = v_email;
    }

    public String getV_address() {
        return v_address;
    }

    public void setV_address(String v_address) {
        this.v_address = v_address;
    }
}
