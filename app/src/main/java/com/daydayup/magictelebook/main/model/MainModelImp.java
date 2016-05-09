package com.daydayup.magictelebook.main.model;

import android.content.Context;

import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;

/**
 * Created by Jay on 16/5/9.
 */
public class MainModelImp implements IMainModel{
    Context context = null;
    public MainModelImp(Context context){
        this.context = context;
    }

    //TODO:@duanpeiyun
    @Override
    public void initRecords(int num, OnRecordsInitListener onRecordsInitListener) {

    }
    //TODO:@duanpeiyun
    @Override
    public void initBriefContacts(OnBriefContactsInitListener onBriefContactsInitListener) {

    }
}
