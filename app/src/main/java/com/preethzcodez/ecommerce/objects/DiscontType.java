package com.preethzcodez.ecommerce.objects;

import com.preethzcodez.ecommerce.pojo.TypeDiscont;

import java.util.List;

public class DiscontType {
    private String type;
    private List<TypeDiscont> typeDiscont = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TypeDiscont> getTypeDiscont() {
        return typeDiscont;
    }

    public void setTypeDiscont(List<TypeDiscont> typeDiscont) {
        this.typeDiscont = typeDiscont;
    }
}
