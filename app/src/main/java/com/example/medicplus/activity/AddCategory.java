package com.example.medicplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.medicinesCategory;
import com.example.medicplus.utils.DBUtils;

@SuppressWarnings("ALL")
public class AddCategory extends AppCompatActivity {

    private AutoCompleteTextView category_name;
    ImageView but_back, but_save, pic;
    private DatabaseHandler db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().hide();

        category_name = findViewById(R.id.category_name);
        but_back = findViewById(R.id.but_back);
        but_save = findViewById(R.id.but_save);
        pic = findViewById(R.id.pic);

        db = new DatabaseHandler(AddCategory.this);

        intent = getIntent();
        if(intent.getStringExtra("MED_CAT_EDIT") != null) {
            category_name.setText(db.getMedicineCategory(intent.getStringExtra("MED_CAT_EDIT")).get(0).getMedicineCategoryName());
            pic.setImageBitmap(DBUtils.getBitmapFromBytes(db.getMedicineCategory(intent.getStringExtra("MED_CAT_EDIT")).get(0).getMedicineCategoryImage()));
        }

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getStringExtra("MED_CAT_EDIT") != null) {
                    db.updateMedicineCategory(new medicinesCategory(Integer.parseInt(intent.getStringExtra("MED_CAT_EDIT")), category_name.getText().toString(), DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())));
                } else {
                    db.addMedicineCategory(new medicinesCategory(category_name.getText().toString(), DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())));
                }

                finish();
            }
        });


        pic .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Choose From Gallery", "Take Photo"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddCategory.this);
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

        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap newProfilePic = extras.getParcelable("data");
                pic.setImageBitmap(newProfilePic);
            }
        }
    }

}
