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
import com.daydayup.magictelebook.main.view.ContactInfoMixActivity;
import com.daydayup.magictelebook.main.view.ContactShowFragment;
import com.daydayup.magictelebook.main.view.MainActivity;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.weather.WeatherInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Created by Jay on 16/5/10.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<BriefContact> contacts;
    private int setWidth;
    private Context mContext;
    private int[] photeId;
    private Random random;

    public ContactAdapter(List<BriefContact> contactList,int setWidth,Context context){
        this.contacts = contactList;
        this.setWidth = setWidth;
        this.mContext = context;
        photeId = new int[]{R.mipmap.touxiang,R.mipmap.touxiang1,R.mipmap.touxiang2,R.mipmap.touxiang3,R.mipmap.touxiang4,R.mipmap.touxiang5,R.mipmap.touxiang6,R.mipmap.touxiang7,R.mipmap.touxiang8,R.mipmap.touxiang9,R.mipmap.touxiang10,R.mipmap.touxiang11};
        random = new Random();

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
                Intent intent = new Intent(mContext, ContactInfoMixActivity.class);
                intent.putExtra(ContactShowFragment.KEY_BRIEFCONTACT,contactdata);
                intent.putExtra(ContactInfoMixActivity.INTENT_STATUS,ContactInfoMixActivity.STATUS_SHOW);
                ((MainActivity)mContext).startActivityForResult(intent,1);
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
        holder.PersonImgView.setImageResource(photeId[position%12]);
        holder.NameView.setText(contacts.get(position).getName());

        String area = contacts.get(position).getArea();
        if (area==null){
            area = getArea();
            contacts.get(position).setArea(area);
        }
        WeatherInfo weatherInfo = contacts.get(position).getWeatherInfo();
        if (weatherInfo==null) return;
        String weather = weatherInfo.getWeatherStatus();
        if ( weather==null){
            weather = getWeather();
            weatherInfo.setWeatherStatus(weather);
        }

        String temperature = weatherInfo.getTemperature();
        if ( temperature==null){
            temperature = getTemperature();
            weatherInfo.setTemperature(temperature);
        }
        contacts.get(position).setWeatherInfo(weatherInfo);
        holder.InfoView.setText(String.format("%s %s %s",area,weather,temperature));
    }

    private String getTemperature() {
        return "25º";
    }

    private String getWeather() {
        return "阳光普照";
    }

    private String getArea() {
        return "武汉";
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
