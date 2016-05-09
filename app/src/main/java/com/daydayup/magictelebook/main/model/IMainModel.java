package com.daydayup.magictelebook.main.model;

import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;

/**
 * Created by Jay on 16/5/9.
 */
public interface IMainModel {
    //详见MainPresenter函数说明
    void initRecords(int num, OnRecordsInitListener onRecordsInitListener);
    void initBriefContacts(OnBriefContactsInitListener onBriefContactsInitListener);
}
