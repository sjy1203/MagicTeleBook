package com.daydayup.magictelebook.main.presenter;

import android.content.Context;

import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;
import com.daydayup.magictelebook.main.model.IMainModel;
import com.daydayup.magictelebook.main.model.MainModelImp;
import com.daydayup.magictelebook.main.view.IMainView;
import com.daydayup.magictelebook.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16/5/9.
 */
public class MainPresenter {
    private static volatile MainPresenter mainPresenter = null;
    private Context context = null;
    private IMainModel mainModel = null;
    private IMainView mainView = null;
    private MainPresenter(Context context,IMainView mainView){
        this.context = context;
        this.mainModel = new MainModelImp(context);
        this.mainView  = mainView;
    }
    //单例模式
    public static MainPresenter getInstance(Context context, IMainView mainView){
        if (mainPresenter == null){
            synchronized (MainPresenter.class){
                if (mainPresenter==null){
                    mainPresenter = new MainPresenter(context,mainView);
                }
            }
        }
        return mainPresenter;
    }

    /*
    @param num:通话记录数量
    @return void
    @brief 从_id=0开始,获取num个通话记录用来初始化列表(适配器)
     */
    public void initRecords(int num){
        mainModel.initRecords(num, new OnRecordsInitListener() {
            @Override
            public void onLoadSuccess(List<Record> recordList) {
                List<Record> records = mainView.getRecordAdapter().getList();
                if (records==null) records=new ArrayList<Record>();
                records.addAll(recordList);
                mainView.getRecordAdapter().notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String msg) {
                T.showShort(context,msg);
            }
        });
    }


    /*
    @param void
    @return void
    @brief 获取所有联系人的简单信息(姓名,_ID)
     */
    public void initBriefContacts(){
        mainModel.initBriefContacts(new OnBriefContactsInitListener() {
            @Override
            public void onLoadSuccess(List<Contact> contactList) {
                List<Contact> contacts = mainView.getContactAdapter().getList();
                if (contacts==null) contacts = new ArrayList<Contact>();
                contacts.addAll(contactList);
                mainView.getContactAdapter().notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String msg) {
                T.showShort(context,msg);
            }
        });
    }


//    /*
//    @param num:通话记录数量,offset:偏移量
//    @return void
//    @brief 带有偏移量offset,获取num个通话记录来更新列表(适配器)
//     */
//    public void addRecords(int offset,int num){
//
//    }

}
