package com.daydayup.magictelebook.main.model;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.telecom.Call;
import android.text.TextUtils;

import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.DefaultListener;
import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;
import com.daydayup.magictelebook.main.callback.OnSelectContactListener;
import com.daydayup.magictelebook.main.dao.SQLiteHelper;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.TimeUtils;

import java.io.ByteArrayOutputStream;
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
                ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID,
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
        asyncQueryHandler.startQuery(Token.TOKEN_initRecords,onRecordsInitListener,CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DATE + " desc"+" limit "+offset+","+num);
    }

    @Override
    public void searchRecordsByName(String name, OnRecordsInitListener onRecordsInitListener) {
        asyncQueryHandler.startQuery(Token.TOKEN_initRecords,onRecordsInitListener,CallLog.Calls.CONTENT_URI,null,CallLog.Calls.CACHED_NAME+" LIKE ?",new String[]{"%"+name+"%"},CallLog.Calls.DATE + " desc");
    }

    @Override
    public void searchRecordsByNumber(String number, OnRecordsInitListener onRecordsInitListener) {
        asyncQueryHandler.startQuery(Token.TOKEN_initRecords,onRecordsInitListener,CallLog.Calls.CONTENT_URI,null,CallLog.Calls.NUMBER+" LIKE ?",new String[]{"%"+number+"%"},CallLog.Calls.DATE + " desc");
    }

    @Override
    public void searchRecordsByAll(String searchStr, OnRecordsInitListener onRecordsInitListener) {
        asyncQueryHandler.startQuery(Token.TOKEN_initRecords,onRecordsInitListener,CallLog.Calls.CONTENT_URI,null,CallLog.Calls.CACHED_NAME+" LIKE ?"+" or "+ CallLog.Calls.NUMBER+" LIKE ?",new String[]{"%"+searchStr+"%","%"+searchStr+"%"},CallLog.Calls.DATE + " desc");
    }

    @Override
    public void insertContactSys(BriefContact briefContact, DefaultListener defaultListener) {
        ContentValues values = new ContentValues();
        Uri rawContactUri = context.getContentResolver().insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 向data表插入数据

            values.clear();
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, briefContact.getName());
            values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, briefContact.getName());

        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);

        // 向data表插入电话号码

            values.clear();
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, briefContact.getNumber());
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);

    }

    @Override
    public void updateContactSys(BriefContact briefContact, DefaultListener defaultListener) {
        //update phone
        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, briefContact.getNumber());
        String where = ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                + ContactsContract.Data.MIMETYPE + "=?";
        String[] selectionArgs = new String[] { String.valueOf(briefContact.getRawContactId()),
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE };
        context.getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionArgs);
        L.e("updateContactSys"+"phone:"+briefContact.getNumber());
        //update name
        values.clear();
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, briefContact.getName());
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, briefContact.getName());

        String where1 = ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                + ContactsContract.Data.MIMETYPE + "=?";
        String[] selectionArgs1 = new String[] { String.valueOf(briefContact.getRawContactId()),
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
        context.getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where1, selectionArgs1);

    }

    @Override
    public void deleteContactSys(Long rawContactId, DefaultListener defaultListener) {
        context.getContentResolver().delete(
                ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI,
                        rawContactId), null, null);
    }

    @Override
    public void deleteRecordFromSys(String id, DefaultListener defaultListener) {
        asyncQueryHandler.startDelete(Token.TOKEN_deleteRecordFromSys,defaultListener,CallLog.Calls.CONTENT_URI, CallLog.Calls._ID+"= ?",new String[]{id});
    }

    @Override
    public void replaceContactFromSqlite(BriefContact briefContact, Bitmap bitmap,DefaultListener defaultListener) {
        SQLiteDatabase sqLiteDatabase = SQLiteHelper.getWriteDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NUMBER,briefContact.getNumber());
        contentValues.put(SQLiteHelper.NAME,briefContact.getName());
        contentValues.put(SQLiteHelper.AREA,briefContact.getArea());
        contentValues.put(SQLiteHelper.BIRTH,briefContact.getBirth());
        if (briefContact.isBlack())
        contentValues.put(SQLiteHelper.BLACK,1);
        else contentValues.put(SQLiteHelper.BLACK,0);
        if (bitmap!=null)
        contentValues.put(SQLiteHelper.LOGO,img(bitmap));
        sqLiteDatabase.replace(SQLiteHelper.TABLE_CONTACT,null,contentValues);
        defaultListener.onSuccess("replace contact from sqlite success");
        sqLiteDatabase.close();
    }

    @Override
    public void deleteContactFromSqlite(BriefContact briefContact, DefaultListener defaultListener) {
        SQLiteDatabase sqLiteDatabase = SQLiteHelper.getWriteDb(context);
        sqLiteDatabase.delete(SQLiteHelper.TABLE_CONTACT,SQLiteHelper.NUMBER + "= ?",new String[]{briefContact.getNumber()});
        sqLiteDatabase.close();
        defaultListener.onSuccess("delete Contact from sqlite success");
    }

    @Override
    public void selectContactFromSqlite(String number, OnSelectContactListener onSelectContactListener) {
        SQLiteDatabase sqLiteDatabase = SQLiteHelper.getReadDb(context);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+SQLiteHelper.TABLE_CONTACT+" where "+SQLiteHelper.NUMBER+" = ?",new String[]{number});
        BriefContact briefContact = new BriefContact();
        if (cursor==null || !cursor.moveToFirst()){
            onSelectContactListener.onFailed("没有查询到"+number+"对应联系人");
            return;
        }
        briefContact.setNumber(cursor.getString(0));
        briefContact.setName(cursor.getString(1));
        briefContact.setArea(cursor.getString(2));
        briefContact.setBirth(cursor.getString(3));
        int isBlack = cursor.getInt(5);
        if (isBlack==1)
        briefContact.setBlack(true);
        else briefContact.setBlack(false);
        byte[] img = cursor.getBlob(4);
        if (img!=null)
        onSelectContactListener.onSuccess(briefContact, BitmapFactory.decodeByteArray(img,0,img.length));
        else onSelectContactListener.onSuccess(briefContact, null);
        cursor.close();
        sqLiteDatabase.close();
    }

    @Override
    public long getRawContactIDByNumber(String data_id) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = contentResolver.query(uri,new String[]{ContactsContract.RawContacts.Data.RAW_CONTACT_ID}, ContactsContract.RawContacts.Data._ID+" =?",new String[]{data_id},null);
        if (cursor==null || !cursor.moveToFirst()){
            return 0;
        }
        cursor.moveToFirst();
        long id = cursor.getLong(0);
        cursor.close();
        return id;
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

        @Override
        protected void onDeleteComplete(int token, Object cookie, int result) {
            switch (token){
                case Token.TOKEN_deleteRecordFromSys:
                    ((DefaultListener)cookie).onSuccess(String.valueOf(result));
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
                Long rawcontactId;
                String lookUpKey;  //此两个是主键
                String name;
                String number;
                Long photoId;
                String sortKey;

                rawcontactId = cursor.getLong(3);
                lookUpKey = cursor.getString(5);
                name = cursor.getString(0);
                number = cursor.getString(1);
                photoId = cursor.getLong(4);
                sortKey = cursor.getString(2);

                BriefContact briefContact = new BriefContact();
                briefContact.setRawContactId(rawcontactId);
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
                _ID = String.valueOf(cursor.getLong(cursor.getColumnIndex(CallLog.Calls._ID)));
                Name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                if (TextUtils.isEmpty(Name)) Name = Telno;
                PersonImgId = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_ID));

                date = TimeUtils.convert_before(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                duration = TimeUtils.convert_between(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION)));
                //TODO:
                Area = "未知";
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

        public static final int TOKEN_deleteRecordFromSys = 2;
    }

    private byte[] img(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
