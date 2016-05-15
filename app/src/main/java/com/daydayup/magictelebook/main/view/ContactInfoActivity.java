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

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.Contact;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoActivity extends AppCompatActivity {

    private ImageView back_btn;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private CircleImageView personImg;
    private CollapsingToolbarLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        personImg = (CircleImageView) findViewById(R.id.person_img);
        layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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

    private void InitContactInfo() {
        Intent intent = getIntent();
        Contact contactdata = (Contact) intent.getSerializableExtra("contactdata");
        layout.setTitle(contactdata.getName());
        personImg.setImageResource(contactdata.getPersonImgId());
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
