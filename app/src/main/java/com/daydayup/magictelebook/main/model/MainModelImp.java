package com.daydayup.magictelebook.main.model;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.telecom.Call;
import android.text.TextUtils;

import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16/5/9.
 */
public class MainModelImp implements IMainModel{
    private Context context = null;
    private AsyncQueryHandler asyncQueryHandler;

    public MainModelImp(Context context){
        this.context = context;
        asyncQueryHandler = new MyAsycQueryHandler(context.getContentResolver());
    }


    @Override
    public void initRecords(int num, OnRecordsInitListener onRecordsInitListener) {
        asyncQueryHandler.startQuery(Token.TOKEN_initRecords,onRecordsInitListener,CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DATE + " desc"+" limit "+num);
    }

    @Override
    public void initContacts(int num, OnBriefContactsInitListener onBriefContactsInitListener) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
        };
        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(Token.TOKEN_initContacts, onBriefContactsInitListener, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");
    }
    //TODO:
    @Override
    public void incrementRecords(int offset, int num, OnRecordsInitListener onRecordsInitListener) {

    }

    @Override
    public void searchRecordsByName(String name, OnRecordsInitListener onRecordsInitListener) {

    }

    @Override
    public void searchRecordsByNumber(String number, OnRecordsInitListener onRecordsInitListener) {

    }

    @Override
    public void searchRecordsByAll(String searchStr, OnRecordsInitListener onRecordsInitListener) {

    }


    private class MyAsycQueryHandler extends AsyncQueryHandler{

        public MyAsycQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            switch (token){
                case Token.TOKEN_initRecords:
                    initRecordsFin((OnRecordsInitListener)cookie,cursor);
                    break;
                case Token.TOKEN_initContacts:
                    initContactsFin((OnBriefContactsInitListener)cookie,cursor);
                    break;
            }
        }

        /**
         * 初始化联系人列表
         * @param cookie
         * @param cursor
         */
        private void initContactsFin(OnBriefContactsInitListener cookie, Cursor cursor) {
            if (cursor==null){
                cookie.onLoadFailed("initContacts cursor null");
                return;
            }
            List<BriefContact> briefContacts = new ArrayList<>();
            while (cursor.moveToNext()){
                Long contactId;
                String lookUpKey;  //此两个是主键
                String name;
                String number;
                Long photoId;
                String sortKey;

                contactId = cursor.getLong(3);
                lookUpKey = cursor.getString(5);
                name = cursor.getString(0);
                number = cursor.getString(1);
                photoId = cursor.getLong(4);
                sortKey = cursor.getString(2);

                BriefContact briefContact = new BriefContact();
                briefContact.setContactId(contactId);
                briefContact.setLookUpKey(lookUpKey);
                briefContact.setName(name);
                briefContact.setNumber(number);
                briefContact.setPhotoId(photoId);
                briefContact.setSortKey(sortKey);

                briefContacts.add(briefContact);
            }
            cursor.close();
            cookie.onLoadSuccess(briefContacts);
        }

        /**
         * 初始化通话记录列表
         * @param cookie
         * @param cursor
         */
        private void initRecordsFin(OnRecordsInitListener cookie, Cursor cursor) {
            if (cursor==null) {
                cookie.onLoadFailed("initRecords cursor null");
                return;
            }
            List<Record> records = new ArrayList<>();
            while(cursor.moveToNext()){

                String _ID; //contact_id
                String Name; //姓名
                long PersonImgId;// 用户缩略图(默认值)
                String Telno;//电话号码
                String Type;//来电,去电,未接等
                String Area;//地区
                String duration;//通话时间
                String date;

                Telno = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                _ID = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_LOOKUP_URI));
                Name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                if (TextUtils.isEmpty(Name)) Name = Telno;
                PersonImgId = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_ID));

                date = TimeUtils.convert_before(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                duration = TimeUtils.convert_between(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION)));
                //TODO:
                Area = "null";
                switch (cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE))){
                    case CallLog.Calls.INCOMING_TYPE:
                        Type = "呼入";break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        Type = "呼出";break;
                    case CallLog.Calls.MISSED_TYPE:
                        Type = "未接";break;
                    default:
                        Type = "挂断";break;
                }
                Record record = new Record(_ID,Name,Telno,Type,Area, duration,date,PersonImgId);
                L.d(record.toString());
                records.add(record);
            }
            cookie.onLoadSuccess(records);
            cursor.close();
        }
    }

    private class Token {
        public static final int TOKEN_initRecords = 0;
        public static final int TOKEN_initContacts = 1;
    }
}
