package com.daydayup.magictelebook.main.bean;

/**
 * Created by Jallen on 2016/5/8.
 */
public class Record {
    private String Name;
    private int PersonImgId;
    private String Telno;
    private int TypeImgId;
    private String Type;
    private int AreaImgId;
    private String Area;
    private int TimeImgId;
    private String Time;

    public Record(String Name,String Telno,String Type,String Area,String Time,
                  int TypeImgId,int AreaImgId,int TimeImgId,int PersonImgId){
        this.Name = Name;
        this.Telno = Telno;
        this.Type = Type;
        this.Area = Area;
        this.Time = Time;
        this.TypeImgId = TypeImgId;
        this.AreaImgId = AreaImgId;
        this.TimeImgId = TimeImgId;
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

    public int getAreaImgId() {
        return AreaImgId;
    }

    public int getTimeImgId() {
        return TimeImgId;
    }

    public int getTypeImgId() {
        return TypeImgId;
    }

    public int getPersonImgId() {
        return PersonImgId;
    }
}
