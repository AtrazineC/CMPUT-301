package com.example.simpleparadox.listycity;

public class City {
    private String city;
    private String province;

    City(String city, String province) {
        this.city = city;
        this.province = province;
    }

    String getCityName() {
        return this.city;
    }

    void setCityName(String city) {
        this.city = city;
    }

    String getProvinceName() {
        return this.province;
    }

    void setProvinceName(String province) {
        this.province = province;
    }
}
