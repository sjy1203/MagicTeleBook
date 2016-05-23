package com.daydayup.magictelebook.main.bean;

import java.io.Serializable;

/**
 * Created by Jay on 16/5/22.
 */
public class BriefContact implements Serializable{

    Long contactId;
    String lookUpKey;  //此两个是主键
    String name;
    String number;
    Long photoId;
    String sortKey;

    String area = null;
    String temperature = null;
    String weather = null;

    boolean isBlack = false;
    public BriefContact(){

    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getLookUpKey() {
        return lookUpKey;
    }

    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "lookUPkey: "+getLookUpKey()+"\nname: "+name+"\nnumber: "+number+"\nphoteid:"+photoId+"\nsortkey:"+sortKey;
    }
}
