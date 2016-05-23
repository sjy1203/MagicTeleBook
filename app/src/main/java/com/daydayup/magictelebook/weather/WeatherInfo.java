package com.daydayup.magictelebook.weather;

import java.io.Serializable;

/**
 * Created by Jay on 16/5/23.
 */
public class WeatherInfo implements Serializable{
    private String temperature; //温度
    private String ganmao;//小贴士

    private String windDirection; //风向
    private String windPower; //风力
    private String tempHighest; //最高气温
    private  String tempLowest; //最低气温
    private String weatherStatus; //天气情况
    private String date; //日期

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getTempHighest() {
        return tempHighest;
    }

    public void setTempHighest(String tempHighest) {
        this.tempHighest = tempHighest;
    }

    public String getTempLowest() {
        return tempLowest;
    }

    public void setTempLowest(String tempLowest) {
        this.tempLowest = tempLowest;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "date:"+getDate()+"\ntemperature:"+getTemperature()+"\ntype:"+getWeatherStatus()+"\ndir:"+getWindDirection()+"\npower:"+getWindPower()+"\nhigh:"+getTempHighest()+"\nlow:"+getTempLowest()+"\nganmao:"+getGanmao();
    }
}
