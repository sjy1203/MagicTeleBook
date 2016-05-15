package com.daydayup.magictelebook.main.bean;

import java.io.Serializable;

/**
 * Created by Jay on 16/5/10.
 */
public class Contact implements Serializable{
    private String _ID;//contact_id
    private String Name;//姓名
    private String Telno;//电话号码
    private String Area;//地理位置
    private String Weather;//天气状况
    private int PersonImgId;// 用户缩略图(默认值)

    public Contact(String _ID,String Name,String Telno,String Area,String Weather,int PersonImgId){
        this._ID = _ID;
        this.Name = Name;
        this.Telno = Telno;
        this.Area = Area;
        this.Weather = Weather;
        this.PersonImgId = PersonImgId;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelno() {
        return Telno;
    }

    public void setTelno(String telno) {
        Telno = telno;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public int getPersonImgId() {
        return PersonImgId;
    }

    public void setPersonImgId(int personImgId) {
        PersonImgId = personImgId;
    }
}
