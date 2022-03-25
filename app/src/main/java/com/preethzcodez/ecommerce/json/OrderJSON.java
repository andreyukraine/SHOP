package com.preethzcodez.ecommerce.json;

import com.preethzcodez.ecommerce.pojo.StatusDiscont;

import java.util.ArrayList;
import java.util.List;

public class OrderJSON {

    private int status;
    private String number;
    private ArrayList products;
    private double totalSum;
    private List<StatusDiscont> result;
    private String desc;

    private String discont;

    public ArrayList getProducts() { return products; }

    public void setProducts(ArrayList products) { this.products = products; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<StatusDiscont> getResult() { return result; }

    public void setResult(List<StatusDiscont> result) { this.result = result; }

    public double getTotalSum() { return totalSum; }

    public void setTotalSum(double totalSum) { this.totalSum = totalSum; }

    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc; }

    public String getDiscont() { return discont; }

    public void setDiscont(String discont) { this.discont = discont; }
}
