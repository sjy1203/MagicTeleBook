package com.daydayup.magictelebook.main.adpter;

import com.daydayup.magictelebook.main.bean.Contact;

import java.util.List;

/**
 * Created by Jay on 16/5/10.
 */
//TODO:@yanhangyu
public class ContactAdapter {
    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList){
        this.contactList = contactList;
    }
    public List<Contact> getList(){
        return contactList;
    }
}
