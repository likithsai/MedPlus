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
import com.example.medicplus.database.customer;
import com.example.medicplus.utils.DBUtils;

@SuppressWarnings("ALL")
public class AddCustomer extends AppCompatActivity {

    EditText customer_name, customer_email, customer_address, customer_phone;
    ImageView but_back, but_save, pic;
    public DatabaseHandler db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        getSupportActionBar().hide();

        customer_name = findViewById(R.id.customer_name);
        customer_email = findViewById(R.id.customer_email);
        customer_address = findViewById(R.id.customer_address);
        customer_phone = findViewById(R.id.customer_phone);
        but_back = findViewById(R.id.but_back);
        but_save = findViewById(R.id.but_save);
        pic = findViewById(R.id.pic);



        intent = getIntent();
        if(intent.getStringExtra("CUS_EDIT") != null) {
            DatabaseHandler edit = new DatabaseHandler(AddCustomer.this);
            customer_name.setText(edit.getCustomer(intent.getStringExtra("CUS_EDIT")).get(0).getCustomerName());
            customer_email.setText(edit.getCustomer(intent.getStringExtra("CUS_EDIT")).get(0).getCustomerEmail());
            customer_address.setText(edit.getCustomer(intent.getStringExtra("CUS_EDIT")).get(0).getCustomerAddress());
            customer_phone.setText(edit.getCustomer(intent.getStringExtra("CUS_EDIT")).get(0).getCustomerPhone());
            pic.setImageBitmap(DBUtils.getBitmapFromBytes(edit.getCustomer(intent.getStringExtra("CUS_EDIT")).get(0).getCustomerImage()));
        }



        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getStringExtra("CUS_EDIT") != null) {
                    db = new DatabaseHandler(AddCustomer.this);
                    db.updateCustomer(new customer(
                            Integer.parseInt(intent.getStringExtra("CUS_EDIT")),
                            customer_name.getText().toString(),
                            customer_email.getText().toString(),
                            customer_address.getText().toString(),
                            customer_phone.getText().toString(),
                            DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap()))
                    );
                } else {
                    db = new DatabaseHandler(AddCustomer.this);
                    db.addCustomer(new customer(
                            customer_name.getText().toString(),
                            customer_email.getText().toString(),
                            customer_address.getText().toString(),
                            customer_phone.getText().toString(),
                            DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap()))
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

                AlertDialog.Builder builder = new AlertDialog.Builder(AddCustomer.this);
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
