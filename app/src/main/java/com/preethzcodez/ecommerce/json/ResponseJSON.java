package com.preethzcodez.ecommerce.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.CountVariant;
import com.preethzcodez.ecommerce.pojo.Offer;
import com.preethzcodez.ecommerce.pojo.Option;
import com.preethzcodez.ecommerce.pojo.PriceType;
import com.preethzcodez.ecommerce.pojo.PriceVariant;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.Ranking;
import com.preethzcodez.ecommerce.pojo.Store;

public class ResponseJSON {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("rankings")
    @Expose
    private List<Ranking> rankings = null;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("storage")
    @Expose
    private List<Store> storage = null;
    @SerializedName("prices")
    @Expose
    private List<PriceType> prices = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("counts")
    @Expose
    private List<CountVariant> countVariant = null;
    @SerializedName("pricesvariant")
    @Expose
    private List<PriceVariant> priceVariants = null;
    @SerializedName("info")
    @Expose
    private List<ProductJSON> jsonList = null;
    @SerializedName("status")
    @Expose
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProductJSON> getJsonList() {
        return jsonList;
    }

    public void setJsonList(List<ProductJSON> jsonList) {
        this.jsonList = jsonList;
    }

    public List<PriceVariant> getPriceVariants() { return priceVariants; }

    public List<CountVariant> getCountVariant() { return countVariant; }

    private List<Client> clients = null;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void setStorage(List<Store> storage) {
        this.storage = storage;
    }

    public void setPrices(List<PriceType> prices) {
        this.prices = prices;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }

    public List<Store> getStorage() {
        return storage;
    }

    public List<PriceType> getPrices() {
        return prices;
    }
}
