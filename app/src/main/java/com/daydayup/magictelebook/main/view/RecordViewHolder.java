package com.daydayup.magictelebook.main.view;

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
    TextView TelnoView;
    ImageView TypeImgView;
    TextView TypeView;
    ImageView AreaImgView;
    TextView AreaView;
    ImageView TimeImgView;
    TextView TimeView;

    public RecordViewHolder(View itemView) {
        super(itemView);
        NameView = (TextView) itemView.findViewById(R.id.person_name);
        TelnoView = (TextView) itemView.findViewById(R.id.person_telno);
        TypeImgView = (ImageView) itemView.findViewById(R.id.contact_type_img);
        TypeView = (TextView) itemView.findViewById(R.id.contact_type);
        AreaImgView = (ImageView) itemView.findViewById(R.id.contact_area_img);
        AreaView = (TextView) itemView.findViewById(R.id.contact_area);
        TimeImgView = (ImageView) itemView.findViewById(R.id.contact_time_img);
        TimeView = (TextView) itemView.findViewById(R.id.contact_time);
    }
}
