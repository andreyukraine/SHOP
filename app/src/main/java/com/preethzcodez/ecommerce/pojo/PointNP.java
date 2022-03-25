package com.preethzcodez.ecommerce.pojo;

public class PointNP {

    private String name;
    private String code_np;
    private String id_city_code_np;
    private int weight;

    public String getId_city_code_np() {
        return id_city_code_np;
    }

    public void setId_city_code_np(String id_city_code_np) {
        this.id_city_code_np = id_city_code_np;
    }

    public String getCode_np() {
        return code_np;
    }

    public void setCode_np(String code_np) {
        this.code_np = code_np;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
