package com.yunyou.yike.entity;

/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class MyAihao {
    private String name;
    private boolean isSelecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelecked() {
        return isSelecked;
    }

    public void setSelecked(boolean selecked) {
        isSelecked = selecked;
    }

    public MyAihao(String name, boolean isSelecked) {
        this.name = name;
        this.isSelecked = isSelecked;
    }
}
