package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/1.
 */

public class CityList {
    private String mName;
    private List<Data> mList;

    public CityList() {
    }

    public CityList(String name, List<Data> list) {
        mName = name;
        mList = list;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Data> getList() {
        return mList;
    }

    public void setList(List<Data> list) {
        mList = list;
    }

    public static class Data {
        private String id;

        public Data() {
        }

        public Data(String id, String cityName) {
            this.id = id;
            this.cityName = cityName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        private String cityName;
    }

}
