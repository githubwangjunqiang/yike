package com.yunyou.yike.entity;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class Order {
    private String name;
    private int type;

    public Order(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
