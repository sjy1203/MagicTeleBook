package com.daydayup.magictelebook.main.adpter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicks;
import com.daydayup.magictelebook.util.L;

import java.util.List;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;

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

    public int getPosition(int viewType){
        if(viewType == FIRSTITEM){
            return 0;
        }else if(viewType == SECONDITEM){
            return 2;
        } else if(viewType == LASTITEM){
            return records.size()-1;
        }else return viewType;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == FIRSTITEM){
            final View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_first,parent,false);
            return new RecordViewHolder(view1, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                    showPopupWindow(parent,view1,viewType);
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno()+" is called");
                }
            });
        }else if(viewType == SECONDITEM){
            final View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_second,parent,false);
            return new RecordViewHolder(view2, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                    showPopupWindow(parent,view2,viewType);
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
                    L.d(records.get(getPosition(viewType)).getName()+" is clicked");
                    showPopupWindow(parent,view3,viewType);
                }

                @Override
                public void onCallBtnClick() {
                }
            });
        }else{
            final View view4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
            return new RecordViewHolder(view4, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName()+" is clicked");
                    showPopupWindow(parent,view4,viewType);
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

    public List<Record> getList(){
        return records;
    }

    private void showPopupWindow(ViewGroup parent, View view, final int viewType){
        View windowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_more,parent,false);
        CircleButton call = (CircleButton) windowView.findViewById(R.id.record_pw_call);
        CircleButton smsg = (CircleButton) windowView.findViewById(R.id.record_pw_sendmsg);
        CircleButton delete = (CircleButton) windowView.findViewById(R.id.record_pw_edit);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d(records.get(getPosition(viewType)).getTelno()+" is called");
            }
        });
        windowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        ColorDrawable dw = new ColorDrawable(0xffffff);
        popupWindow.setBackgroundDrawable(dw);

        // 设置好参数之后再show
        int xoff = view.getWidth() - popupWindow.getContentView().getMeasuredWidth() - 10;
        int yoff1 = - 4*view.getHeight()/5;
        int yoff2 = - view.getHeight()/3;
        int yoff3 =  - 8*view.getHeight()/9;
        L.d(view.getHeight()+""+yoff2);

        if ((viewType == FIRSTITEM) && (viewType == SECONDITEM)){
            popupWindow.showAsDropDown(view,xoff-10,yoff2);
        } else if(viewType == LASTITEM){
            popupWindow.showAsDropDown(view,xoff-10,yoff3);
        }else popupWindow.showAsDropDown(view,xoff,yoff1);
    }
}
