package com.preethzcodez.ecommerce.objects;

import com.preethzcodez.ecommerce.pojo.Order;

import java.util.List;

public class OrdersDate {

    private String date;
    private int sort;
    private List<Order> orderList;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
