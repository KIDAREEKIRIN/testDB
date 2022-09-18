package com.example.testdb.DutyExample.DTO;

import com.google.gson.annotations.SerializedName;

public class MemberShip {

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("number")
    private int number;

    @SerializedName("nickName")
    private String nickName;

    // 비밀번호 암호+복호.
    @SerializedName("pw")
    private String pw;

    // 아이디 찾기 생성.
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getNickName() {
        return nickName;
    }

    // 아이디 찾기 게터.
    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getEmail() {
        return email;
    }

    public int getNumber() {
        return number;
    }
}
