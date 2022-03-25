package com.preethzcodez.ecommerce.objects;

public class Delivery {

    private int isNewPost;
    private int isPickup;
    private int isLogist;
    private String city;
    private String point;
    private String address;

    public int getIsNewPost() {
        return isNewPost;
    }

    public void setIsNewPost(int isNewPost) {
        this.isNewPost = isNewPost;
    }

    public int getIsPickup() {
        return isPickup;
    }

    public void setIsPickup(int isPickup) {
        this.isPickup = isPickup;
    }

    public int getIsLogist() {
        return isLogist;
    }

    public void setIsLogist(int isLogist) {
        this.isLogist = isLogist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
