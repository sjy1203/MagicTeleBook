package com.daydayup.magictelebook.main.callback;

import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.bean.Record;

import java.util.List;

/**
 * Created by Jay on 16/5/10.
 */
public interface OnRecordsInitListener {
    void onLoadSuccess(List<Record> recordList);
    void onLoadFailed(String msg);
}
