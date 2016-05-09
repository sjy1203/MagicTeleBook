package com.daydayup.magictelebook.main.bean;

/**
 * Created by Jallen on 2016/5/8.
 */
public class Record {
    private String Name;
    private int PersonImgId;
    private String Telno;
    private String Type;
    private String Area;
    private String Time;

    public Record(String Name,String Telno,String Type,String Area,String Time,
                  int PersonImgId){
        this.Name = Name;
        this.Telno = Telno;
        this.Type = Type;
        this.Area = Area;
        this.Time = Time;
        this.PersonImgId = PersonImgId;
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
