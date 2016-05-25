package com.daydayup.magictelebook.main.model;

import android.graphics.Bitmap;

import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.callback.DefaultListener;
import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;
import com.daydayup.magictelebook.main.callback.OnSelectContactListener;

/**
 * Created by Jay on 16/5/9.
 */
public interface IMainModel {
    //详见MainPresenter函数说明
    void initRecords(int num, OnRecordsInitListener onRecordsInitListener);
    void initContacts(int num,OnBriefContactsInitListener onBriefContactsInitListener);

    void incrementRecords(int offset,int num,OnRecordsInitListener onRecordsInitListener);

    void searchRecordsByName(String name,OnRecordsInitListener onRecordsInitListener);
    void searchRecordsByNumber(String number,OnRecordsInitListener onRecordsInitListener);
    void searchRecordsByAll(String searchStr,OnRecordsInitListener onRecordsInitListener);

    //system
    void insertContactSys(BriefContact briefContact, DefaultListener defaultListener);
    void updateContactSys(BriefContact briefContact, DefaultListener defaultListener);
    void deleteContactSys(Long rawContactId,DefaultListener defaultListener);
    //void selectContactSys(String number, OnSelectContactListener onSelectContactListener);

    void deleteRecordFromSys(String id,DefaultListener defaultListener);

    //sqliteDb
    void replaceContactFromSqlite(BriefContact briefContact, Bitmap bitmap, DefaultListener defaultListener);
    void deleteContactFromSqlite(BriefContact briefContact,DefaultListener defaultListener);
    void selectContactFromSqlite(String number, OnSelectContactListener onSelectContactListener);

    //extern
    long getRawContactIDByNumber(String number);
}
