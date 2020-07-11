package com.example.medicplus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.medicplus.R;


@SuppressWarnings("ALL")
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);

                if(prefs.getBoolean("lock_application", false)) {
                    Intent in = new Intent(SplashActivity.this, Lockscreen.class);
                    startActivity(in);
                } else {
                    Intent i=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
