package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.callback.IContactViewHolderClicks;
import com.daydayup.magictelebook.util.MarqueeText;
import com.hp.hpl.sparta.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jallen on 2016/5/10.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView PersonImgView;
    MarqueeText NameView;
    MarqueeText InfoView;
    IContactViewHolderClicks mListener;
    CardView cardView;
    TextView personTv;

    public ContactViewHolder(View itemView, IContactViewHolderClicks listener) {
        super(itemView);
        PersonImgView = (CircleImageView) itemView.findViewById(R.id.person_img);
        NameView = (MarqueeText) itemView.findViewById(R.id.person_name);
        InfoView = (MarqueeText) itemView.findViewById(R.id.weather);
        mListener = listener;
        cardView = (CardView) itemView.findViewById(R.id.contact_card_view);
        cardView.setOnClickListener(this);
        personTv = (TextView) itemView.findViewById(R.id.person_tv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact_card_view:
                mListener.onItemClick();
        }
    }
}
