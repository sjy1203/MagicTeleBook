package com.daydayup.magictelebook.main.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Contact;

import java.util.List;

/**
 * Created by Jay on 16/5/10.
 */
//TODO:@yanhangyu
public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contactList){
        this.contacts = contactList;
    }
    public List<Contact> getList(){
        return contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.PersonImgView.setImageResource(contacts.get(position).getPersonImgId());
        holder.NameView.setText(contacts.get(position).getName());
        holder.InfoView.setText(contacts.get(position).getArea()+" "+contacts.get(position).getWeather());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
