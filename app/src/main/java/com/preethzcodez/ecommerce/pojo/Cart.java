package com.preethzcodez.ecommerce.pojo;

public class Cart {

    private Integer id;
    private Integer itemQuantity;
    private Product product;
    private Variant variant;
    private String order_number;
    private double price_value;
    private String store_id;
    private String order_client;

    public String getOrder_client() { return order_client; }

    public void setOrder_client(String order_client) { this.order_client = order_client; }

    public String getStore_id() { return store_id; }

    public void setStore_id(String store_id) { this.store_id = store_id; }

    public String getOrder_number() { return order_number; }

    public void setOrder_number(String order_number) { this.order_number = order_number; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getPrice_value() { return price_value; }

    public void setPrice_value(double price_value) { this.price_value = price_value; }
}
