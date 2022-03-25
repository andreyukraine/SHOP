package com.preethzcodez.ecommerce.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PriceVariant {

    @SerializedName("var_id")
    @Expose
    private String var_id;
    @SerializedName("price")
    @Expose
    private List<Price> priceList = null;

    public String getVar_id() {
        return var_id;
    }

    public void setVar_id(String var_id) {
        this.var_id = var_id;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
}
