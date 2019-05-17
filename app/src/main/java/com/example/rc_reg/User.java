package com.example.rc_reg;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@IgnoreExtraProperties
public class User {
    public String name;
    public String email;
    public String phone;
    public String location;
    public String score;
    public String voucher_code;
    public String time;



    public User(){

    }

    public User(String name, String email,String phone, String location, String voucher_code, String time,String score){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.location=location;
        this.voucher_code=voucher_code;
        this.time=time;
        this.score=score;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public String getLocation(){
        return location;
    }
    public String getVoucher_code(){
        return time;
    }
    public String getTime(){
        return time;
    }
    public String getScore(){
        return score;
    }



    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public void setVoucher_code(String voucher_code){
        this.voucher_code=voucher_code;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setScore(String score){
        this.score=score;
    }
}

