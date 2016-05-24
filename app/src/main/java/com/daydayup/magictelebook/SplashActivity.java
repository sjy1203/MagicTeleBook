package com.daydayup.magictelebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daydayup.magictelebook.main.view.MainActivity;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.T;
import com.daydayup.magictelebook.util.WeatherParseUtil;
import com.daydayup.magictelebook.weather.WeatherInfo;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseAcitivity {

    private static final int SHOW_TIME_MIN = 4000;
    Timer timer;
    @Override
    protected void initView() {
        //initView

        //startTimer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                goToMainAcitivty();
            }
        },SHOW_TIME_MIN);
    }

    private void goToMainAcitivty() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}
