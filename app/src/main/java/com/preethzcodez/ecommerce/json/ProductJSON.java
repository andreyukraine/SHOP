package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.Count;
import com.preethzcodez.ecommerce.pojo.Price;
import java.util.List;

public class ProductJSON {
    @SerializedName("var_id")
    @Expose
    private String var_id = null;
    @SerializedName("storage")
    @Expose
    private List<Count> countList = null;
    @SerializedName("client_price")
    @Expose
    private List<Price> priceList = null;

    public String getVar_id() {
        return var_id;
    }

    public void setVar_id(String var_id) {
        this.var_id = var_id;
    }

    public List<Count> getCountList() {
        return countList;
    }

    public void setCountList(List<Count> countList) {
        this.countList = countList;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
}
