package com.example.medicplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicplus.R;
import com.example.medicplus.adapter.CustomSearchTextview;
import com.example.medicplus.adapter.ListAdapterInvoice;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.invoice;
import com.example.medicplus.database.medicines;
import com.example.medicplus.utils.DBUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ALL")
public class AddInvoice extends AppCompatActivity {

    EditText inv_number, inv_date, inv_desc;
    Spinner inv_payment_type;
    ImageButton btn_barcode;
    ImageView but_back, but_save;
    public DatabaseHandler db;
    private Intent intent;
    AutoCompleteTextView txtMedicine;
    private CustomSearchTextview adapter;
    private List<medicines> mMed;
    private ListView lstItems;
    private ArrayList<String> med_name, med_expiry, med_category, med_price, med_qty, sel_qty;
    private ArrayList<Bitmap> med_img;
    private ListAdapterInvoice lAdapter;
    public TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);

        getSupportActionBar().hide();

        lAdapter = new ListAdapterInvoice(AddInvoice.this);

        btn_barcode = findViewById(R.id.btn_barcode);
        inv_number = findViewById(R.id.inv_number);
        inv_date = findViewById(R.id.inv_date);
        txtMedicine = findViewById(R.id.txtMedicines);
        lstItems = findViewById(R.id.lst_items);
        but_back = findViewById(R.id.but_back);
        txtTotal = findViewById(R.id.txttotal);
        but_save = findViewById(R.id.but_save);
        inv_desc = findViewById(R.id.inv_desc);
        inv_payment_type = findViewById(R.id.inv_payment_type);

        med_name = new ArrayList<>();
        med_expiry = new ArrayList<>();
        med_category = new ArrayList<>();
        med_price = new ArrayList<>();
        med_qty = new ArrayList<>();
        med_img = new ArrayList<>();
        sel_qty = new ArrayList<>();


        db = new DatabaseHandler(AddInvoice.this);
        final List<medicines>[] medicine = new List[]{db.getMedicine()};
        mMed = new ArrayList<>(medicine[0].size());
        for(int i = 0; i< medicine[0].size(); i++) {
            mMed.add(new medicines(medicine[0].get(i).getMedicineName(), medicine[0].get(i).getMedicineExpiry(), medicine[0].get(i).getMedicineSellingPrice(), medicine[0].get(i).getMedicineQuantity()));
        }

        adapter = new CustomSearchTextview(AddInvoice.this, R.layout.activity_main, R.id.lbl_name, mMed);
        txtMedicine.setAdapter(adapter);
        txtMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private List<medicines> medicine_details;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                 String medicine_name = ((medicines) adapterView.getItemAtPosition(pos)).getMedicineName();
                 medicine_details = db.searchMedicines(medicine_name);

                 med_name.add(medicine_details.get(0).getMedicineName());
                 med_expiry.add(medicine_details.get(0).getMedicineExpiry());
                 med_category.add(medicine_details.get(0).getMedicineCategory());
                 med_price.add(medicine_details.get(0).getMedicineSellingPrice());
                 med_qty.add(medicine_details.get(0).getMedicineQuantity());
                 med_img.add(DBUtils.getBitmapFromBytes(medicine_details.get(0).getMedicineImage()));
                 sel_qty.add("1");

                 lAdapter = new ListAdapterInvoice(AddInvoice.this, med_name, med_expiry, med_category, med_price, med_qty, med_img, sel_qty);
                 lstItems.setEmptyView(findViewById(R.id.emptyListview));
                 lstItems.setAdapter(lAdapter);
                 txtMedicine.setText(null);

                 txtTotal.setText("₹ " + lAdapter.getTotal());

            }
        });


        btn_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(AddInvoice.this).initiateScan();
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<med_name.size(); i++) {
                    db.addInvoice(
                            new invoice(
                                inv_desc.getText().toString(),
                                inv_number.getText().toString(),
                                lAdapter.getInvoiceName().get(i).toString(),
                                lAdapter.getInvoicePrice().get(i).toString(),
                                lAdapter.getInvoiceQty().get(i).toString(),
                                inv_payment_type.getSelectedItem().toString()
                            )
                    );
                }

                finish();

            }
        });


        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lAdapter.getCount() > 0) {
                    new AlertDialog.Builder(AddInvoice.this)
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                } else {
                    finish();
                }
            }
        });

        inv_number.setText(new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()));
        inv_date.setText(new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(new Date()));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        //barcode
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "" + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        if(lAdapter.getCount() > 0) {
            new AlertDialog.Builder(AddInvoice.this)
                    .setMessage("Do you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        } else {
            finish();
        }
    }
}
