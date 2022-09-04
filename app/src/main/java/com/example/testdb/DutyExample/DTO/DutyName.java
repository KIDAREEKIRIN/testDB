package com.example.testdb.DutyExample.DTO;

import com.google.gson.annotations.SerializedName;

public class DutyName {
    @SerializedName("duty_id") Integer duty_id;
    @SerializedName("duty_name") String duty_name;
    @SerializedName("cate_id") Integer cate_id;
    @SerializedName("user_id") Integer user_id;

    public DutyName(Integer duty_id, String duty_name, Integer cate_id, Integer user_id) {
        this.duty_id = duty_id;
        this.duty_name = duty_name;
        this.cate_id = cate_id;
        this.user_id = user_id;
    }

    public Integer getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(Integer duty_id) {
        this.duty_id = duty_id;
    }

    public String getDuty_name() {
        return duty_name;
    }

    public void setDuty_name(String duty_name) {
        this.duty_name = duty_name;
    }

    public Integer getCate_id() {
        return cate_id;
    }

    public void setCate_id(Integer cate_id) {
        this.cate_id = cate_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
