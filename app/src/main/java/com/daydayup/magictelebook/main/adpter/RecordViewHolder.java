package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.callback.IRecordViewHolderClicks;

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
    ImageButton CallBtn;
    CardView cardView;
    IRecordViewHolderClicks mListener;


    public RecordViewHolder(View itemView,IRecordViewHolderClicks listener) {
        super(itemView);
        NameView = (TextView) itemView.findViewById(R.id.person_name);
        PersonImgView = (CircleImageView) itemView.findViewById(R.id.person_img);
        //TelnoView = (TextView) itemView.findViewById(R.id.person_telno);
        TypeView = (TextView) itemView.findViewById(R.id.contact_type);
        AreaView = (TextView) itemView.findViewById(R.id.contact_area);
        TimeView = (TextView) itemView.findViewById(R.id.contact_time);
        cardView = (CardView) itemView.findViewById(R.id.record_card_view);
        CallBtn = (ImageButton) itemView.findViewById(R.id.call_btn);
        mListener = listener;
        cardView.setOnClickListener(this);
        CallBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_card_view:
                mListener.onItemClick();
                break;
            case R.id.call_btn:
                mListener.onCallBtnClick();
                break;
            default:break;

        }

    }
}
