package com.daydayup.magictelebook.main.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Jay on 16/4/24.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="contactDb";
    public static final int DB_VERSION=1;

    //column name
    public static final String NUMBER = "number";
    public static final String NAME = "name";
    public static final String AREA = "area";
    public static final String BIRTH = "birth";
    public static final String LOGO = "logo";
    public static final String BLACK = "black";

    //table
    public static final String TABLE_CONTACT ="contact";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+TABLE_CONTACT+"("+
            NUMBER+" varchar primary key," +
            NAME +" varchar,"+
            AREA +" varchar,"+
            BIRTH+" varchar,"+
            LOGO+" blob,"+
            BLACK+" int)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static SQLiteDatabase getWriteDb(Context context){
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        return sqLiteHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getReadDb(Context context){
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        return sqLiteHelper.getReadableDatabase();
    }

}
