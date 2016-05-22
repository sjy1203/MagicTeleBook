package com.daydayup.magictelebook.main.view;

import android.content.ContentResolver;
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
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydayup.magictelebook.BaseAcitivity;
import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.util.BottomDialog;
import com.daydayup.magictelebook.util.CustomDialog;
import com.daydayup.magictelebook.util.L;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoActivity extends BaseAcitivity {

    private ImageView back_btn;
    private ImageView backdrop;
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
        backdrop = (ImageView) findViewById(R.id.backdrop);
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
        setBackGround();
        InitContactInfo();
    }

    private void setBackGround() {
        Time t=new Time();
        t.setToNow();
        int hour = t.hour;
        if (hour>=7 && hour<19){
            backdrop.setImageResource(R.mipmap.testbg);
        }else
            backdrop.setImageResource(R.mipmap.night);
    }

    @Override
    public int getLayoutId() {
        return R.layout.contact_info;
    }

    private void InitContactInfo() {
        Intent intent = getIntent();
        final BriefContact contactdata = (BriefContact) intent.getSerializableExtra("contactdata");
        layout.setTitle(contactdata.getName());
        personImg.setImageResource(R.mipmap.touxiang);
        personArea.setText(contactdata.getArea());
        contactArea.setText(contactdata.getArea());
        personWeather.setText(contactdata.getWeather());
        personTemp.setText(contactdata.getTemperature());
        personTel.setText(contactdata.getNumber());
        if(contactdata.isBlack()){
            IsBlack.setText("解除黑名单");
            IsBlack.setTextColor(getResources().getColor(R.color.lightblue));
        }else{
            IsBlack.setText("加入黑名单");
            IsBlack.setTextColor(getResources().getColor(R.color.red));
        }

        IsBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsBlack.getText().equals("加入黑名单")){
                    CustomDialog.Builder builder = new CustomDialog.Builder(ContactInfoActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("确定加入黑名单吗？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contactdata.setBlack(true);
                            IsBlack.setText("解除黑名单");
                            IsBlack.setTextColor(getResources().getColor(R.color.lightblue));
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }else if(IsBlack.getText().equals("解除黑名单")){
                    CustomDialog.Builder builder = new CustomDialog.Builder(ContactInfoActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("确定解除黑名单吗？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contactdata.setBlack(false);
                            IsBlack.setText("加入黑名单");
                            IsBlack.setTextColor(getResources().getColor(R.color.red));
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

            }
        });
        personImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(ContactInfoActivity.this,contactdata.getPersonImgId());
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

    /*
     *拍照或从相册选取并裁剪
     */
    private void showEditDialog(Context context, final int PersonImgId){
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

            personImg.setImageDrawable(drawable);
        }
    }


}
