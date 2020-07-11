package com.example.medicplus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.likith.lockview.LockView;
import com.likith.lockview.Password;


@SuppressWarnings("ALL")
public class Lockscreen extends AppCompatActivity {

    private LockView LockView;
    private Intent intent;
    private String origPassword = null;
    private int INPUT_PASSWORD = 0;
    private int ENTER_PASSWORD = 1;
    private int ENTER_PASSWORD_AGAIN = 2;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);

        getSupportActionBar().hide();

        LockView = findViewById(R.id.blurlockview);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        intent = getIntent();
        if(intent.getBooleanExtra("SET_PASSCODE", false)) {

            showPasswordRegScreen(ENTER_PASSWORD);

        } else if(intent.getBooleanExtra("INPUT_PASSCODE", false)) {

            showPasswordRegScreen(INPUT_PASSWORD);

        } else {

            String passcode = preferences.getString("passcode", "1234");
            LockView.setTitle("Input Password");
            LockView.setType(Password.NUMBER, true);
            LockView.setOnPasswordInputListener(new LockView.OnPasswordInputListener() {
                @Override
                public void input(String inputPassword) {
                    if(inputPassword.length() == LockView.getPasswordLength()) {
                        if (inputPassword.equals(passcode)) {
                            Intent in = new Intent(Lockscreen.this, MainActivity.class);
                            startActivity(in);
                        } else {
                            LockView.clear();
                            Toast.makeText(Lockscreen.this, "Wrong Password !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }


    }

    private void showPasswordRegScreen(int pwd) {

        if(pwd == 0) {

            String passcode = preferences.getString("passcode", "1234");
            LockView.setTitle("Input Password");
            LockView.setType(Password.NUMBER, true);
            LockView.setOnPasswordInputListener(new LockView.OnPasswordInputListener() {
                @Override
                public void input(String inputPassword) {
                    if(inputPassword.length() == LockView.getPasswordLength()) {
                        if (inputPassword.equals(passcode)) {
                            LockView.clear();
                            showPasswordRegScreen(ENTER_PASSWORD);
                        } else {
                            LockView.clear();
                            Toast.makeText(Lockscreen.this, "Wrong Password !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else if(pwd == 1) {

            LockView.setTitle("Enter Password");
            LockView.setType(Password.NUMBER, true);
            LockView.setOnPasswordInputListener(new LockView.OnPasswordInputListener() {
                @Override
                public void input(String inputPassword) {
                    if (inputPassword.length() == LockView.getPasswordLength()) {
                        origPassword = inputPassword;
                        LockView.clear();
                        showPasswordRegScreen(ENTER_PASSWORD_AGAIN);
                    }
                }
            });

        } else {

            LockView.setTitle("Enter Password Again");
            LockView.setType(Password.NUMBER, true);
            LockView.setOnPasswordInputListener(new LockView.OnPasswordInputListener() {
                @Override
                public void input(String inputPassword) {
                    if (inputPassword.length() == LockView.getPasswordLength()) {
                        if(origPassword.equals(inputPassword)) {

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("passcode", inputPassword);
                            editor.apply();

                            Toast.makeText(Lockscreen.this, "Password Saved !", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Lockscreen.this, "Password not Matched", Toast.LENGTH_SHORT).show();
                        }

                        LockView.clear();
                    }
                }
            });

        }
    }
}