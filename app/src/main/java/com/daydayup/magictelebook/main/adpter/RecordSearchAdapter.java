package com.daydayup.magictelebook.main.adpter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Record;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jallen on 2016/5/15.
 */
public class RecordSearchAdapter extends BaseAdapter {

    private final static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    private List<Record> mRecords;
    private LayoutInflater mInflater;
    private Context mContext;

    public  RecordSearchAdapter(Context context,List<Record> recordList){
        mRecords = recordList;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.record_search_item,null);
            viewHolder.NameView = (TextView) convertView.findViewById(R.id.person_name);
            viewHolder.PersonImgView = (CircleImageView) convertView.findViewById(R.id.person_img);
            viewHolder.TelnoView = (TextView) convertView.findViewById(R.id.person_telno);
            viewHolder.TypeView = (TextView) convertView.findViewById(R.id.contact_type);
            viewHolder.AreaView = (TextView) convertView.findViewById(R.id.contact_area);
            viewHolder.TimeView = (TextView) convertView.findViewById(R.id.contact_time);
            viewHolder.CardView = (CardView) convertView.findViewById(R.id.record_search_card_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.NameView.setText(mRecords.get(position).getName());
        //TODO:
        viewHolder.PersonImgView.setImageResource(R.mipmap.touxiang);
        viewHolder.TelnoView.setText(mRecords.get(position).getTelno());
        viewHolder.TypeView.setText(mRecords.get(position).getType()+",");
        viewHolder.AreaView.setText(mRecords.get(position).getArea()+",");
        viewHolder.TimeView.setText(mRecords.get(position).getDate());
        viewHolder.CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mRecords.get(position).getTelno()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });
        return  convertView;
    }

    class ViewHolder {
        public TextView NameView;
        public CircleImageView  PersonImgView;
        public TextView TelnoView;
        public TextView TypeView;
        public TextView AreaView;
        public TextView TimeView;
        public CardView CardView;
    }
}
