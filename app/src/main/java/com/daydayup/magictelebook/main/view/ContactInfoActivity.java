package com.daydayup.magictelebook.main.view;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydayup.magictelebook.BaseAcitivity;
import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Contact;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoActivity extends BaseAcitivity {

    private ImageView back_btn;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private CircleImageView personImg;
    private CollapsingToolbarLayout layout;
    private TextView personArea;
    private TextView contactArea;
    private TextView personWeather;
    private TextView personTemp;
    private TextView personTel;
    private TextView IsBlack;


    @Override
    protected void initView() {
        setContentView(getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        personImg = (CircleImageView) findViewById(R.id.person_img);
        layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        personArea = (TextView) findViewById(R.id.person_area);
        contactArea = (TextView) findViewById(R.id.contact_area);
        personWeather = (TextView) findViewById(R.id.contact_weather);
        personTemp = (TextView) findViewById(R.id.contact_temp);
        personTel = (TextView) findViewById(R.id.person_telno);
        IsBlack = (TextView) findViewById(R.id.addtoblack);
        fab = (FloatingActionButton) findViewById(R.id.edit_info);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InitContactInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.contact_info;
    }

    private void InitContactInfo() {
        Intent intent = getIntent();
        final Contact contactdata = (Contact) intent.getSerializableExtra("contactdata");
        layout.setTitle(contactdata.getName());
        personImg.setImageResource(contactdata.getPersonImgId());
        personArea.setText(contactdata.getArea());
        contactArea.setText(contactdata.getArea());
        personWeather.setText(contactdata.getWeather());
        personTemp.setText(contactdata.getTemperature());
        personTel.setText(contactdata.getTelno());
        if(contactdata.getBlack()){
            IsBlack.setText("解除黑名单");
        }else IsBlack.setText("加入黑名单");
        IsBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsBlack.getText().equals("加入黑名单")){
                    contactdata.setBlack(true);
                    IsBlack.setText("解除黑名单");
                    IsBlack.setTextColor(getResources().getColor(R.color.lightblue));
                }else if(IsBlack.getText().equals("解除黑名单")){
                    contactdata.setBlack(false);
                    IsBlack.setText("加入黑名单");
                    IsBlack.setTextColor(getResources().getColor(R.color.red));
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
