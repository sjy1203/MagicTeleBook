package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.view.IRecordViewHolderClicks;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.T;

import java.util.List;

/**
 * Created by Jallen on 2016/5/8.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private final static int FIRSTITEM = 0;
    private final static int SECONDITEM = 1;
    private final static int LASTITEM = 999;
    private List<Record> records;

    public RecordAdapter(List<Record> list){
        L.d("list size"+list.size());
        records = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return FIRSTITEM;
        }else if(position == 1){
            return SECONDITEM;
        }else if(position == records.size()-1){
            return LASTITEM;
        }else return position;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent,final int viewType) {
        if (viewType == FIRSTITEM){
            final View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_first,parent,false);
            return new RecordViewHolder(view1, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno()+" is called");
                }
            });
        }else if(viewType == SECONDITEM){
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_second,parent,false);
            return new RecordViewHolder(view2, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno()+" is called");
                }
            });
        }else if(viewType == LASTITEM){
            final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_last,parent,false);
            return new RecordViewHolder(view3, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                }

                @Override
                public void onCallBtnClick() {
                }
            });
        }else{
            View view4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
            return new RecordViewHolder(view4, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno()+" is called");
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        L.d("onBindView name"+records.get(position).getName());
        holder.NameView.setText(records.get(position).getName());
        holder.PersonImgView.setImageResource(records.get(position).getPersonImgId());
        //holder.TelnoView.setText(records.get(position).getTelno());
        holder.TypeView.setText(records.get(position).getType()+",");
        holder.AreaView.setText(records.get(position).getArea()+",");
        holder.TimeView.setText(records.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
