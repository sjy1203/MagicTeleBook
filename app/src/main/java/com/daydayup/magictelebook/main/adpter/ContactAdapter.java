package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.callback.IContactViewHolderClicks;
import com.daydayup.magictelebook.util.L;

import java.util.List;

/**
 * Created by Jay on 16/5/10.
 */
//TODO:@yanhangyu
public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts;
    private int setWidth;

    public ContactAdapter(List<Contact> contactList,int setWidth){
        this.contacts = contactList;
        this.setWidth = setWidth;
    }
    public List<Contact> getList(){
        return contacts;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view, new IContactViewHolderClicks() {
            @Override
            public void onItemClick() {
                L.d(contacts.get(viewType).getName()+" cardview is clicked");
            }
        });
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ViewGroup.LayoutParams  lp = holder.PersonImgView.getLayoutParams();
        lp.width = 3*setWidth/10;
        lp.height = 3*setWidth/10;
        holder.PersonImgView.setLayoutParams(lp);
        holder.PersonImgView.setImageResource(contacts.get(position).getPersonImgId());
        holder.NameView.setText(contacts.get(position).getName());
        holder.InfoView.setText(contacts.get(position).getArea()+" "+contacts.get(position).getWeather());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
