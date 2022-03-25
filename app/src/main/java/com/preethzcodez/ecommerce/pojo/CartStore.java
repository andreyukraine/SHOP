package com.preethzcodez.ecommerce.pojo;

import java.util.List;

public class CartStore {

    private String store;
    private List<Cart> cartList;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
