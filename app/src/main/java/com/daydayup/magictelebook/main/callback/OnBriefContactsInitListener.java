package com.daydayup.magictelebook.main.callback;

import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Contact;

import java.util.List;

/**
 * Created by Jay on 16/5/10.
 */
public interface OnBriefContactsInitListener {
    void onLoadSuccess(List<BriefContact> contactList);
    void onLoadFailed(String msg);
}
