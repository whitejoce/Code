package com.example.newweather

class WeatherBean(var date: String, var status: String, var high: String, var low: String) {

    override fun toString(): String {
        return "weatherBean{" +
                "date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                '}'
    }
}