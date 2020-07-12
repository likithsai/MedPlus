package com.example.medicplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.medicplus.R;
import com.example.medicplus.adapter.ListAdapterMedicines;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.medicines;
import com.example.medicplus.utils.DBUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OutOfStockMedicines extends AppCompatActivity {

    List<medicines> outOfStock;
    ImageView but_back;
    private DatabaseHandler db;
    private ListAdapterMedicines lAdapter;
    ListView outOfStockListview;
    TextView txtOutOfStockMedicines;
    TextView outofmedicines_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_stock);

        Objects.requireNonNull(getSupportActionBar()).hide();

        but_back = findViewById(R.id.but_back);
        outOfStockListview = findViewById(R.id.outOfStockListview);
        txtOutOfStockMedicines = findViewById(R.id.txtOutOfStockMedicines);
        outofmedicines_count = findViewById(R.id.outofmedicines_count);


        db = new DatabaseHandler(OutOfStockMedicines.this);
        loadOutOfMedicines();

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private void loadOutOfMedicines() {
        outOfStock =  db.getOutOfStockMedicine();
        outofmedicines_count.setText("" + new DatabaseHandler(OutOfStockMedicines.this).getOutOfStockMedicinesCount());

        ArrayList<Bitmap> med_images = new ArrayList<>();
        ArrayList<String> med_id = new ArrayList<>();
        ArrayList<String> med_name = new ArrayList<>();
        ArrayList<String> med_category = new ArrayList<>();
        ArrayList<String> med_company = new ArrayList<>();
        ArrayList<String> med_strength = new ArrayList<>();
        ArrayList<String> med_description = new ArrayList<>();
        ArrayList<String> med_location = new ArrayList<>();
        ArrayList<String> med_expired_date = new ArrayList<>();
        ArrayList<String> med_selling_price = new ArrayList<>();
        ArrayList<String> med_qty = new ArrayList<>();
        ArrayList<String> med_added_date = new ArrayList<>();

        for(int i=0; i<outOfStock.size(); i++) {
            med_id.add(String.valueOf(outOfStock.get(i).getID()));
            med_name.add(outOfStock.get(i).getMedicineName());
            med_category.add(outOfStock.get(i).getMedicineCategory());
            med_company.add(outOfStock.get(i).getMedicineStrength());
            med_strength.add(outOfStock.get(i).getMedicineStrength());
            med_description.add(outOfStock.get(i).getMedicineDescription());
            med_location.add(outOfStock.get(i).getMedicineLocation());
            med_expired_date.add(outOfStock.get(i).getMedicineExpiry());
            med_selling_price.add(outOfStock.get(i).getMedicineSellingPrice());
            med_qty.add(outOfStock.get(i).getMedicineQuantity());
            med_images.add(DBUtils.getBitmapFromBytes(outOfStock.get(i).getMedicineImage()));
            med_added_date.add(outOfStock.get(i).getMedicineAddDate());

        }

        lAdapter = new ListAdapterMedicines(OutOfStockMedicines.this, med_name, med_category, med_expired_date, med_selling_price, med_qty, med_images);
        outOfStockListview.setEmptyView(findViewById(R.id.emptyListview));
        outOfStockListview.setAdapter(lAdapter);





        outOfStockListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View bottom_option = getLayoutInflater().inflate(R.layout.activity_view_medicine, null);

                TextView medicine_name = (TextView) bottom_option.findViewById(R.id.medicine_name);
                TextView medicine_price = (TextView) bottom_option.findViewById(R.id.medicine_price);
                TextView medicine_qty = (TextView) bottom_option.findViewById(R.id.medicine_qty);
                TextView medicine_expiry = (TextView) bottom_option.findViewById(R.id.medicine_expiry);
                TextView medicine_category = (TextView) bottom_option.findViewById(R.id.medicine_category);
                TextView medicine_company = (TextView) bottom_option.findViewById(R.id.medicine_comp);
                TextView medicine_strength = (TextView) bottom_option.findViewById(R.id.medicine_stren);
                TextView medicine_description = (TextView) bottom_option.findViewById(R.id.medicine_description);
                TextView medicine_location = (TextView) bottom_option.findViewById(R.id.medicine_Location);
                ImageView medicine_image = (ImageView) bottom_option.findViewById(R.id.appIconIV);
                Button but_delete = (Button) bottom_option.findViewById(R.id.but_delete);
                Button but_edit = (Button) bottom_option.findViewById(R.id.but_edit);

                medicine_name.setText(med_name.get(i));
                medicine_price.setText("₹ " + med_selling_price.get(i));
                medicine_qty.setText("Qty: " + med_qty.get(i));
                medicine_expiry.setText(med_expired_date.get(i));
                medicine_category.setText(med_category.get(i));
                medicine_company.setText(med_company.get(i));
                medicine_strength.setText(med_strength.get(i));
                medicine_description.setText(med_description.get(i));
                medicine_location.setText(med_location.get(i));
                medicine_image.setImageBitmap(med_images.get(i));

                BottomSheetDialog dialog = new BottomSheetDialog(OutOfStockMedicines.this);
                dialog.setContentView(bottom_option);
                dialog.show();

                but_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(OutOfStockMedicines.this)
                                .setTitle("Delete Store")
                                .setMessage("Do you want to delete Medicine " + med_name.get(i) + "?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int a) {
                                        db.deleteMedicine(String.valueOf(med_id.get(i)));

                                        if (dialog.isShowing()){
                                            dialog.dismiss();
                                        }

                                        loadOutOfMedicines();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();


                    }
                });

                but_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addStore = new Intent(OutOfStockMedicines.this, AddMedicines.class);
//                                addStore.putExtra("MED_EDIT", med_id[i]);
                        addStore.putExtra("MED_EDIT", med_id.get(i));
                        startActivity(addStore);

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });
            }
        });






        txtOutOfStockMedicines.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                outOfStock =  db.searchOutOfStockMedicine(s.toString());
                outofmedicines_count.setText("" + new DatabaseHandler(OutOfStockMedicines.this).searchOutOfStockMedicineCount(s.toString()));

                med_images.clear();
                med_id.clear();
                med_name.clear();
                med_category.clear();
                med_company.clear();
                med_strength.clear();
                med_description.clear();
                med_location.clear();
                med_expired_date.clear();
                med_selling_price.clear();
                med_qty.clear();
                med_added_date.clear();

                for(int i=0; i<outOfStock.size(); i++) {
                    med_id.add(String.valueOf(outOfStock.get(i).getID()));
                    med_name.add(outOfStock.get(i).getMedicineName());
                    med_category.add(outOfStock.get(i).getMedicineCategory());
                    med_company.add(outOfStock.get(i).getMedicineStrength());
                    med_strength.add(outOfStock.get(i).getMedicineStrength());
                    med_description.add(outOfStock.get(i).getMedicineDescription());
                    med_location.add(outOfStock.get(i).getMedicineLocation());
                    med_expired_date.add(outOfStock.get(i).getMedicineExpiry());
                    med_selling_price.add(outOfStock.get(i).getMedicineSellingPrice());
                    med_qty.add(outOfStock.get(i).getMedicineQuantity());
                    med_images.add(DBUtils.getBitmapFromBytes(outOfStock.get(i).getMedicineImage()));
                    med_added_date.add(outOfStock.get(i).getMedicineAddDate());

                }

                lAdapter = new ListAdapterMedicines(OutOfStockMedicines.this, med_name, med_category, med_expired_date, med_selling_price, med_qty, med_images);
                outOfStockListview.setEmptyView(findViewById(R.id.emptyListview));
                outOfStockListview.setAdapter(lAdapter);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
