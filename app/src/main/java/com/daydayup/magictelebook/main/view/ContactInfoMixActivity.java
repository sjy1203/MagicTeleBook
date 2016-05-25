package com.daydayup.magictelebook.main.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydayup.magictelebook.BaseAcitivity;
import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.ContactAdapter;
import com.daydayup.magictelebook.main.adpter.RecordAdapter;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.callback.OnSelectContactListener;
import com.daydayup.magictelebook.main.presenter.MainPresenter;
import com.daydayup.magictelebook.util.BottomDialog;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.T;
import com.daydayup.magictelebook.util.WeatherParseUtil;
import com.daydayup.magictelebook.weather.WeatherInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoMixActivity extends BaseAcitivity implements IMainView{
    //view in common
    private ImageView back_btn;
    private ImageView backdrop;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private CircleImageView personImg;
    private CollapsingToolbarLayout layout;
    private TextView personWeather;
    private TextView personTemp;
    private TextView contactArea;

    //const
    public static final String INTENT_STATUS = "fragment_status";
    public static final int STATUS_EDIT = 1;
    public static final int STATUS_SHOW = 2;
    public static final int STATUS_RECORD = 3;


    //fragment
    private ContactShowFragment contactShowFragment = null;
    private ContactEditFragment contactEditFragment = null;
    private ContactRecordFragment contactRecordFragment = null;

    //flag
    private boolean inEdit = false;

    //img
    Bitmap imgStore = null;

    @Override
    protected void initView() {
        //same in common
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        personImg = (CircleImageView) findViewById(R.id.person_img);
        layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        contactArea = (TextView) findViewById(R.id.contact_area);
        personWeather = (TextView) findViewById(R.id.contact_weather);
        personTemp = (TextView) findViewById(R.id.contact_temp);
        fab = (FloatingActionButton) findViewById(R.id.edit_info);
        setSupportActionBar(toolbar);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFragment();

    }


    private void initFragment() {
        final Intent intent = getIntent();
        final int status = intent.getIntExtra(INTENT_STATUS,-1);
        if (status==-1) return;

        if (intent.getSerializableExtra(ContactShowFragment.KEY_BRIEFCONTACT)!=null){
            final BriefContact briefContact1 = (BriefContact) intent.getSerializableExtra(ContactShowFragment.KEY_BRIEFCONTACT);
            MainPresenter.getInstance(this,this).selectContactFromSqlite(briefContact1.getNumber(), new OnSelectContactListener() {
                @Override
                public void onSuccess(BriefContact briefContact, Bitmap bitmap) {
                    if (bitmap!=null) personImg.setImageBitmap(bitmap);
                    briefContact1.setBlack(briefContact.isBlack());
                    briefContact1.setArea(briefContact.getArea());
                    briefContact1.setBirth(briefContact.getBirth());

                    switch (status){
                        case STATUS_SHOW:
                            showFragment(briefContact1);
                            break;
                        case STATUS_EDIT:
//                            if (intent.getSerializableExtra(ContactEditFragment.KEY_BRIEFCONTACT)==null){
//                                editFragment(null);
//                            }else{
                                editFragment(briefContact1);
//                            }
                            break;
                        case STATUS_RECORD:
                            recordFragment(briefContact1);
                            break;
                    }
                    //天气获取
                    if (!TextUtils.isEmpty(briefContact1.getArea()))
                        WeatherParseUtil.parseWeather(ContactInfoMixActivity.this, briefContact1.getArea(), new WeatherParseUtil.OnWeatherInfoListener() {
                            @Override
                            public void onSuccess(WeatherInfo weatherInfo) {
                                briefContact1.setWeatherInfo(weatherInfo);
                                personTemp.setText(weatherInfo.getTemperature()+"º");
                                personWeather.setText(weatherInfo.getWeatherStatus());
                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        });
                }

                @Override
                public void onFailed(String msg) {
                    switch (status){
                        case STATUS_SHOW:
                            showFragment(briefContact1);
                            break;
                        case STATUS_EDIT:
//                            if (intent.getSerializableExtra(ContactEditFragment.KEY_BRIEFCONTACT)==null){
//                                editFragment(null);
//                            }else{
                            editFragment(briefContact1);
//                            }
                            break;
                        case STATUS_RECORD:
                            recordFragment(briefContact1);
                            break;
                    }
                }
            });
        }else{
            editFragment(null);
        }



    }

    private void recordFragment(final Serializable serializable) {
        //commonviews
        initCommonView((BriefContact) serializable);
        fab.setImageResource(R.drawable.edit1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFragment(serializable);
            }
        });
        //fragment
        contactRecordFragment = new ContactRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContactShowFragment.KEY_BRIEFCONTACT,serializable);
        contactRecordFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fl,contactRecordFragment).commit();

        //null
        contactEditFragment = null;
        contactShowFragment = null;
    }

    private void editFragment(Serializable serializable) {
        inEdit = true;
        contactEditFragment = new ContactEditFragment();
        fab.setImageResource(R.drawable.ok);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(contactEditFragment.commit(imgStore));
            }
        });
        if (serializable==null) serializable = new BriefContact();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ContactEditFragment.KEY_BRIEFCONTACT,serializable);
            contactEditFragment.setArguments(bundle);

            initCommonView((BriefContact) serializable);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_fl,contactEditFragment).commit();

        //null
        contactRecordFragment = null;
        contactShowFragment = null;
    }

    private void showFragment(final Serializable serializable) {
        //commonviews
        initCommonView((BriefContact) serializable);
        fab.setImageResource(R.drawable.edit1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFragment(serializable);
            }
        });
        //fragment
        contactShowFragment = new ContactShowFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContactShowFragment.KEY_BRIEFCONTACT,serializable);
        contactShowFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fl,contactShowFragment).commit();

        //null
        contactEditFragment = null;
        contactRecordFragment = null;
    }
    private void initCommonView(final BriefContact briefContact) {

        if (briefContact.getName()==null){
            layout.setTitle(" ");
            L.d("name null");
        }else{
            layout.setTitle(briefContact.getName());
        }
        contactArea.setText(briefContact.getArea());
        personImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(ContactInfoMixActivity.this);
            }
        });

//        if (briefContact.getWeatherInfo()!=null){
//            personWeather.setText(briefContact.getWeatherInfo().getWeatherStatus());
//            personTemp.setText(briefContact.getWeatherInfo().getTemperature());
//        }else{
//            personWeather.setText(" ");
//            personTemp.setText(" ");
//        }


        setBackGround();
    }

    private void setBackGround() {
        Time t=new Time();
        t.setToNow();
        int hour = t.hour;
        if (hour>=7 && hour<19){
            backdrop.setImageResource(R.drawable.day);
        }else
            backdrop.setImageResource(R.drawable.night1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_info_mix;
    }

    private void showEditDialog(Context context){
        BottomDialog.Builder builder = new BottomDialog.Builder(context);
        builder.setActionTouch("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setItem1Touch("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(),
                                "test.jpg")));
                startActivityForResult(intent, 2);
            }
        });
        builder.setItem2Touch("从相册选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, 1);
            }
        });
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                startPhotoZoom(data.getData());
                break;
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/test.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case 3:
                if(data != null){
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] b = stream.toByteArray();
            imgStore = photo;
            personImg.setImageDrawable(drawable);
        }
    }
    //没用
    @Override
    public RecordAdapter getRecordAdapter() {
        return null;
    }

    @Override
    public ContactAdapter getContactAdapter() {
        return null;
    }


}
