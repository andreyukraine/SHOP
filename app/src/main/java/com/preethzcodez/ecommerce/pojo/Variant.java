package com.preethzcodez.ecommerce.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Variant {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("option")
    @Expose
    private ArrayList<ProductOption> productOptions = null;
    @SerializedName("price")
    @Expose
    private List<Price> price;
    @SerializedName("storage")
    @Expose
    private List<Count> storage = null;

    private String product_id;

    private String variant_id;

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
