package com.example.newweather;

import androidx.annotation.NonNull;

public class WeatherBean {
    String date;
    String status;
    String high;
    String low;

    public WeatherBean(String date, String status, String high, String low) {
        this.date = date;
        this.status = status;
        this.high = high;
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    @NonNull
    @Override
    public String toString() {
        return "weatherBean{" +
                "date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                '}';
    }
}

