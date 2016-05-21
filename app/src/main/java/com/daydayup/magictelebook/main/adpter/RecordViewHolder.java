package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicks;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicksAddMore;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jallen on 2016/5/8.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    TextView NameView;
    CircleImageView  PersonImgView;
    //TextView TelnoView;
    TextView TypeView;
    TextView AreaView;
    TextView TimeView;
    ImageButton MoreBtn;
    Button AddBtn;
    CardView cardView;
    IRecordViewHolderClicks mListener;
    IRecordViewHolderClicksAddMore mListener1;


    public RecordViewHolder(View itemView,IRecordViewHolderClicks listener) {
        super(itemView);
        NameView = (TextView) itemView.findViewById(R.id.person_name);
        PersonImgView = (CircleImageView) itemView.findViewById(R.id.person_img);
        //TelnoView = (TextView) itemView.findViewById(R.id.person_telno);
        TypeView = (TextView) itemView.findViewById(R.id.contact_type);
        AreaView = (TextView) itemView.findViewById(R.id.contact_area);
        TimeView = (TextView) itemView.findViewById(R.id.contact_time);
        cardView = (CardView) itemView.findViewById(R.id.record_card_view);
        MoreBtn = (ImageButton) itemView.findViewById(R.id.more_btn);
        mListener = listener;
        cardView.setOnClickListener(this);
        MoreBtn.setOnClickListener(this);
    }

    public RecordViewHolder(View itemView,IRecordViewHolderClicksAddMore listener) {
        super(itemView);
        NameView = (TextView) itemView.findViewById(R.id.person_name);
        PersonImgView = (CircleImageView) itemView.findViewById(R.id.person_img);
        //TelnoView = (TextView) itemView.findViewById(R.id.person_telno);
        TypeView = (TextView) itemView.findViewById(R.id.contact_type);
        AreaView = (TextView) itemView.findViewById(R.id.contact_area);
        TimeView = (TextView) itemView.findViewById(R.id.contact_time);
        cardView = (CardView) itemView.findViewById(R.id.record_card_view_last);
        MoreBtn = (ImageButton) itemView.findViewById(R.id.more_btn_last);
        AddBtn = (Button) itemView.findViewById(R.id.load_records);
        mListener1 = listener;
        cardView.setOnClickListener(this);
        MoreBtn.setOnClickListener(this);
        AddBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_card_view:
                mListener.onItemClick();
                break;
            case R.id.more_btn:
                mListener.onCallBtnClick();
                break;
            case R.id.record_card_view_last:
                mListener1.onItemClick();
                break;
            case R.id.more_btn_last:
                mListener1.onCallBtnClick();
                break;
            case R.id.load_records:
                mListener1.onAddBtnCLick();
            default:break;
        }

    }
}
