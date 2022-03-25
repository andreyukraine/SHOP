package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.StatusDiscont;
import java.util.List;

public class OrderResponse {

    @SerializedName("status")
    List<StatusDiscont> status;

    public List<StatusDiscont> getStatus() {
        return status;
    }

    public void setStatus(List<StatusDiscont> status) {
        this.status = status;
    }
}
