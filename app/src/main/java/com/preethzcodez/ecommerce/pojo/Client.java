package com.preethzcodez.ecommerce.pojo;

import java.util.ArrayList;

public class Client {

    private String name;
    private String xml_id;
    private double debt;
    private String price_id;
    private String client_type;
    private String address;
    private ArrayList address2;
    private String phone;

    public ArrayList getAddress2() {
        return address2;
    }

    public void setAddress2(ArrayList address2) {
        this.address2 = address2;
    }

    public String getClient_type() { return client_type; }

    public void setClient_type(String client_type) { this.client_type = client_type; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXml_id() {
        return xml_id;
    }

    public void setXml_id(String xml_id) {
        this.xml_id = xml_id;
    }

    public double getDebt() { return debt; }

    public void setDebt(double debt) { this.debt = debt; }

    public String getPrice_id() { return price_id; }

    public void setPrice_id(String price_id) { this.price_id = price_id; }

    public String getType() { return client_type; }

    public void setType(String client_type) { this.client_type = client_type; }
}
