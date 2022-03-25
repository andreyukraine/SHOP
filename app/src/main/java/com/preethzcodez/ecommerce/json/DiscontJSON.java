package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.Discont;

import java.util.List;

public class DiscontJSON {
    @SerializedName("disconts")
    public List<Discont> discontList = null;

    public List<Discont> getDiscontList() { return discontList; }

    public void setDiscontList(List<Discont> discontList) { this.discontList = discontList; }
}
