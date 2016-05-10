package com.daydayup.magictelebook.main.adpter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicks;
import com.daydayup.magictelebook.util.L;

import java.util.List;

/**
 * Created by Jallen on 2016/5/8.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private final static int FIRSTITEM = 0;
    private final static int SECONDITEM = 1;
    private final static int LASTITEM = 999;
    private List<Record> records;

    public RecordAdapter(List<Record> recordList){
        L.d("list size"+recordList.size());
        records = recordList;
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
    public RecordViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
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
                    showPopupWindow(parent,view1,viewType);
                }
            });
        }else if(viewType == SECONDITEM){
            final View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_second,parent,false);
            return new RecordViewHolder(view2, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno()+" is called");
                    showPopupWindow(parent,view2,viewType);
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
                    showPopupWindow(parent,view3,viewType);
                }
            });
        }else{
            final View view4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
            return new RecordViewHolder(view4, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno()+" is called");
                    showPopupWindow(parent,view4,viewType);
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

    public List<Record> getList(){
        return records;
    }

    private void showPopupWindow(ViewGroup parent,View view,int viewType){
        View windowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_more,parent,false);
        ImageButton callbtn = (ImageButton) view.findViewById(R.id.call_btn);
        ImageButton smsgbtn = (ImageButton) view.findViewById(R.id.sendmsg_btn);
        ImageButton deletebtn = (ImageButton) view.findViewById(R.id.delete_btn);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                L.d("clicked");
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        ColorDrawable dw = new ColorDrawable(0xffffff);
        popupWindow.setBackgroundDrawable(dw);

        // 设置好参数之后再show

        if((viewType!=FIRSTITEM) && (viewType!=SECONDITEM) && (viewType!=LASTITEM))
              popupWindow.showAsDropDown(view,424,-10);
        else popupWindow.showAsDropDown(view,434,-10);
    }
}
