package com.preethzcodez.ecommerce.pojo;

public class Pics {

    String client_id;
    String pics_name;
    String gps_lat;
    String gps_lon;
    String date;
    String time;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getPics_name() { return pics_name; }

    public void setPics_name(String pics_name) {
        this.pics_name = pics_name;
    }

    public String getGps_lat() {
        return gps_lat;
    }

    public void setGps_lat(String gps_lat) {
        this.gps_lat = gps_lat;
    }

    public String getGps_lon() {
        return gps_lon;
    }

    public void setGps_lon(String gps_lon) {
        this.gps_lon = gps_lon;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}
