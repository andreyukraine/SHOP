package com.preethzcodez.ecommerce.pojo;

public class Count {

    private String store;
    private double count;
    private String name_store;

    public String getName_store() { return name_store; }

    public void setName_store(String name_store) { this.name_store = name_store; }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
