package com.ravi.fit.donatefood;


class DonatorClass {

    private String id;
    private String d_title;
    private String d_decription;
    private String d_nop;
    private String d_email;
    private String d_fdate;
    private String d_ftime;
    private String d_mobile;
    private String d_city;
    private String d_address;
    public DonatorClass(String id, String d_title, String d_decription, String d_nop, String d_email, String d_fdate, String d_ftime, String d_mobile, String d_city, String d_address) {
        this.id = id;
        this.d_title = d_title;
        this.d_decription = d_decription;
        this.d_nop = d_nop;
        this.d_email = d_email;
        this.d_fdate = d_fdate;
        this.d_ftime = d_ftime;
        this.d_mobile = d_mobile;
        this.d_city = d_city;
        this.d_address = d_address;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getD_title() {
        return d_title;
    }

    public void setD_title(String d_title) {
        this.d_title = d_title;
    }

    public String getD_decription() {
        return d_decription;
    }

    public void setD_decription(String d_decription) {
        this.d_decription = d_decription;
    }

    public String getD_nop() {
        return d_nop;
    }

    public void setD_nop(String d_nop) {
        this.d_nop = d_nop;
    }

    public String getD_email() {
        return d_email;
    }

    public void setD_email(String d_email) {
        this.d_email = d_email;
    }

    public String getD_fdate() {
        return d_fdate;
    }

    public void setD_fdate(String d_fdate) {
        this.d_fdate = d_fdate;
    }

    public String getD_ftime() {
        return d_ftime;
    }

    public void setD_ftime(String d_ftime) {
        this.d_ftime = d_ftime;
    }

    public String getD_mobile() {
        return d_mobile;
    }

    public void setD_mobile(String d_mobile) {
        this.d_mobile = d_mobile;
    }

    public String getD_city() {
        return d_city;
    }

    public void setD_city(String d_city) {
        this.d_city = d_city;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }


    public DonatorClass() {
    }
}
