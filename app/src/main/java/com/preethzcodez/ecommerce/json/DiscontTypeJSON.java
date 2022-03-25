package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.TypeDiscont;
import java.util.List;

public class DiscontTypeJSON {

    @SerializedName("disconts_type")
    public List<TypeDiscont> disconts_type = null;

    public List<TypeDiscont> getDisconts_type() {
        return disconts_type;
    }

    public void setDisconts_type(List<TypeDiscont> disconts_type) {
        this.disconts_type = disconts_type;
    }
}
