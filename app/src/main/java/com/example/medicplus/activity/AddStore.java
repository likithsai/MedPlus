package com.example.medicplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.store;
import com.example.medicplus.utils.DBUtils;

@SuppressWarnings("ALL")
public class AddStore extends AppCompatActivity {

    EditText store_name, store_address, store_city, store_number;
    ImageView but_back, but_save, pic;
    public DatabaseHandler db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        getSupportActionBar().hide();

        store_name = findViewById(R.id.store_name);
        store_address = findViewById(R.id.store_address);
        store_city = findViewById(R.id.store_city);
        store_number = findViewById(R.id.store_phone);
        but_back = findViewById(R.id.but_back);
        but_save = findViewById(R.id.but_save);
        pic = findViewById(R.id.pic);


        intent = getIntent();
        if(intent.getStringExtra("STORE_EDIT") != null) {
            DatabaseHandler edit = new DatabaseHandler(AddStore.this);
            store_name.setText(edit.getStore(intent.getStringExtra("STORE_EDIT")).get(0).getStoreName());
            store_address.setText(edit.getStore(intent.getStringExtra("STORE_EDIT")).get(0).getStoreAddress());
            store_city.setText(edit.getStore(intent.getStringExtra("STORE_EDIT")).get(0).getStoreCity());
            store_number.setText(edit.getStore(intent.getStringExtra("STORE_EDIT")).get(0).getStorePhone());
            pic.setImageBitmap(DBUtils.getBitmapFromBytes(edit.getStore(intent.getStringExtra("STORE_EDIT")).get(0).getStoreImage()));
        }

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getStringExtra("STORE_EDIT") != null) {
                    db = new DatabaseHandler(AddStore.this);
                    db.updateStore(
                        new store(
                            Integer.parseInt(intent.getStringExtra("STORE_EDIT")),
                            store_name.getText().toString(),
                            store_address.getText().toString(),
                            store_city.getText().toString(),
                            store_number.getText().toString(),
                            DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())
                        )
                    );

                } else {
                    db = new DatabaseHandler(AddStore.this);
                    db.addStore(
                        new store(
                            store_name.getText().toString(),
                            store_address.getText().toString(),
                            store_city.getText().toString(),
                            store_number.getText().toString(),
                            DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())
                        )
                    );

                }

                finish();
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Choose From Gallery", "Take Photo"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddStore.this);
                builder.setTitle("Select");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                intent.putExtra("crop", "true");
                                intent.putExtra("scale", true);
                                intent.putExtra("outputX", 256);
                                intent.putExtra("outputY", 256);
                                intent.putExtra("aspectX", 1);
                                intent.putExtra("aspectY", 1);
                                intent.putExtra("return-data", true);
                                startActivityForResult(intent, 1);
                                break;

                            case 1:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, 1);
                                }

                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                pic.setImageBitmap(newProfilePic);
            }
        }
    }

}
