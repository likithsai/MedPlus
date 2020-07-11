package com.example.medicplus.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.example.medicplus.adapter.CustomSearchTextview;
import com.example.medicplus.adapter.ListAdapterCategory;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.medicines;
import com.example.medicplus.database.medicinesCategory;
import com.example.medicplus.utils.DBUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("ALL")
public class AddMedicines extends AppCompatActivity {

    EditText  medicine_price, medicine_qty, medicine_barcode, medicine_company, medicine_strength, medicine_description, medicine_location;
    TextView medicine_expiry_date, but_spinner;
    ImageView but_back, but_save;
    ArrayList<String> arrayList = new ArrayList<>();
    ImageView pic, img_barcode;
    public DatabaseHandler db;
    private List<medicinesCategory> materialCategory;
    Dialog myDialog;
    private Intent intent;
    AutoCompleteTextView medicine_name;
    private List<medicines> mMed;
    private CustomSearchTextview adapter;
    private List<medicinesCategory> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);
        getSupportActionBar().hide();


        but_back = findViewById(R.id.but_back);
        but_save = findViewById(R.id.but_save);
        but_spinner = findViewById(R.id.spinner);
        pic = findViewById(R.id.pic);
        img_barcode = findViewById(R.id.img_barcode);
        medicine_name = findViewById(R.id.medicine_name);
        medicine_price = findViewById(R.id.medicine_price);
        medicine_qty = findViewById(R.id.medicine_qty);
        medicine_expiry_date = findViewById(R.id.medicine_expiry_date);
        medicine_barcode = findViewById(R.id.medicine_barcode);
        medicine_company = findViewById(R.id.medicine_company);
        medicine_strength = findViewById(R.id.medicine_strength);
        medicine_description = findViewById(R.id.medicine_description);
        medicine_location = findViewById(R.id.medicine_location);
        myDialog = new Dialog(this);

        loadMedicineCategory();



        db = new DatabaseHandler(AddMedicines.this);
        final List<medicines>[] medicine = new List[]{db.getMedicine()};
        mMed = new ArrayList<>(medicine[0].size());
        for(int i = 0; i< medicine[0].size(); i++) {
            mMed.add(new medicines(medicine[0].get(i).getMedicineName(), medicine[0].get(i).getMedicineExpiry(), medicine[0].get(i).getMedicineSellingPrice(), medicine[0].get(i).getMedicineQuantity()));
        }
        adapter = new CustomSearchTextview(AddMedicines.this, R.layout.activity_main, R.id.lbl_name, mMed);
        medicine_name.setAdapter(adapter);
        medicine_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private List<medicines> medicine_details;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                // this is the way to find selected object/item
                String medicine_name = ((medicines) adapterView.getItemAtPosition(pos)).getMedicineName();
                medicine_details = db.searchMedicines(medicine_name);

                medicine_barcode.setText(medicine_details.get(0).getMedicineBarcode());
                medicine_expiry_date.setText(medicine_details.get(0).getMedicineExpiry());
                but_spinner.setText(medicine_details.get(0).getMedicineCategory());
                medicine_price.setText(medicine_details.get(0).getMedicineSellingPrice());
                medicine_qty.setText(medicine_details.get(0).getMedicineQuantity());
                pic.setImageBitmap(DBUtils.getBitmapFromBytes(medicine_details.get(0).getMedicineImage()));
                medicine_description.setText(medicine_details.get(0).getMedicineDescription());
                medicine_company.setText(medicine_details.get(0).getMedicineCompany());
                medicine_strength.setText(medicine_details.get(0).getMedicineStrength());
                medicine_description.setText(medicine_details.get(0).getMedicineDescription());
                medicine_location.setText(medicine_details.get(0).getMedicineLocation());

            }
        });




        intent = getIntent();
        if(intent.getStringExtra("MED_EDIT") != null) {
            DatabaseHandler edit = new DatabaseHandler(AddMedicines.this);
            medicine_name.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineName());
            medicine_barcode.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineBarcode());
            but_spinner.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineCategory());
            medicine_price.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineSellingPrice());
            medicine_qty.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineQuantity());
            medicine_expiry_date.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineExpiry());
            medicine_company.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineCompany());
            medicine_strength.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineStrength());
            medicine_description.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineDescription());
            medicine_location.setText(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineLocation());
            pic.setImageBitmap(DBUtils.getBitmapFromBytes(edit.getMedicine(intent.getStringExtra("MED_EDIT")).get(0).getMedicineImage()));
        }


        but_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = new DatabaseHandler(AddMedicines.this);
                category = db.getMedicineCategory();
                ArrayList<String> category_id = new ArrayList<>();
                ArrayList<String> category_name = new ArrayList<>();
                ArrayList<Bitmap> category_image = new ArrayList<>();

                for(int i=0; i<category.size(); i++) {
                    category_id.add(String.valueOf(category.get(i).getID()));
                    category_name.add(category.get(i).getMedicineCategoryName());
                    category_image.add(DBUtils.getBitmapFromBytes(category.get(i).getMedicineCategoryImage()));
                }

                ListAdapterCategory lCategory = new ListAdapterCategory(AddMedicines.this, category_id, category_name, category_image);
                Context context = new ContextThemeWrapper(AddMedicines.this, R.style.MaterialTheme);
                new MaterialAlertDialogBuilder(context)
                    .setTitle("Category")
                    .setAdapter(lCategory, null)
                    .setItems(category_name.toArray(new String[category_name.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            but_spinner.setText(category_name.get(which));
                        }
                    })
                    .setNeutralButton("Add Category", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(AddMedicines.this, AddCategory.class);
                            startActivity(in);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            }
        });



        img_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(AddMedicines.this).initiateScan();
            }
        });


        medicine_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicines.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        medicine_expiry_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });


        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(intent.getStringExtra("MED_EDIT") != null) {
                    db = new DatabaseHandler(AddMedicines.this);
                    db.updateMedicine(
                            new medicines(
                                    Integer.parseInt(intent.getStringExtra("MED_EDIT")),
                                    medicine_name.getText().toString(),
                                    medicine_barcode.getText().toString(),
                                    but_spinner.getText().toString(),
                                    medicine_company.getText().toString(),
                                    medicine_strength.getText().toString(),
                                    medicine_description.getText().toString(),
                                    medicine_location.getText().toString(),
                                    medicine_qty.getText().toString(),
                                    medicine_expiry_date.getText().toString(),
                                    medicine_price.getText().toString(),
                                    DBUtils.getBytes(((BitmapDrawable) pic.getDrawable()).getBitmap())
                            )
                    );

                    Toast.makeText(AddMedicines.this, "Saved", Toast.LENGTH_SHORT).show();

                } else {
                    db = new DatabaseHandler(AddMedicines.this);
                    db.addMedicine(
                        new medicines(
                                medicine_name.getText().toString(),
                                medicine_barcode.getText().toString(),
                                but_spinner.getText().toString(),
                                medicine_company.getText().toString(),
                                medicine_strength.getText().toString(),
                                medicine_description.getText().toString(),
                                medicine_location.getText().toString(),
                                medicine_qty.getText().toString(),
                                medicine_expiry_date.getText().toString(),
                                medicine_price.getText().toString(),
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

        pic .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Choose From Gallery", "Take Photo"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMedicines.this);
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








    private void loadMedicineCategory() {
        arrayList.clear();
        arrayList.add("Syrup");
        db = new DatabaseHandler(this);
        materialCategory =  db.getMedicineCategory();

        for(int i=0; i<materialCategory.size(); i++) {
            arrayList.add(materialCategory.get(i).getMedicineCategoryName());
        }
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
                Bitmap newProfilePic = extras.getParcelable("data");
                pic.setImageBitmap(newProfilePic);
            }
        }


        //barcode
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                medicine_barcode.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    protected void onResume() {
        super.onResume();
        loadMedicineCategory();
    }
}
