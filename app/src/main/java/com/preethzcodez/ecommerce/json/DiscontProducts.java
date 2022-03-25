package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.Discont;
import com.preethzcodez.ecommerce.pojo.ProductDiscont;
import com.preethzcodez.ecommerce.pojo.StatusDiscont;

import java.util.List;

public class DiscontProducts {

    public String idDiscont;
    public List<StatusDiscont> status;
    public int colgift;
    public List<ProductDiscont> products = null;

    public List<StatusDiscont> getStatus() {
        return status;
    }

    public void setStatus(List<StatusDiscont> status) {
        this.status = status;
    }

    public String getIdDiscont() {
        return idDiscont;
    }

    public void setIdDiscont(String idDiscont) {
        this.idDiscont = idDiscont;
    }

    public int getColgift() {
        return colgift;
    }

    public void setColgift(int colgift) {
        this.colgift = colgift;
    }

    public List<ProductDiscont> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDiscont> products) {
        this.products = products;
    }
}
