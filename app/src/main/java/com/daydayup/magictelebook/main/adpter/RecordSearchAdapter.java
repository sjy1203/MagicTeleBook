package com.daydayup.magictelebook.main.adpter;

import android.content.Context;
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
    private List<Record> mRecords;
    private LayoutInflater mInflater;
    private int[] photeId;

    public  RecordSearchAdapter(Context context,List<Record> recordList){
        mRecords = recordList;
        mInflater = LayoutInflater.from(context);
        photeId = new int[]{R.mipmap.touxiang,R.mipmap.touxiang1,R.mipmap.touxiang2,R.mipmap.touxiang3,R.mipmap.touxiang4,R.mipmap.touxiang5,R.mipmap.touxiang6,R.mipmap.touxiang7,R.mipmap.touxiang8,R.mipmap.touxiang9,R.mipmap.touxiang10,R.mipmap.touxiang11};

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            viewHolder.personTv = (TextView) convertView.findViewById(R.id.person_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.NameView.setText(mRecords.get(position).getName());
        //TODO:
        viewHolder.PersonImgView.setImageResource(photeId[position%12]);
        viewHolder.TelnoView.setText(mRecords.get(position).getTelno());
        viewHolder.TypeView.setText(mRecords.get(position).getType());
        viewHolder.AreaView.setText(mRecords.get(position).getArea());
        viewHolder.TimeView.setText(mRecords.get(position).getDate());
        return  convertView;
    }

    class ViewHolder {
        public TextView NameView;
        public CircleImageView  PersonImgView;
        public TextView TelnoView;
        public TextView TypeView;
        public TextView AreaView;
        public TextView TimeView;
        public TextView personTv;
    }
}
