package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydayup.magictelebook.R;

/**
 * Created by Jallen on 2016/5/8.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {

    TextView NameView;
    ImageView PersonImgView;
    //TextView TelnoView;
    TextView TypeView;
    TextView AreaView;
    TextView TimeView;

    public RecordViewHolder(View itemView) {
        super(itemView);
        NameView = (TextView) itemView.findViewById(R.id.person_name);
        PersonImgView = (ImageView) itemView.findViewById(R.id.person_img);
        //TelnoView = (TextView) itemView.findViewById(R.id.person_telno);
        TypeView = (TextView) itemView.findViewById(R.id.contact_type);
        AreaView = (TextView) itemView.findViewById(R.id.contact_area);
        TimeView = (TextView) itemView.findViewById(R.id.contact_time);
    }
}
