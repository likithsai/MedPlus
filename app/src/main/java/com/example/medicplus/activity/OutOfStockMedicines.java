package com.example.medicplus.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.example.medicplus.adapter.ListAdapterMedicines;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.medicines;
import com.example.medicplus.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OutOfStockMedicines extends AppCompatActivity {

    List<medicines> outOfStock;
    ImageView but_back;
    private DatabaseHandler db;
    private ListAdapterMedicines lAdapter;
    ListView outOfStockListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_stock);

        Objects.requireNonNull(getSupportActionBar()).hide();

        but_back = findViewById(R.id.but_back);
        outOfStockListview = findViewById(R.id.outOfStockListview);

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
        outOfStockListview.setAdapter(lAdapter);

    }
}
