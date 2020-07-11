package com.example.medicplus.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.medicplus.R;
import java.io.File;


@SuppressWarnings("ALL")
public class PreferenceActivity extends android.preference.PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        private Activity hostActivity;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            hostActivity = getActivity();


            Preference share = (Preference) findPreference("share");
            share.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    String app_share_details = getResources().getString(R.string.app_share_link);
                    if (app_share_details != null) {
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        myIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome movie app.\n" + "*Movielist*\n" + app_share_details);
                        startActivity(Intent.createChooser(myIntent, "Share with"));
                    }

                    return true;
                }
            });


            Preference change_passcode = (Preference) findPreference("change_passcode");
            change_passcode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                @Override
                public boolean onPreferenceClick(Preference preference) {

                    Intent in = new Intent(hostActivity, Lockscreen.class);
                    in.putExtra("INPUT_PASSCODE", true);
                    startActivity(in);
                    return false;
                }
            });


            Preference export_database = (Preference) findPreference("export_database");
            export_database.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                private Intent intent;
                private SQLiteToExcel sqliteToExcel;

                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sqliteToExcel = new SQLiteToExcel(hostActivity, Environment.getExternalStorageDirectory() + File.separator + "Medplus");
                    sqliteToExcel.exportAllTables("Medplus.xls", new SQLiteToExcel.ExportListener() {
                        @Override
                        public void onStart() {

                        }
                        @Override
                        public void onCompleted(String filePath) {

                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });

                    Toast.makeText(hostActivity, "File Exported to " + Environment.getExternalStorageDirectory() + File.separator + "Medplus.xls", Toast.LENGTH_SHORT).show();

                    return false;
                }
            });



            SwitchPreference lock_application = (SwitchPreference) findPreference("lock_application");
            lock_application.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(hostActivity);

                    if(prefs.getBoolean("lock_application", false)) {

                        if(prefs.getString("passcode", "null").equals("null")) {
                            Intent in = new Intent(hostActivity, Lockscreen.class);
                            in.putExtra("SET_PASSCODE", true);
                            startActivity(in);
                        }
                    }
                    return false;
                }
            });

        }



        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            String Fpath = data.getDataString();
            // do somthing...
            Toast.makeText(hostActivity, "Selected file: " + Fpath, Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
