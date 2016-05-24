package com.daydayup.magictelebook.main.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.weather.WeatherInfo;
import com.hp.hpl.sparta.Text;

/**
 * Created by Jay on 16/5/24.
 */
public class ContactShowFragment extends Fragment implements View.OnClickListener{
    //view
    private TextView person_telno,person_area,person_birth;
    private ImageView phone,smg;

    //bean
    private BriefContact briefContact;


    //const
    public static final String KEY_BRIEFCONTACT = "briefContact";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        briefContact = (BriefContact) getArguments().getSerializable(KEY_BRIEFCONTACT);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactshow,container,false);

        //textview
        person_area = (TextView) view.findViewById(R.id.person_area);
        person_telno = (TextView) view.findViewById(R.id.person_telno);
        person_birth = (TextView) view.findViewById(R.id.person_birth);

        //imageview
        phone = (ImageView) view.findViewById(R.id.phone);
        smg = (ImageView) view.findViewById(R.id.smg);
        phone.setOnClickListener(this);
        smg.setOnClickListener(this);

        //initial text
        person_area.setText(briefContact.getArea());
        person_telno.setText(briefContact.getNumber());
        person_birth.setText(briefContact.getBirth());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.smg:
                Uri uri = Uri.parse("smsto:" + briefContact.getNumber());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                if (briefContact.getWeatherInfo()!=null)
                sendIntent.putExtra("sms_body", briefContact.getWeatherInfo().getGanmao());
                else sendIntent.putExtra("sms_body","");
                startActivity(sendIntent);
                break;
            case R.id.phone:
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + briefContact.getNumber()));
                startActivity(callIntent);
                break;
        }
    }
}
