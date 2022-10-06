package com.example.testdb.DutyExample.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DutyName {
    @SerializedName("duty_id") Integer duty_id;
    @SerializedName("duty_name") String duty_name;
    @SerializedName("cate_id") Integer cate_id;
    // name_check 체크박스 용 추가. 22. 10. 06.(목) / 값 추가.
    @SerializedName("name_check") Integer name_check;
    // user_id  -> number 수정. 22.10.05.(목) / 멤버 인덱스 수정.
    @SerializedName("number") Integer number;

    // CRUD 추가 내용.
    @Expose
    @SerializedName("success") private Boolean success;

    @Expose
    @SerializedName("message") private String message;

    public DutyName(Integer duty_id, String duty_name, Integer cate_id, Integer name_check,Integer number) {
        this.duty_id = duty_id;
        this.duty_name = duty_name;
        this.cate_id = cate_id;
        this.name_check = name_check;
        this.number = number;
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

    public Integer getName_check() {
        return name_check;
    }

    public void setName_check(Integer name_check) {
        this.name_check = name_check;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
