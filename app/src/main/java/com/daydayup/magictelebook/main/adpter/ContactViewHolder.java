package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.daydayup.magictelebook.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jallen on 2016/5/10.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder {

    CircleImageView PersonImgView;
    TextView NameView;
    TextView InfoView;

    public ContactViewHolder(View itemView) {
        super(itemView);
        PersonImgView = (CircleImageView) itemView.findViewById(R.id.person_img);
        NameView = (TextView) itemView.findViewById(R.id.person_name);
        InfoView = (TextView) itemView.findViewById(R.id.weather);
    }
}
