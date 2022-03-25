package com.preethzcodez.ecommerce.objects;

import com.preethzcodez.ecommerce.pojo.ProductDiscont;

public class SelectGift {

    ProductDiscont product;
    int countGift;

    public SelectGift(ProductDiscont product, int countGift) {
        this.product = product;
        this.countGift = countGift;
    }

    public ProductDiscont getProduct() {
        return product;
    }

    public void setProduct(ProductDiscont product) {
        this.product = product;
    }

    public int getCountGift() {
        return countGift;
    }

    public void setCountGift(int countGift) {
        this.countGift = countGift;
    }
}
