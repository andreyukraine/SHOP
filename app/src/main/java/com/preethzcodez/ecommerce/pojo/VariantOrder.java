package com.preethzcodez.ecommerce.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VariantOrder {


    private String id;
    private String sku;
    private String barcode;
    private ArrayList<ProductOption> productOptions = null;
    private List<Price> price;
    private List<Count> storage = null;
    private String product_id;
    private String variant_id;
    private int isGift;
    private int quantity;
    private int quantity_1c;
    private int manual_discont;
    private int auto_discont;
    private double price_value;
    private double price_value_1c;

    public double getPrice_value() { return price_value; }

    public void setPrice_value(double price_value) { this.price_value = price_value; }

    public double getPrice_value_1c() { return price_value_1c; }

    public void setPrice_value_1c(double price_value_1c) { this.price_value_1c = price_value_1c; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getQuantity_1c() { return quantity_1c; }

    public void setQuantity_1c(int quantity_1c) { this.quantity_1c = quantity_1c; }

    public int getManual_discont() { return manual_discont; }

    public void setManual_discont(int manual_discont) { this.manual_discont = manual_discont; }

    public int getAuto_discont() { return auto_discont; }

    public void setAuto_discont(int auto_discont) { this.auto_discont = auto_discont; }

    public int getIsGift() { return isGift; }

    public void setIsGift(int isGift) { this.isGift = isGift; }

    public String getVariant_id() {return variant_id;}

    public void setVariant_id(String variant_id) {this.variant_id = variant_id;}

    public String getProduct_id() { return product_id; }

    public void setProduct_id(String product_id) { this.product_id = product_id; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Price> getPrice() {
        return price;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    public List<Count> getStorage() {
        return storage;
    }

    public void setStorage(List<Count> storage) {
        this.storage = storage;
    }

    public List<Count> getCount() {
        return storage;
    }

    public void setCount(List<Count> count) {
        this.storage = count;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setProductOptions(ArrayList<ProductOption> productOptions) { this.productOptions = productOptions; }

}
