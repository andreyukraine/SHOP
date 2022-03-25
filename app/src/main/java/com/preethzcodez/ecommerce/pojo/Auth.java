package com.preethzcodez.ecommerce.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auth {
    @SerializedName("result")
    @Expose
    private int result;

    public int getResult() {
        return result;
    }

}
