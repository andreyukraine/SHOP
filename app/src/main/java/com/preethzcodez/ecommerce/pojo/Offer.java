package com.preethzcodez.ecommerce.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Offer {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("pdc_id")
    @Expose
    private String pdc_id;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("option")
    @Expose
    private ArrayList<ProductOption> productOptions = null;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getSku() { return sku; }

    public void setSku(String sku) { this.sku = sku; }

    public String getPdc_id() { return pdc_id; }

    public void setPdc_id(String pdc_id) { this.pdc_id = pdc_id; }

    public String getBarcode() { return barcode; }

    public void setBarcode(String barcode) { this.barcode = barcode; }

    public ArrayList<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(ArrayList<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }
}
