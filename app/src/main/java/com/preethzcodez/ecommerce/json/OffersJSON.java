package com.preethzcodez.ecommerce.json;

import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.Offer;
import java.util.List;

public class OffersJSON {
    @SerializedName("offers")
    public List<Offer> offerList = null;

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }
}
