package com.daydayup.magictelebook.main.callback;

import android.graphics.Bitmap;

import com.daydayup.magictelebook.main.bean.BriefContact;

/**
 * Created by Jay on 16/5/25.
 */
public interface OnSelectContactListener {
    void onSuccess(BriefContact briefContact, Bitmap bitmap);
    void onFailed(String msg);
}
