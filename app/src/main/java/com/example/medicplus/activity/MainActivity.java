package com.example.medicplus.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.medicplus.R;
import com.example.medicplus.adapter.ListAdapterCustomer;
import com.example.medicplus.adapter.ListAdapterInvoiceList;
import com.example.medicplus.adapter.ListAdapterManufacturer;
import com.example.medicplus.adapter.ListAdapterMedicines;
import com.example.medicplus.adapter.ListAdapterStores;
import com.example.medicplus.database.DatabaseHandler;
import com.example.medicplus.database.TotalSalesChartHandler;
import com.example.medicplus.database.invoice;
import com.example.medicplus.database.medicines;
import com.example.medicplus.utils.DBUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    EditText txtMedicines, txtManufacturer, txtCustomer, txtInvoice;
    ImageView but_settings, btn_drawer;
    TextView medicine_count, manufacturer_count, customer_count, invoice_count, store, txtCategory, totalSales;
    BottomNavigationView bottomBar;
    public DatabaseHandler db;
    ListView lst, lst_invoice, lst_manufacturer, lst_customer;
    ListAdapterMedicines lAdapter;
    ListAdapterManufacturer lAdapterManufacturer;
    ListAdapterCustomer lAdapterCustomer;
    ListAdapterInvoiceList lAdapterInvoice;
    private List<medicines> medicine;
    private List<invoice> invoice;
    private List<com.example.medicplus.database.manufacturer> manufacturer;
    private List<com.example.medicplus.database.customer> customer;
    private List<TotalSalesChartHandler> totalSalesHandler;
    private DrawerLayout dl;
    ViewFlipper page;
    private List<com.example.medicplus.database.store> store_data;
    boolean flag = true;
    private ExtendedFloatingActionButton addManufacturer, addCustomer, addInvoice, displayOptions, addMedicines, expiredMedicines, outOfStockMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        requestPermision();

        bottomBar = findViewById(R.id.bottomBar);
        but_settings = findViewById(R.id.but_settings);
        btn_drawer = findViewById(R.id.btn_drawer);
        medicine_count = findViewById(R.id.medicine_count);
        manufacturer_count = findViewById(R.id.manufacturer_count);
        customer_count = findViewById(R.id.customer_count);
        invoice_count = findViewById(R.id.invoice_count);
        lst = findViewById(R.id.listview);
        lst_invoice = findViewById(R.id.lst_invoice);
        lst_manufacturer = findViewById(R.id.lst_manufacturer);
        lst_customer = findViewById(R.id.lst_customer);
        addMedicines = findViewById(R.id.addMedicines);
        expiredMedicines = findViewById(R.id.expiredMedicines);
        outOfStockMedicines = findViewById(R.id.outOfStockMedicines);
        addManufacturer = findViewById(R.id.addManufacturer);
        addCustomer = findViewById(R.id.addCustomer);
        addInvoice = findViewById(R.id.addInvoice);
        txtMedicines = findViewById(R.id.txtMedicines);
        txtInvoice = findViewById(R.id.txtinvoice);
        txtManufacturer = findViewById(R.id.txtManufacturer);
        txtCustomer = findViewById(R.id.txtCustomer);
        displayOptions = findViewById(R.id.displayOptions);
        totalSales = findViewById(R.id.totalSales);
        txtCategory = findViewById(R.id.txtCategory);
        dl = findViewById(R.id.activity_main);
        store = findViewById(R.id.store);
        page = findViewById(R.id.page);
        //page.setInAnimation(this, android.R.anim.slide_in_left);
        //page.setOutAnimation(this, android.R.anim.slide_out_right);

        db = new DatabaseHandler(MainActivity.this);

        loadHome();

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_data = db.getStore();

                ArrayList<String> store_name = new ArrayList<>();
                ArrayList<String> store_city = new ArrayList<>();
                ArrayList<String> store_id = new ArrayList<>();
                ArrayList<Bitmap> store_images = new ArrayList<>();

                for(int i=0; i<store_data.size(); i++) {
                    store_name.add(store_data.get(i).getStoreName());
                    store_city.add(store_data.get(i).getStoreCity());
                    store_images.add(DBUtils.getBitmapFromBytes(store_data.get(i).getStoreImage()));
                    store_id.add(String.valueOf(store_data.get(i).getStoreID()));
                }

                ListAdapterStores lStore = new ListAdapterStores(MainActivity.this, store_name, store_city, store_images, store_id);
                Context context = new ContextThemeWrapper(MainActivity.this, R.style.MaterialTheme);
                new MaterialAlertDialogBuilder(context)
                    .setTitle("Stores")
                    .setAdapter(lStore, null)
                    .setItems(store_name.toArray(new String[store_name.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            store.setText(store_name.get(which) + ", " + store_city.get(which));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Add Store", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent addStore = new Intent(MainActivity.this, AddStore.class);
                            startActivity(addStore);
                        }
                    })
                    .show();
            }
        });


        btn_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(GravityCompat.START);
            }
        });


        but_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(MainActivity.this, PreferenceActivity.class);
                startActivity(settings);
            }
        });


        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString().toLowerCase()) {

                    case "home":
                        loadHome();
                        page.setDisplayedChild(0);
                        break;

                    case "invoice":
                        loadInvoice();
                        page.setDisplayedChild(1);
                        break;

                    case "medicines":
                        loadMedicines();
                        page.setDisplayedChild(2);
                        break;

                    case "manufacturer":
                        loadManufacturer();
                        page.setDisplayedChild(3);
                        break;

                    case "user":
                        loadCustomer();
                        page.setDisplayedChild(4);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

//        loadHome();
//        loadInvoice();
//        loadMedicines();
//        loadManufacturer();
//        loadCustomer();

//        Toast.makeText(this, "" + page.getDisplayedChild(), Toast.LENGTH_SHORT).show();

        switch (page.getDisplayedChild()) {
            case 0:
                loadHome();
                break;

            case 1:
                loadInvoice();
                break;

            case 2:
                loadMedicines();
                break;

            case 3:
                loadManufacturer();
                break;

            case 4:
                loadCustomer();
                break;

        }

    }



    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lst_preference, menu);
    }




    private void loadHome() {
        findViewById(R.id.emptyListview).setVisibility(View.GONE);
        txtCategory.setText("" + db.getMedicineCategoryCount());
        totalSales.setText("" + db.getTotalSales());

        showTotalSalesChart();
        showCategoryWiseContribution();
    }




    private void loadInvoice() {
        invoice = db.getInvoiceList();
        invoice_count.setText("" + new DatabaseHandler(this).getInvoiceCount());

        ArrayList<String> invoice_id = new ArrayList<>();
        ArrayList<String> invoice_desc = new ArrayList<>();
        ArrayList<String> invoice_date = new ArrayList<>();
        ArrayList<String> invoice_price = new ArrayList<>();
        ArrayList<String> invoice_payment_type = new ArrayList<>();
        ArrayList<String> invoice_qty = new ArrayList<>();

        for(int i=0; i<invoice.size(); i++) {
            invoice_id.add(String.valueOf(invoice.get(i).getInvoiceID()));
            invoice_desc.add(String.valueOf(invoice.get(i).getInvoiceDesc()));
            invoice_date.add(invoice.get(i).getInvoiceAddDate());
            invoice_price.add(invoice.get(i).getInvoiceItemPrice());
            invoice_qty.add(invoice.get(i).getInvoiceItemQty());
            invoice_payment_type.add(invoice.get(i).getInvoicePaymentType());
        }

        lAdapterInvoice = new ListAdapterInvoiceList(MainActivity.this, invoice_desc, invoice_date, invoice_price, invoice_qty, invoice_payment_type);
        lst_invoice.setEmptyView(findViewById(R.id.emptyListview));
        lst_invoice.setAdapter(lAdapterInvoice);


        txtInvoice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                invoice =  db.searchInvoice(s.toString());
                invoice_count.setText("" + new DatabaseHandler(MainActivity.this).searchInvoiceCount(s.toString()));

                invoice_id.clear();
                invoice_desc.clear();
                invoice_date.clear();
                invoice_price.clear();
                invoice_qty.clear();
                invoice_payment_type.clear();

                for(int i=0; i<invoice.size(); i++) {
                    invoice_id.add(String.valueOf(invoice.get(i).getInvoiceID()));
                    invoice_desc.add(String.valueOf(invoice.get(i).getInvoiceDesc()));
                    invoice_date.add(invoice.get(i).getInvoiceAddDate());
                    invoice_price.add(invoice.get(i).getInvoiceItemPrice());
                    invoice_qty.add(invoice.get(i).getInvoiceItemQty());
                    invoice_payment_type.add(invoice.get(i).getInvoicePaymentType());
                }

                lAdapterInvoice = new ListAdapterInvoiceList(MainActivity.this, invoice_desc, invoice_date, invoice_price, invoice_qty, invoice_payment_type);
                lst_invoice.setEmptyView(findViewById(R.id.emptyListview));
                lst_invoice.setAdapter(lAdapterInvoice);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        lst_invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View bottom_option = getLayoutInflater().inflate(R.layout.activity_view_medicine, null);
                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(bottom_option);
                dialog.show();
            }
        });


        addInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddInvoice.class);
                startActivity(in);
            }
        });
    }




    private void loadMedicines() {
        medicine =  db.getMedicine();
        medicine_count.setText("" + new DatabaseHandler(this).getMedicineCount());

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

        for(int i=0; i<medicine.size(); i++) {
            med_id.add(String.valueOf(medicine.get(i).getID()));
            med_name.add(medicine.get(i).getMedicineName());
            med_category.add(medicine.get(i).getMedicineCategory());
            med_company.add(medicine.get(i).getMedicineStrength());
            med_strength.add(medicine.get(i).getMedicineStrength());
            med_description.add(medicine.get(i).getMedicineDescription());
            med_location.add(medicine.get(i).getMedicineLocation());
            med_expired_date.add(medicine.get(i).getMedicineExpiry());
            med_selling_price.add(medicine.get(i).getMedicineSellingPrice());
            med_qty.add(medicine.get(i).getMedicineQuantity());
            med_images.add(DBUtils.getBitmapFromBytes(medicine.get(i).getMedicineImage()));
            med_added_date.add(medicine.get(i).getMedicineAddDate());

        }

        lAdapter = new ListAdapterMedicines(MainActivity.this, med_name, med_category, med_expired_date, med_selling_price, med_qty, med_images);
        lst.setEmptyView(findViewById(R.id.emptyListview));
        lst.setAdapter(lAdapter);


        addMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddMedicines.class);
                startActivity(in);
            }
        });

        displayOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag) {

//                    displayOptions.animate().rotationBy(45);
                    addMedicines.setVisibility(View.VISIBLE);
                    expiredMedicines.setVisibility(View.VISIBLE);
                    outOfStockMedicines.setVisibility(View.VISIBLE);

                    addMedicines.animate().translationY(-180);
                    expiredMedicines.animate().translationY(-360);
                    outOfStockMedicines.animate().translationY(-540);
                    flag = false;

                } else {

//                    displayOptions.animate().rotationBy(-45);
                    addMedicines.setVisibility(View.GONE);
                    expiredMedicines.setVisibility(View.GONE);
                    outOfStockMedicines.setVisibility(View.GONE);
                    flag = true;

                }
            }
        });


        expiredMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ExpiredMedicines.class);
                startActivity(in);
            }
        });


        outOfStockMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, OutOfStockMedicines.class);
                startActivity(in);
            }
        });



        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(bottom_option);
                dialog.show();



                but_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Delete Store")
                                .setMessage("Do you want to delete Medicine " + med_name.get(i) + "?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int a) {
                                        db.deleteMedicine(String.valueOf(med_id.get(i)));

                                        if (dialog.isShowing()){
                                            dialog.dismiss();
                                        }

                                        loadMedicines();
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
                        Intent addStore = new Intent(MainActivity.this, AddMedicines.class);
                        addStore.putExtra("MED_EDIT", med_id.get(i));
                        startActivity(addStore);

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });
            }
        });



        txtMedicines.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                medicine =  db.searchMedicines(s.toString());
                medicine_count.setText("" + new DatabaseHandler(MainActivity.this).searchMedicineCount(s.toString()));

                med_id.clear();
                med_images.clear();
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

                for(int i=0; i<medicine.size(); i++) {
                    med_id.add(String.valueOf(medicine.get(i).getID()));
                    med_name.add(medicine.get(i).getMedicineName());
                    med_category.add(medicine.get(i).getMedicineCategory());
                    med_company.add(medicine.get(i).getMedicineStrength());
                    med_strength.add(medicine.get(i).getMedicineStrength());
                    med_description.add(medicine.get(i).getMedicineDescription());
                    med_location.add(medicine.get(i).getMedicineLocation());
                    med_expired_date.add(medicine.get(i).getMedicineExpiry());
                    med_selling_price.add(medicine.get(i).getMedicineSellingPrice());
                    med_qty.add(medicine.get(i).getMedicineQuantity());
                    med_images.add(DBUtils.getBitmapFromBytes(medicine.get(i).getMedicineImage()));
                    med_added_date.add(medicine.get(i).getMedicineAddDate());
                }

                lAdapter = new ListAdapterMedicines(MainActivity.this, med_name, med_category, med_expired_date, med_selling_price, med_qty, med_images);
                lst.setEmptyView(findViewById(R.id.emptyListview));
                lst.setAdapter(lAdapter);

                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                        dialog.setContentView(bottom_option);
                        dialog.show();

                        but_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Delete Store")
                                        .setMessage("Do you want to delete Medicine " + med_name.get(i) + "?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int a) {
                                                db.deleteMedicine(String.valueOf(med_id.get(i)));

                                                if (dialog.isShowing()){
                                                    dialog.dismiss();
                                                }

                                                loadMedicines();
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
                                Intent addStore = new Intent(MainActivity.this, AddMedicines.class);
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    private void loadManufacturer() {
        manufacturer = db.getManufacturer();
        manufacturer_count.setText("" + new DatabaseHandler(this).getManufacturerCount());

        ArrayList<String> man_id = new ArrayList<>();
        ArrayList<String> man_name = new ArrayList<>();
        ArrayList<String> man_phone = new ArrayList<>();
        ArrayList<String> man_address = new ArrayList<>();
        ArrayList<String> man_added_date = new ArrayList<>();
        ArrayList<Bitmap> man_images = new ArrayList<>();

        for(int i=0; i<manufacturer.size(); i++) {
            man_id.add(String.valueOf(manufacturer.get(i).getManufacturerID()));
            man_name.add(manufacturer.get(i).getManufacturerName());
            man_phone.add(manufacturer.get(i).getManufacturerMobile());
            man_address.add(manufacturer.get(i).getManufacturerAddress());
            man_added_date.add(manufacturer.get(i).getManufacturerAddDate());
            man_images.add(DBUtils.getBitmapFromBytes(manufacturer.get(i).getManufacturerImage()));
        }

        lAdapterManufacturer = new ListAdapterManufacturer(MainActivity.this, man_name, man_phone, man_images);
        lst_manufacturer.setEmptyView(findViewById(R.id.emptyListview));
        lst_manufacturer.setAdapter(lAdapterManufacturer);


        addManufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddManufacturer.class);
                startActivity(in);
            }
        });



        lst_manufacturer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View bottom_option = getLayoutInflater().inflate(R.layout.activity_view_manufacturer, null);

                ImageView manufacturer_image = bottom_option.findViewById(R.id.manufacturer_image);
                TextView manufacturer_name = bottom_option.findViewById(R.id.manufacturer_name);
                TextView manufacturer_mobile = bottom_option.findViewById(R.id.manufacturer_mobile);
                TextView manufacturer_address = bottom_option.findViewById(R.id.manufacturer_address);
                Button but_delete = (Button) bottom_option.findViewById(R.id.but_delete);
                Button but_edit = (Button) bottom_option.findViewById(R.id.but_edit);

                manufacturer_image.setImageBitmap(man_images.get(i));
                manufacturer_name.setText(man_name.get(i));
                manufacturer_mobile.setText(man_phone.get(i));
                manufacturer_address.setText(man_address.get(i));

                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(bottom_option);
                dialog.show();

                but_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Delete Store")
                                .setMessage("Do you want to delete Medicine " + man_name.get(i) + "?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int a) {
                                        db.deleteManufacturer(String.valueOf(man_id.get(i)));

                                        if (dialog.isShowing()){
                                            dialog.dismiss();
                                        }

                                        loadManufacturer();
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
                        Intent addStore = new Intent(MainActivity.this, AddManufacturer.class);
//                        addStore.putExtra("MAN_EDIT", man_id[i]);
                        addStore.putExtra("MAN_EDIT", man_id.get(i));
                        startActivity(addStore);

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });

            }
        });




        txtManufacturer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                manufacturer = db.searchManufacturer(s.toString());

                manufacturer_count.setText("" + new DatabaseHandler(MainActivity.this).searchManufacturerCount(s.toString()));

                man_id.clear();
                man_name.clear();
                man_phone.clear();
                man_address.clear();
                man_added_date.clear();
                man_images.clear();

                for(int i=0; i<manufacturer.size(); i++) {
                    man_id.add(String.valueOf(manufacturer.get(i).getManufacturerID()));
                    man_name.add(manufacturer.get(i).getManufacturerName());
                    man_phone.add(manufacturer.get(i).getManufacturerMobile());
                    man_address.add(manufacturer.get(i).getManufacturerAddress());
                    man_added_date.add(manufacturer.get(i).getManufacturerAddDate());
                    man_images.add(DBUtils.getBitmapFromBytes(manufacturer.get(i).getManufacturerImage()));

                }

                lAdapterManufacturer = new ListAdapterManufacturer(MainActivity.this, man_name, man_phone, man_images);
                lst_manufacturer.setEmptyView(findViewById(R.id.emptyListview));
                lst_manufacturer.setAdapter(lAdapterManufacturer);


                lst_manufacturer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        View bottom_option = getLayoutInflater().inflate(R.layout.activity_view_manufacturer, null);

                        ImageView manufacturer_image = bottom_option.findViewById(R.id.manufacturer_image);
                        TextView manufacturer_name = bottom_option.findViewById(R.id.manufacturer_name);
                        TextView manufacturer_mobile = bottom_option.findViewById(R.id.manufacturer_mobile);
                        TextView manufacturer_address = bottom_option.findViewById(R.id.manufacturer_address);
                        Button but_delete = (Button) bottom_option.findViewById(R.id.but_delete);
                        Button but_edit = (Button) bottom_option.findViewById(R.id.but_edit);

                        manufacturer_image.setImageBitmap(man_images.get(i));
                        manufacturer_name.setText(man_name.get(i));
                        manufacturer_mobile.setText(man_phone.get(i));
                        manufacturer_address.setText(man_address.get(i));

                        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                        dialog.setContentView(bottom_option);
                        dialog.show();


                        but_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Delete Store")
                                        .setMessage("Do you want to delete Manufacturer " + man_name.get(i) + "?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int a) {
                                                db.deleteManufacturer(String.valueOf(man_id.get(i)));

                                                if (dialog.isShowing()){
                                                    dialog.dismiss();
                                                }

                                                loadManufacturer();

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
                                Intent addStore = new Intent(MainActivity.this, AddManufacturer.class);
                                addStore.putExtra("MAN_EDIT", man_id.get(i));
                                startActivity(addStore);

                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }





    private void loadCustomer() {
        customer = db.getCustomer();
        customer_count.setText("" + new DatabaseHandler(this).getCustomerCount());

        ArrayList<Bitmap> cus_images = new ArrayList<>();
        ArrayList<String> cus_id = new ArrayList<>();
        ArrayList<String> cus_name = new ArrayList<>();
        ArrayList<String> cus_email = new ArrayList<>();
        ArrayList<String> cus_address = new ArrayList<>();
        ArrayList<String> cus_phone = new ArrayList<>();
        ArrayList<String> cus_added_date = new ArrayList<>();

        for(int i=0; i<customer.size(); i++) {
            cus_id.add(String.valueOf(customer.get(i).getCustomerID()));
            cus_name.add(customer.get(i).getCustomerName());
            cus_email.add(customer.get(i).getCustomerEmail());
            cus_address.add(customer.get(i).getCustomerAddress());
            cus_phone.add(customer.get(i).getCustomerPhone());
            cus_images.add(DBUtils.getBitmapFromBytes(customer.get(i).getCustomerImage()));
            cus_added_date.add(customer.get(i).getCustomerAddDate());
        }

        lAdapterCustomer = new ListAdapterCustomer(MainActivity.this, cus_name, cus_phone, cus_images);
        lst_customer.setEmptyView(findViewById(R.id.emptyListview));
        lst_customer.setAdapter(lAdapterCustomer);



        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddCustomer.class);
                startActivity(in);
            }
        });



        lst_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                View bottom_option = getLayoutInflater().inflate(R.layout.activity_view_customer, null);
                ImageView customer_image = bottom_option.findViewById(R.id.customer_image);
                TextView customer_name = bottom_option.findViewById(R.id.customer_name);
                TextView customer_mobile = bottom_option.findViewById(R.id.customer_mobile);
                TextView customer_email = bottom_option.findViewById(R.id.customer_email);
                TextView customer_address = bottom_option.findViewById(R.id.customer_address);
                Button but_delete = (Button) bottom_option.findViewById(R.id.but_delete);
                Button but_edit = (Button) bottom_option.findViewById(R.id.but_edit);


                customer_image.setImageBitmap(cus_images.get(i));
                customer_name.setText(cus_name.get(i));
                customer_mobile.setText(cus_phone.get(i));
                customer_email.setText(cus_email.get(i));
                customer_address.setText(cus_address.get(i));

                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(bottom_option);
                dialog.show();

                but_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Delete Store")
                                .setMessage("Do you want to delete Medicine " + cus_name.get(i) + "?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int a) {
                                        db.deleteCustomer(String.valueOf(cus_id.get(i)));

                                        if (dialog.isShowing()){
                                            dialog.dismiss();
                                        }

                                        loadCustomer();
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
                        Intent addStore = new Intent(MainActivity.this, AddCustomer.class);
                        addStore.putExtra("CUS_EDIT", cus_id.get(i));
                        startActivity(addStore);

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });

            }
        });


        txtCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customer = db.searchCustomer(s.toString());
                customer_count.setText("" + new DatabaseHandler(MainActivity.this).searchCustomerCount(s.toString()));

                cus_images.clear();
                cus_id.clear();
                cus_name.clear();
                cus_email.clear();
                cus_address.clear();
                cus_phone.clear();
                cus_added_date.clear();

                for(int i=0; i<customer.size(); i++) {
                    cus_id.add(String.valueOf(customer.get(i).getCustomerID()));
                    cus_name.add(customer.get(i).getCustomerName());
                    cus_email.add(customer.get(i).getCustomerEmail());
                    cus_address.add(customer.get(i).getCustomerAddress());
                    cus_phone.add(customer.get(i).getCustomerPhone());
                    cus_images.add(DBUtils.getBitmapFromBytes(customer.get(i).getCustomerImage()));
                    cus_added_date.add(customer.get(i).getCustomerAddDate());
                }

                lAdapterCustomer = new ListAdapterCustomer(MainActivity.this, cus_name, cus_phone, cus_images);
                lst_customer.setEmptyView(findViewById(R.id.emptyListview));
                lst_customer.setAdapter(lAdapterCustomer);


                lst_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        View bottom_option = getLayoutInflater().inflate(R.layout.activity_view_customer, null);
                        ImageView customer_image = bottom_option.findViewById(R.id.customer_image);
                        TextView customer_name = bottom_option.findViewById(R.id.customer_name);
                        TextView customer_mobile = bottom_option.findViewById(R.id.customer_mobile);
                        TextView customer_email = bottom_option.findViewById(R.id.customer_email);
                        TextView customer_address = bottom_option.findViewById(R.id.customer_address);
                        Button but_delete = (Button) bottom_option.findViewById(R.id.but_delete);
                        Button but_edit = (Button) bottom_option.findViewById(R.id.but_edit);

                        customer_image.setImageBitmap(cus_images.get(i));
                        customer_name.setText(cus_name.get(i));
                        customer_mobile.setText(cus_phone.get(i));
                        customer_email.setText(cus_email.get(i));
                        customer_address.setText(cus_address.get(i));

                        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                        dialog.setContentView(bottom_option);
                        dialog.show();


                        but_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Delete Store")
                                        .setMessage("Do you want to delete Medicine " + cus_name.get(i) + "?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int a) {
                                                db.deleteCustomer(String.valueOf(cus_id.get(i)));

                                                if (dialog.isShowing()){
                                                    dialog.dismiss();
                                                }

                                                loadCustomer();
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
                                Intent addStore = new Intent(MainActivity.this, AddCustomer.class);
                                addStore.putExtra("CUS_EDIT", cus_id.get(i));
                                startActivity(addStore);

                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    private void showTotalSalesChart() {
        LineChart chart = (LineChart) findViewById(R.id.chart1);
        ArrayList<Entry> entries = new ArrayList<>();
        final ArrayList<String> xEntries = new ArrayList<>();

        totalSalesHandler =  db.getMonthWiseData();
        for(int i=0; i<totalSalesHandler.size(); i++) {
            entries.add(new Entry(i+1, totalSalesHandler.get(i).getTotalSales()));
            xEntries.add(totalSalesHandler.get(i).getSalesMonth());
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "Total Sales");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.chart_fillcolor));
        lineDataSet.setValueTextSize(10);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setColor(R.color.colorPrimary);
        lineDataSet.setCircleColor(R.color.colorPrimary);
        lineDataSet.setCircleColorHole(R.color.colorPrimary);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        chart.setData(data);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
//        chart.getAxisRight().setStartAtZero(false);
//        chart.getAxisLeft().setStartAtZero(false);
        chart.setDrawGridBackground(false);
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);
        chart.setBackgroundColor(Color.TRANSPARENT);
        chart.setDragEnabled(true);
        chart.setScaleYEnabled(true);
        chart.setScaleXEnabled(true);
        chart.setDescription(null);
        chart.setExtraTopOffset(10f);
        chart.setDrawBorders(false);
        chart.setExtraOffsets(15, 0, 75, 15);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {
                return xEntries.get( (int) value % xEntries.size() );
            }
        });

        //chart.getXAxis().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.invalidate();
    }




    private void showCategoryWiseContribution() {
        PieChart chart = (PieChart) findViewById(R.id.categoryChart);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(2f, 0));
        pieEntries.add(new PieEntry(4f, 1));
        pieEntries.add(new PieEntry(6f, 2));
        pieEntries.add(new PieEntry(8f, 3));
        pieEntries.add(new PieEntry(7f, 4));
        pieEntries.add(new PieEntry(3f, 5));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "categories");
        pieDataSet.setColors(
                Color.parseColor("#1A00e676"),
                Color.parseColor("#3300e676"),
                Color.parseColor("#4D00e676"),
                Color.parseColor("#6600e676"),
                Color.parseColor("#8000e676"),
                Color.parseColor("#9900e676"),
                Color.parseColor("#B300e676"),
                Color.parseColor("#CC00e676"),
                Color.parseColor("#E600e676")
        );

        chart.setData(new PieData(pieDataSet));
        chart.getLegend().setEnabled(false);
        chart.setBackgroundColor(Color.TRANSPARENT);
        chart.setDescription(null);
        chart.setExtraTopOffset(10f);
        chart.invalidate();

    }




    @Override
    public void onBackPressed() {
        if(dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawers();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }





    public void requestPermision(){
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED || permissionCheck3 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }
    }

}
