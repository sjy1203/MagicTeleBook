package com.daydayup.magictelebook.main.view;

import com.daydayup.magictelebook.main.adpter.ContactAdapter;
import com.daydayup.magictelebook.main.adpter.RecordAdapter;

/**
 * Created by Jay on 16/5/9.
 */
public interface IMainView {
    RecordAdapter getRecordAdapter();
    ContactAdapter getContactAdapter();
}
