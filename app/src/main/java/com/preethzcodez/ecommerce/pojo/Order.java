package com.preethzcodez.ecommerce.pojo;

public class Order {

    private int id;
    private String number;
    private String number_1c;
    private String date;
    private String store_id;
    private double total;
    private double total_1c;
    private String client_code;
    private String comment;
    private int isBase;
    private int status;
    private int logist;
    private int pickup;
    private String discont;
    private String shipping_date;
    private String address_code;
    private int is_bank;

    public String getAddress_code() {
        return address_code;
    }

    public void setAddress_code(String address_code) {
        this.address_code = address_code;
    }

    public String getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(String shipping_date) {
        this.shipping_date = shipping_date;
    }

    public int getIs_bank() {
        return is_bank;
    }

    public void setIs_bank(int is_bank) {
        this.is_bank = is_bank;
    }

    public int getLogist() { return logist; }

    public void setLogist(int logist) { this.logist = logist; }

    public int getPickup() { return pickup; }

    public void setPickup(int pickup) { this.pickup = pickup; }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() { return number; }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public double getTotal() { return total; }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getClient_code() { return client_code; }

    public void setClient_code(String client_code) { this.client_code = client_code; }

    public int getBase() { return isBase; }

    public void setBase(int base) { isBase = base; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getNumber_1c() { return number_1c; }

    public void setNumber_1c(String number_1c) { this.number_1c = number_1c; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public int getIsBase() { return isBase; }

    public void setIsBase(int isBase) { this.isBase = isBase; }

    public double getTotal_1c() { return total_1c; }

    public void setTotal_1c(double total_1c) { this.total_1c = total_1c; }

    public String getDiscont() { return discont; }

    public void setDiscont(String discont) { this.discont = discont; }
}
