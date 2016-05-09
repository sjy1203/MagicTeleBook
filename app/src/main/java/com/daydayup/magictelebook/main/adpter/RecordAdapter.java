package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.util.L;

import java.util.List;

/**
 * Created by Jallen on 2016/5/8.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private List<Record> records;

    public RecordAdapter(List<Record> list){
        L.d("list size"+list.size());
        records = list;
    }
    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        L.d("onBindView name"+records.get(position).getName());
        holder.NameView.setText(records.get(position).getName());
        holder.PersonImgView.setImageResource(records.get(position).getPersonImgId());
        holder.TelnoView.setText(records.get(position).getTelno());
        holder.TypeImgView.setImageResource(records.get(position).getTypeImgId());
        holder.TypeView.setText(records.get(position).getType());
        holder.AreaImgView.setImageResource(records.get(position).getAreaImgId());
        holder.AreaView.setText(records.get(position).getArea());
        holder.TimeImgView.setImageResource(records.get(position).getTimeImgId());
        holder.TimeView.setText(records.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
