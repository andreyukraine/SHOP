package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.ProductAccess;
import java.util.List;

public class AccesProductsJSON {

    @SerializedName("products")
    public List<ProductAccess> products = null;

    public List<ProductAccess> getProducts() {
        return products;
    }

    public void setProducts(List<ProductAccess> products) {
        this.products = products;
    }
}
