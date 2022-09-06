package com.example.testdb.DutyExample.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DutyStep {

    @SerializedName("step_id") Integer step_id;
    @SerializedName("step_name") String step_name;
    @SerializedName("step_check") Integer step_check;
    @SerializedName("title_id") Integer title_id;

    // CRUD 추가 내용.
    @Expose
    @SerializedName("success") private Boolean success;

    @Expose
    @SerializedName("message") private String message;

    public DutyStep(Integer step_id, String step_name, Integer step_check, Integer title_id) {
        this.step_id = step_id;
        this.step_name = step_name;
        this.step_check = step_check;
        this.title_id = title_id;
    }

    public Integer getStep_id() {
        return step_id;
    }

    public void setStep_id(Integer step_id) {
        this.step_id = step_id;
    }

    public String getStep_name() {
        return step_name;
    }

    public void setStep_name(String step_name) {
        this.step_name = step_name;
    }

    public Integer getStep_check() {
        return step_check;
    }

    public void setStep_check(Integer step_check) {
        this.step_check = step_check;
    }

    public Integer getTitle_id() {
        return title_id;
    }

    public void setTitle_id(Integer title_id) {
        this.title_id = title_id;
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

    //    private String subItemTitle;
//
//    public DutyStep(String subItemTitle) {
//        this.subItemTitle = subItemTitle;
//    }
//
//
//    public String getSubItemTitle() {
//        return subItemTitle;
//    }
//
//    public void setSubItemTitle(String subItemTitle) {
//        this.subItemTitle = subItemTitle;
//    }
}
