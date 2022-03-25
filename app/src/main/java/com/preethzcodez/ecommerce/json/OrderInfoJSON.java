package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderInfoJSON {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_bank")
    @Expose
    private int is_bank;
    @SerializedName("pickup")
    @Expose
    private int pickup;
    @SerializedName("shipping_date")
    @Expose
    private String shipping_date;
    @SerializedName("logist")
    @Expose
    private int logist;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("number_order")
    @Expose
    private String number_order;
    @SerializedName("products")
    @Expose
    private ArrayList products;
    @SerializedName("discont")
    @Expose
    private String discont;
    @SerializedName("sum_order")
    @Expose
    private double sum_order;
    @SerializedName("address_code")
    @Expose
    private String address_code;
    @SerializedName("delivery_np")
    @Expose
    private int delivery_np;
    @SerializedName("city_code_np")
    @Expose
    private String city_code_np;
    @SerializedName("post_code_np")
    @Expose
    private String post_code_np;

    public String getAddress_code() {
        return address_code;
    }

    public void setAddress_code(String address_code) {
        this.address_code = address_code;
    }

    public String getShipping_date() { return shipping_date; }

    public void setShipping_date(String shipping_date) { this.shipping_date = shipping_date; }

    public int getIs_bank() { return is_bank; }

    public void setIs_bank(int is_bank) { this.is_bank = is_bank; }

    public int getPickup() { return pickup; }

    public void setPickup(int pickup) { this.pickup = pickup; }

    public int getLogist() { return logist; }

    public void setLogist(int logist) { this.logist = logist; }

    public double getSum_order() { return sum_order; }

    public void setSum_order(double sum_order) { this.sum_order = sum_order; }

    public String getNumber_order() { return number_order; }

    public void setNumber_order(String number_order) { this.number_order = number_order; }

    public ArrayList getProducts() { return products; }

    public void setProducts(ArrayList products) { this.products = products; }

    public String getNumber() {
        return number_order;
    }

    public void setNumber(String number_order) {
        this.number_order = number_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDiscont() { return discont; }

    public void setDiscont(String discont) { this.discont = discont; }

    public int getDelivery_np() {
        return delivery_np;
    }

    public void setDelivery_np(int delivery_np) {
        this.delivery_np = delivery_np;
    }

    public String getCity_code_np() {
        return city_code_np;
    }

    public void setCity_code_np(String city_code_np) {
        this.city_code_np = city_code_np;
    }

    public String getPost_code_np() {
        return post_code_np;
    }

    public void setPost_code_np(String post_code_np) {
        this.post_code_np = post_code_np;
    }
}
