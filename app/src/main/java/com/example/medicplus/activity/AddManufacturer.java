package com.example.medicplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.manufacturer;
import com.example.medicplus.utils.DBUtils;

@SuppressWarnings("ALL")
public class AddManufacturer extends AppCompatActivity {

    EditText manufacturer_address, manufacturer_phone;
    AutoCompleteTextView manufacturer_name;
    ImageView but_back, but_save, pic;
    public DatabaseHandler db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manufacturer);

        getSupportActionBar().hide();

        manufacturer_name = findViewById(R.id.manufacturer_name);
        manufacturer_address = findViewById(R.id.manufacturer_address);
        manufacturer_phone = findViewById(R.id.manufacturer_phone);
        but_back = findViewById(R.id.but_back);
        but_save = findViewById(R.id.but_save);
        pic = findViewById(R.id.pic);




        intent = getIntent();
        if(intent.getStringExtra("MAN_EDIT") != null) {
            DatabaseHandler edit = new DatabaseHandler(AddManufacturer.this);
            manufacturer_name.setText(edit.getManufacturer(intent.getStringExtra("MAN_EDIT")).get(0).getManufacturerName());
            manufacturer_address.setText(edit.getManufacturer(intent.getStringExtra("MAN_EDIT")).get(0).getManufacturerAddress());
            manufacturer_phone.setText(edit.getManufacturer(intent.getStringExtra("MAN_EDIT")).get(0).getManufacturerMobile());
            pic.setImageBitmap(DBUtils.getBitmapFromBytes(edit.getManufacturer(intent.getStringExtra("MAN_EDIT")).get(0).getManufacturerImage()));
        }




        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getStringExtra("MAN_EDIT") != null) {
                    db = new DatabaseHandler(AddManufacturer.this);
                    db.updateManufacturer(new manufacturer(
                            Integer.parseInt(intent.getStringExtra("MAN_EDIT")),
                            manufacturer_name.getText().toString(),
                            manufacturer_phone.getText().toString(),
                            manufacturer_address.getText().toString(),
                            DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())));
                } else {
                    db = new DatabaseHandler(AddManufacturer.this);
                    db.addManufacturer(new manufacturer(manufacturer_name.getText().toString(),
                            manufacturer_phone.getText().toString(),
                            manufacturer_address.getText().toString(),
                            DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())));
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

                AlertDialog.Builder builder = new AlertDialog.Builder(AddManufacturer.this);
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
