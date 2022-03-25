package com.preethzcodez.ecommerce.objects;

import com.google.android.gms.maps.model.LatLng;

public class Marker {

    private String time;
    private LatLng latLng;
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
