package com.preethzcodez.ecommerce.objects;

import com.preethzcodez.ecommerce.pojo.Pics;

import java.util.List;

public class PicsDate {

    private String date;
    private List<Pics> orderList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Pics> getPicsList() {
        return orderList;
    }

    public void setPicsList(List<Pics> orderList) {
        this.orderList = orderList;
    }
}
