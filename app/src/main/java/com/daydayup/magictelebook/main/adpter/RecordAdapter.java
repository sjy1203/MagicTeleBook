package com.daydayup.magictelebook.main.adpter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicks;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicksAddMore;
import com.daydayup.magictelebook.util.BottomDialog;
import com.daydayup.magictelebook.util.L;

import java.util.List;

import at.markushi.ui.CircleButton;

/**
 * Created by Jallen on 2016/5/8.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private final static int FIRSTITEM = 0;
    private final static int SECONDITEM = 1;
    private final static int LASTITEM = 999;
    private final static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private Context mContext;
    private List<Record> records;

    public RecordAdapter(List<Record> recordList, Context context) {
        L.d("list size" + recordList.size());
        records = recordList;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FIRSTITEM;
        } else if (position == 1) {
            return SECONDITEM;
        } else if (position == records.size() - 1) {
            return LASTITEM;
        } else return position;
    }

    public int getPosition(int viewType) {
        if (viewType == FIRSTITEM) {
            return 0;
        } else if (viewType == SECONDITEM) {
            return 1;
        } else if (viewType == LASTITEM) {
            return records.size() - 1;
        } else return viewType;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == FIRSTITEM) {
            final View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_first, parent, false);
            return new RecordViewHolder(view1, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName() + " is clicked");
                    showPopupWindow(parent, view1, viewType);
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno() + " is called");
                    showEditDialog(mContext, viewType);
                }
            });
        } else if (viewType == SECONDITEM) {
            final View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_second, parent, false);
            return new RecordViewHolder(view2, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName() + " is clicked");
                    showPopupWindow(parent, view2, viewType);
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno() + " is called");
                    showEditDialog(mContext, viewType);
                }
            });
        } else if (viewType == LASTITEM) {
            final View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_last, parent, false);
            return new RecordViewHolder(view3, new IRecordViewHolderClicksAddMore() {
                @Override
                public void onItemClick() {
                    L.d(records.get(getPosition(viewType)).getName() + " is clicked");
                    showPopupWindow(parent, view3, viewType);
                }

                @Override
                public void onCallBtnClick() {
                    showEditDialog(mContext, viewType);
                }

                @Override
                public void onAddBtnCLick() {
                    L.d("More records are loaded!");
                }
            });
        } else {
            final View view4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
            return new RecordViewHolder(view4, new IRecordViewHolderClicks() {
                @Override
                public void onItemClick() {
                    L.d(records.get(viewType).getName() + " is clicked");
                    showPopupWindow(parent, view4, viewType);
                }

                @Override
                public void onCallBtnClick() {
                    L.d(records.get(viewType).getTelno() + " is called");
                    showEditDialog(mContext, viewType);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        L.d("onBindView name" + records.get(position).getName());
        holder.NameView.setText(records.get(position).getName());
        //TODO:
        holder.PersonImgView.setImageResource(R.mipmap.touxiang);
        if (records.get(position).getType().equals("未接")) {
            holder.NameView.setTextColor(Color.rgb(255, 0, 0));
        }
        holder.TypeView.setText(records.get(position).getType());
        holder.AreaView.setText(records.get(position).getArea());
        holder.TimeView.setText(records.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public List<Record> getList() {
        return records;
    }

    private void showPopupWindow(final ViewGroup parent, final View view, final int viewType) {
        final View windowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_more, parent, false);
        CircleButton call = (CircleButton) windowView.findViewById(R.id.record_pw_call);
        CircleButton smsg = (CircleButton) windowView.findViewById(R.id.record_pw_sendmsg);
        CircleButton edit = (CircleButton) windowView.findViewById(R.id.record_pw_edit);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d(records.get(getPosition(viewType)).getTelno() + " is called");
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + records.get(getPosition(viewType)).getTelno()));
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
        smsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + records.get(getPosition(viewType)).getTelno());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                sendIntent.putExtra("sms_body", "");
                mContext.startActivity(sendIntent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showEditPopupWindow(parent,windowView,viewType);
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
        int yoff2 = - 3*view.getHeight()/5;
        int yoff3 =  - 8*view.getHeight()/9;

        switch (viewType){
            case FIRSTITEM:
            case SECONDITEM:
                popupWindow.showAsDropDown(view,xoff-10,yoff2);
                break;
            case LASTITEM:
                popupWindow.showAsDropDown(view,xoff-10,yoff3);
                break;
            default:
                popupWindow.showAsDropDown(view,xoff,yoff1);
                break;
        }
    }

    private void showEditDialog(Context context, final int viewType){
        BottomDialog.Builder builder = new BottomDialog.Builder(context);
        builder.setActionTouch("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setItem1Touch("加入黑名单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                L.d(records.get(getPosition(viewType)).getName()+"is added to BlackList");
            }
        });
        builder.setItem2Touch("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                L.d(records.get(getPosition(viewType)).getName()+"is deleted");
            }
        });
        builder.create().show();
    }


}
