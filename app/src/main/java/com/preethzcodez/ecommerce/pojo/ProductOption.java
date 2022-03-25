package com.preethzcodez.ecommerce.pojo;

public class ProductOption {

    private String opt_id;
    private String opt_val;
    private String var_id;

    public String getVar_id() { return var_id; }

    public void setVar_id(String var_id) { this.var_id = var_id; }

    public String getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public String getOpt_val() {
        return opt_val;
    }

    public void setOpt_val(String opt_val) {
        this.opt_val = opt_val;
    }
}
