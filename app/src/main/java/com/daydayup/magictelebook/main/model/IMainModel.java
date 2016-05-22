package com.daydayup.magictelebook.main.model;

import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;

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
}
