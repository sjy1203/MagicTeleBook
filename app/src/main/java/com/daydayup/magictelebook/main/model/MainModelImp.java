package com.daydayup.magictelebook.main.model;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.design.widget.Snackbar;
import android.telecom.Call;
import android.text.TextUtils;

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
    public void initBriefContacts(OnBriefContactsInitListener onBriefContactsInitListener) {

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
            }
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
    }
}
