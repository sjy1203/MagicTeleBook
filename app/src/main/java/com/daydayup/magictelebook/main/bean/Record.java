package com.daydayup.magictelebook.main.bean;

import android.provider.ContactsContract;

/**
 * Created by Jallen on 2016/5/8.
 */
public class Record {
    private String _ID; //contact_id
    private String Name; //姓名
    private String Telno;//电话号码
    private String Type;//来电,去电,未接等
    private String Area;//地区
    private String Duration;//通话时间
    private long PersonImgId;// 用户缩略图(查询值)
    private String Date;

    public Record(String _ID,String Name,String Telno,String Type,String Area,String Duration,String Date,
                  long PersonImgId){
        this._ID = _ID;
        this.Name = Name;
        this.Telno = Telno;
        this.Type = Type;
        this.Area = Area;
        this.Duration = Duration;
        this.Date = Date;
        this.PersonImgId = PersonImgId;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPersonImgId(long personImgId) {
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

    public String get_ID() {
        return _ID;
    }

    public String getArea() {
        return Area;
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

    public long getPersonImgId() {
        return PersonImgId;
    }

    @Override
    public String toString() {
        return "name: "+getName()+"\nlkid: "+get_ID()+"\ntype: "+getType()+"\nduration: "+getDuration()+"\ndate: "+getDate()+"\nimgid: "+getPersonImgId()+"area: "+getArea();
    }
}
