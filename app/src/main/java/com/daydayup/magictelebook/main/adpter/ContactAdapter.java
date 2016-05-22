package com.daydayup.magictelebook.main.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.callback.IContactViewHolderClicks;
import com.daydayup.magictelebook.main.view.ContactInfoActivity;
import com.daydayup.magictelebook.util.L;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay on 16/5/10.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<BriefContact> contacts;
    private int setWidth;
    private Context mContext;

    public ContactAdapter(List<BriefContact> contactList,int setWidth,Context context){
        this.contacts = contactList;
        this.setWidth = setWidth;
        this.mContext = context;
    }
    public List<BriefContact> getList(){
        return contacts;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view, new IContactViewHolderClicks() {
            @Override
            public void onItemClick() {
                BriefContact contactdata = contacts.get(viewType);
                Intent intent = new Intent(mContext, ContactInfoActivity.class);
                intent.putExtra("contactdata",contactdata);
                mContext.startActivity(intent);
                L.d(contacts.get(viewType).getName()+" cardview is clicked");
            }
        });
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ViewGroup.LayoutParams  lp = holder.PersonImgView.getLayoutParams();
        lp.width = 1*setWidth/3-32;
        lp.height = 1*setWidth/3-32;
        holder.PersonImgView.setLayoutParams(lp);
        holder.PersonImgView.setImageResource(R.mipmap.touxiang);
        holder.NameView.setText(contacts.get(position).getName());

        String area = contacts.get(position).getArea();
        if (area==null){
            area = getArea();
            contacts.get(position).setArea(area);
        }

        String weather = contacts.get(position).getWeather();
        if (weather==null){
            weather = getWeather();
            contacts.get(position).setWeather(weather);
        }

        String temperature = contacts.get(position).getTemperature();
        if (temperature==null){
            temperature = getTemperature();
            contacts.get(position).setTemperature(temperature);
        }
        holder.InfoView.setText(String.format("%s %s %s",area,weather,temperature));
    }

    private String getTemperature() {
        return "25º";
    }

    private String getWeather() {
        return "阳光普照";
    }

    private String getArea() {
        return "湖北武汉";
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
