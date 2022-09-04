package com.example.testdb.DutyExample.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DutyTitle {

    // Item 값을 받아온다 -> Retrofit 값.
    @SerializedName("title_id") Integer title_id;
    @SerializedName("title_name") String title_name;
    @SerializedName("title_order") Integer title_order;
    @SerializedName("duty_id") Integer duty_id;

//    private String itemTitle;
    // 하위 리사이클러뷰 아이템으로 정의한 subItemList를 전역변수로 선언한다.
    List<SubItem> subItemList;

    public DutyTitle(Integer title_id, String title_name, Integer title_order, Integer duty_id, List<SubItem> subItemList) {
        this.title_id = title_id;
        this.title_name = title_name;
        this.title_order = title_order;
        this.duty_id = duty_id;
        this.subItemList = subItemList;
    }

//    public Item(String itemTitle, List<SubItem> subItemList) {
//        this.itemTitle = itemTitle;
//        // 하위 리사이클러뷰
//        this.subItemList = subItemList;
//    }

//    public String getItemTitle() {
//        return itemTitle;
//    }
//
//    public void setItemTitle(String itemTitle) {
//        this.itemTitle = itemTitle;
//    }


    public Integer getTitle_id() {
        return title_id;
    }

    public void setTitle_id(Integer title_id) {
        this.title_id = title_id;
    }

    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public Integer getTitle_order() {
        return title_order;
    }

    public void setTitle_order(Integer title_order) {
        this.title_order = title_order;
    }

    public Integer getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(Integer duty_id) {
        this.duty_id = duty_id;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }
}
