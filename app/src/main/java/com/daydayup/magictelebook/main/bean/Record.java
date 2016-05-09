package com.daydayup.magictelebook.main.bean;

import android.provider.ContactsContract;

/**
 * Created by Jallen on 2016/5/8.
 */
public class Record {
    private String _ID; //contact_id
    private String Name; //姓名
    private int PersonImgId;// 用户缩略图(默认值)
    private String Telno;//电话号码
    private String Type;//来电,去电,未接等
    private String Area;//地区
    private String Time;//最近一次通话时间

    public Record(String _ID,String Name,String Telno,String Type,String Area,String Time,
                  int PersonImgId){
        this._ID = _ID;
        this.Name = Name;
        this.Telno = Telno;
        this.Type = Type;
        this.Area = Area;
        this.Time = Time;
        this.PersonImgId = PersonImgId;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPersonImgId(int personImgId) {
        PersonImgId = personImgId;
    }

    public void setTelno(String telno) {
        Telno = telno;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setTime(String time) {
        Time = time;
    }
    public String get_ID() {
        return _ID;
    }
    public String getArea() {
        return Area;
    }

    public String getTime() {
        return Time;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public String getTelno() {
        return Telno;
    }

    public int getPersonImgId() {
        return PersonImgId;
    }

}
