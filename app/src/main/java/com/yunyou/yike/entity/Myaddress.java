package com.yunyou.yike.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by ${王俊强} on 2017/6/7.
 */

public class Myaddress implements Parcelable {


    private String address ;
    private String addressInfo ;
    private LatLng mLatLng;
    private String province;
    private String provinceId;
    private String city;
    private String cityId;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    private String county;
    private String countyId;

    public Myaddress() {
    }

    public Myaddress(String address, String addressInfo, LatLng latLng, String province, String city, String county) {
        this.address = address;
        this.addressInfo = addressInfo;
        mLatLng = latLng;
        this.province = province;
        this.city = city;
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "Myaddress{" +
                "address='" + address + '\'' +
                ", addressInfo='" + addressInfo + '\'' +
                ", mLatLng=" + mLatLng +
                ", province='" + province + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", city='" + city + '\'' +
                ", cityId='" + cityId + '\'' +
                ", county='" + county + '\'' +
                ", countyId='" + countyId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.addressInfo);
        dest.writeParcelable(this.mLatLng, flags);
        dest.writeString(this.province);
        dest.writeString(this.provinceId);
        dest.writeString(this.city);
        dest.writeString(this.cityId);
        dest.writeString(this.county);
        dest.writeString(this.countyId);
    }

    protected Myaddress(Parcel in) {
        this.address = in.readString();
        this.addressInfo = in.readString();
        this.mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        this.province = in.readString();
        this.provinceId = in.readString();
        this.city = in.readString();
        this.cityId = in.readString();
        this.county = in.readString();
        this.countyId = in.readString();
    }

    public static final Parcelable.Creator<Myaddress> CREATOR = new Parcelable.Creator<Myaddress>() {
        @Override
        public Myaddress createFromParcel(Parcel source) {
            return new Myaddress(source);
        }

        @Override
        public Myaddress[] newArray(int size) {
            return new Myaddress[size];
        }
    };
}
