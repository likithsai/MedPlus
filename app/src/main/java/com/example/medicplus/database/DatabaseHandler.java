package com.example.medicplus.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.medicplus.activity.OutOfStockMedicines;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@SuppressWarnings("ALL")
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Medplus";
    private static final String TABLE_MEDICINE = "tblmedicines";
    private static final String TABLE_MEDICINE_CATEGORY = "tblmedicinecategory";
    private static final String TABLE_MANUFACTURER = "tblmanufacturer";
    private static final String TABLE_CUSTOMER = "tblcustomer";
    private static final String TABLE_STORE = "tblstore";
    private static final String TABLE_INVOICE = "tblinvoice";
    private static final String KEY_MED_ID = "medicine_id";
    private static final String KEY_MED_NAME = "medicine_name";
    private static final String KEY_MED_BARCODE = "medicine_barcode";
    private static final String KEY_MED_CATEGORY = "medicine_category";
    private static final String KEY_MED_COMPANY = "medicine_company";
    private static final String KEY_MED_STRENGTH = "medicine_strength";
    private static final String KEY_MED_DESCRIPTION = "medicine_description";
    private static final String KEY_MED_LOCATION = "medicine_location";
    private static final String KEY_MED_QUANTITY = "medicine_quantity";
    private static final String KEY_MED_EXPIRY_DATE = "medicine_expiry_date";
    private static final String KEY_MED_SELLING_PRICE = "medicine_selling_price";
    private static final String KEY_MED_IMAGE = "medicine_image";
    private static final String KEY_MED_ADD_DATE = "medicine_added_date";
    private static final String KEY_MED_CAT_ID = "category_id";
    private static final String KEY_MED_CAT_NAME = "category_name";
    private static final String KEY_MED_CAT_IMAGE = "category_image";
    private static final String KEY_MED_CAT_ADD_DATE = "catergory_added_date";
    private static final String KEY_MANUFACTURER_ID = "manufacturer_id";
    private static final String KEY_MANUFACTURER_NAME = "manufacturer_name";
    private static final String KEY_MANUFACTURER_MOBILE = "manufacturer_mobile";
    private static final String KEY_MANUFACTURER_ADDRESS = "manufacturer_address";
    private static final String KEY_MANUFACTURER_IMAGE = "manufacturer_image";
    private static final String KEY_MANUFACTURER_ADD_DATE = "manufacturer_add_date";
    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_CUSTOMER_NAME = "customer_name";
    private static final String KEY_CUSTOMER_EMAIL = "customer_email";
    private static final String KEY_CUSTOMER_ADDRESS = "customer_address";
    private static final String KEY_CUSTOMER_PHONE = "customer_phone";
    private static final String KEY_CUSTOMER_IMAGE = "customer_image";
    private static final String KEY_CUS_ADD_DATE = "customer_add_date";
    private static final String KEY_STORE_ID = "store_id";
    private static final String KEY_STORE_NAME = "store_name";
    private static final String KEY_STORE_PHONE = "store_phone";
    private static final String KEY_STORE_CITY = "store_city";
    private static final String KEY_STORE_ADDRESS = "store_address";
    private static final String KEY_STORE_IMAGE = "store_image";
    private static final String KEY_STORE_ADD_DATE = "store_add_date";
    private static final String KEY_INVOICE_ID = "invoice_id";
    private static final String KEY_INVOICE_NAME = "invoice_name";
    private static final String KEY_INVOICE_DESC = "invoice_desc";
    private static final String KEY_INVOICE_DATE = "invoice_date";
    private static final String KEY_INVOICE_DUE_DATE = "invoice_due_date";
    private static final String KEY_INVOICE_ITEM_NAME = "invoice_item_name";
    private static final String KEY_INVOICE_ITEM_PRICE = "invoice_item_price";
    private static final String KEY_INVOICE_ITEM_QTY = "invoice_item_qty";
    private static final String KEY_INVOICE_ITEM_PAYMENT_TYPE = "invoice_item_payment_type";
    private static final String KEY_INVOICE_ADD_DATE = "invoice_add_date";
    private static final String KEY_INVOICE_DATE_MONTH = "invoice_date_month";
    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDICINE_TABLE = "CREATE TABLE " + TABLE_MEDICINE + "("
                + KEY_MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_MED_NAME + " TEXT,"
                + KEY_MED_BARCODE + " TEXT,"
                + KEY_MED_CATEGORY + " TEXT,"
                + KEY_MED_COMPANY + " TEXT,"
                + KEY_MED_STRENGTH + " TEXT,"
                + KEY_MED_DESCRIPTION + " TEXT,"
                + KEY_MED_LOCATION + " TEXT,"
                + KEY_MED_QUANTITY + " TEXT,"
                + KEY_MED_EXPIRY_DATE + " TEXT,"
                + KEY_MED_SELLING_PRICE + " TEXT,"
                + KEY_MED_IMAGE + " BLOB,"
                + KEY_MED_ADD_DATE + " DATETIME NOT NULL DEFAULT (strftime('%s', 'now')))";
        db.execSQL(CREATE_MEDICINE_TABLE);



        String CREATE_MEDICINE_CAT_TABLE = "CREATE TABLE " + TABLE_MEDICINE_CATEGORY + "("
                + KEY_MED_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_MED_CAT_NAME + " TEXT,"
                + KEY_MED_CAT_IMAGE + " BLOB,"
                + KEY_MED_CAT_ADD_DATE + " DATETIME NOT NULL DEFAULT (strftime('%s', 'now')))";
        db.execSQL(CREATE_MEDICINE_CAT_TABLE);



        String CREATE_MEDICINE_MANUFACTURER_TABLE = "CREATE TABLE " + TABLE_MANUFACTURER + "("
                + KEY_MANUFACTURER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_MANUFACTURER_NAME + " TEXT,"
                + KEY_MANUFACTURER_MOBILE + " TEXT,"
                + KEY_MANUFACTURER_ADDRESS + " TEXT,"
                + KEY_MANUFACTURER_IMAGE + " BLOB,"
                + KEY_MANUFACTURER_ADD_DATE + " DATETIME NOT NULL DEFAULT (strftime('%s', 'now')))";
        db.execSQL(CREATE_MEDICINE_MANUFACTURER_TABLE);



        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
                + KEY_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_CUSTOMER_NAME + " TEXT,"
                + KEY_CUSTOMER_EMAIL + " TEXT,"
                + KEY_CUSTOMER_ADDRESS + " TEXT,"
                + KEY_CUSTOMER_PHONE + " TEXT,"
                + KEY_CUSTOMER_IMAGE + " BLOB,"
                + KEY_CUS_ADD_DATE + " DATETIME NOT NULL DEFAULT (strftime('%s', 'now')))";
        db.execSQL(CREATE_CUSTOMER_TABLE);




        String CREATE_STORE_TABLE = "CREATE TABLE " + TABLE_STORE + "("
                + KEY_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_STORE_NAME + " TEXT,"
                + KEY_STORE_ADDRESS + " TEXT,"
                + KEY_STORE_CITY + " TEXT,"
                + KEY_STORE_PHONE + " TEXT,"
                + KEY_STORE_IMAGE + " BLOB, "
                + KEY_STORE_ADD_DATE + " DATETIME NOT NULL DEFAULT (strftime('%s', 'now')))";
        db.execSQL(CREATE_STORE_TABLE);

        String CREATE_INVOICE_TABLE = "CREATE TABLE " + TABLE_INVOICE + "("
                + KEY_INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_INVOICE_DESC + " TEXT,"
                + KEY_INVOICE_DATE + " TEXT,"
                + KEY_INVOICE_ITEM_NAME + " TEXT, "
                + KEY_INVOICE_ITEM_PRICE + " TEXT, "
                + KEY_INVOICE_ITEM_QTY + " TEXT, "
                + KEY_INVOICE_ITEM_PAYMENT_TYPE + " TEXT, "
                + KEY_INVOICE_ADD_DATE + " DATETIME NOT NULL DEFAULT (strftime('%s', 'now')),"
                + KEY_INVOICE_DATE_MONTH + " TEXT NOT NULL DEFAULT (case strftime('%m', 'now') when '01' then 'Jan' when '02' then 'Feb' when '03' then 'Mar' when '04' then 'Apr' when '05' then 'May' when '06' then 'Jun' when '07' then 'Jul' when '08' then 'Aug' when '09' then 'Sep' when '10' then 'Oct' when '11' then 'Nov' when '12' then 'Dec' else 'NULL' end))";
        db.execSQL(CREATE_INVOICE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANUFACTURER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);
        // Create tables again
        onCreate(db);
    }


    public void addMedicine(medicines filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_MED_ID, filemodal.getID()); // Contact Name
        values.put(KEY_MED_NAME, filemodal.getMedicineName());
        values.put(KEY_MED_BARCODE, filemodal.getMedicineBarcode());
        values.put(KEY_MED_CATEGORY, filemodal.getMedicineCategory()); // Contact Phone
        values.put(KEY_MED_COMPANY, filemodal.getMedicineCompany());
        values.put(KEY_MED_STRENGTH, filemodal.getMedicineStrength());
        values.put(KEY_MED_DESCRIPTION, filemodal.getMedicineDescription());
        values.put(KEY_MED_LOCATION, filemodal.getMedicineLocation());
        values.put(KEY_MED_QUANTITY, filemodal.getMedicineQuantity());
        values.put(KEY_MED_EXPIRY_DATE, filemodal.getMedicineExpiry());
        values.put(KEY_MED_SELLING_PRICE, filemodal.getMedicineSellingPrice());
        values.put(KEY_MED_IMAGE, filemodal.getMedicineImage());


        db.insert(TABLE_MEDICINE, null, values);
        db.close();
    }


    public void addMedicineCategory(medicinesCategory filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MED_CAT_NAME, filemodal.getMedicineCategoryName());
        values.put(KEY_MED_CAT_IMAGE, filemodal.getMedicineCategoryImage());
        db.insert(TABLE_MEDICINE_CATEGORY, null, values);
        db.close();
    }


    public void addManufacturer(manufacturer filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_MANUFACTURER_ID, filemodal.getManufacturerID());
        values.put(KEY_MANUFACTURER_NAME, filemodal.getManufacturerName());
        values.put(KEY_MANUFACTURER_MOBILE, filemodal.getManufacturerMobile());
        values.put(KEY_MANUFACTURER_ADDRESS, filemodal.getManufacturerAddress());
        values.put(KEY_MANUFACTURER_IMAGE, filemodal.getManufacturerImage());

        db.insert(TABLE_MANUFACTURER, null, values);
        db.close();
    }




    public void addCustomer(customer filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_CUSTOMER_ID, filemodal.getCustomerID());
        values.put(KEY_CUSTOMER_NAME, filemodal.getCustomerName());
        values.put(KEY_CUSTOMER_EMAIL, filemodal.getCustomerEmail());
        values.put(KEY_CUSTOMER_ADDRESS, filemodal.getCustomerAddress());
        values.put(KEY_CUSTOMER_PHONE, filemodal.getCustomerPhone());
        values.put(KEY_CUSTOMER_IMAGE, filemodal.getCustomerImage());

        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }



    public void addStore(store filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STORE_NAME, filemodal.getStoreName());
        values.put(KEY_STORE_ADDRESS, filemodal.getStoreAddress());
        values.put(KEY_STORE_CITY, filemodal.getStoreCity());
        values.put(KEY_STORE_PHONE, filemodal.getStorePhone()); // Contact Phone
        values.put(KEY_STORE_IMAGE, filemodal.getStoreImage());

        db.insert(TABLE_STORE, null, values);
        db.close();
    }


    public void addInvoice(invoice filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_INVOICE_DESC, filemodal.getInvoiceDesc());
        values.put(KEY_INVOICE_DATE, filemodal.getInvoiceDate());
        values.put(KEY_INVOICE_ITEM_NAME, filemodal.getInvoiceItemName());
        values.put(KEY_INVOICE_ITEM_PRICE, filemodal.getInvoiceItemPrice());
        values.put(KEY_INVOICE_ITEM_QTY, filemodal.getInvoiceItemQty());
        values.put(KEY_INVOICE_ITEM_PAYMENT_TYPE, filemodal.getInvoicePaymentType());


        db.insert(TABLE_INVOICE, null, values);
        db.close();
    }


    public int updateMedicine(medicines filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_MED_ID, filemodal.getID()); // Contact Name
        values.put(KEY_MED_NAME, filemodal.getMedicineName());
        values.put(KEY_MED_BARCODE, filemodal.getMedicineBarcode());
        values.put(KEY_MED_CATEGORY, filemodal.getMedicineCategory()); // Contact Phone
        values.put(KEY_MED_COMPANY, filemodal.getMedicineCompany());
        values.put(KEY_MED_STRENGTH, filemodal.getMedicineStrength());
        values.put(KEY_MED_DESCRIPTION, filemodal.getMedicineDescription());
        values.put(KEY_MED_LOCATION, filemodal.getMedicineLocation());
        values.put(KEY_MED_QUANTITY, filemodal.getMedicineQuantity());
        values.put(KEY_MED_EXPIRY_DATE, filemodal.getMedicineExpiry());
        values.put(KEY_MED_SELLING_PRICE, filemodal.getMedicineSellingPrice());
        values.put(KEY_MED_IMAGE, filemodal.getMedicineImage());

        return db.update(TABLE_MEDICINE, values, KEY_MED_ID + " = ?", new String[] { String.valueOf(filemodal.getID()) });
    }


    public int updateMedicineCategory(medicinesCategory filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_MED_CAT_ID, filemodal.getID()); // Contact Name
        values.put(KEY_MED_CAT_NAME, filemodal.getMedicineCategoryName());
        values.put(KEY_MED_CAT_IMAGE, filemodal.getMedicineCategoryImage());
        return db.update(TABLE_MEDICINE_CATEGORY, values, KEY_MED_CAT_ID + " = ?", new String[] { String.valueOf(filemodal.getID()) });
    }


    public int updateManufacturer(manufacturer filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_MANUFACTURER_ID, filemodal.getManufacturerID()); // Contact Name
        values.put(KEY_MANUFACTURER_NAME, filemodal.getManufacturerName());
        values.put(KEY_MANUFACTURER_MOBILE, filemodal.getManufacturerMobile());
        values.put(KEY_MANUFACTURER_ADDRESS, filemodal.getManufacturerAddress());
        values.put(KEY_MANUFACTURER_IMAGE, filemodal.getManufacturerImage());

        return db.update(TABLE_MANUFACTURER, values, KEY_MANUFACTURER_ID + " = ?", new String[] { String.valueOf(filemodal.getManufacturerID()) });
    }




    public int updateCustomer(customer filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_CUSTOMER_ID, filemodal.getCustomerID());
        values.put(KEY_CUSTOMER_NAME, filemodal.getCustomerName());
        values.put(KEY_CUSTOMER_EMAIL, filemodal.getCustomerEmail());
        values.put(KEY_CUSTOMER_ADDRESS, filemodal.getCustomerAddress());
        values.put(KEY_CUSTOMER_PHONE, filemodal.getCustomerPhone());
        values.put(KEY_CUSTOMER_IMAGE, filemodal.getCustomerImage());

        return db.update(TABLE_CUSTOMER, values, KEY_CUSTOMER_ID + " = ?", new String[] { String.valueOf(filemodal.getCustomerID()) });
    }


    public int updateStore(store filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_MED_ID, filemodal.getID()); // Contact Name
        values.put(KEY_STORE_NAME, filemodal.getStoreName());
        values.put(KEY_STORE_ADDRESS, filemodal.getStoreAddress());
        values.put(KEY_STORE_CITY, filemodal.getStoreCity());
        values.put(KEY_STORE_PHONE, filemodal.getStorePhone()); // Contact Phone
        values.put(KEY_STORE_IMAGE, filemodal.getStoreImage());

        return db.update(TABLE_STORE, values, KEY_STORE_ID + " = ?", new String[] { String.valueOf(filemodal.getStoreID()) });
    }


    public int updateInvoice(invoice filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_INVOICE_DESC, filemodal.getInvoiceDesc());
        values.put(KEY_INVOICE_DATE, filemodal.getInvoiceDate());
        values.put(KEY_INVOICE_ITEM_NAME, filemodal.getInvoiceItemName());
        values.put(KEY_INVOICE_ITEM_PRICE, filemodal.getInvoiceItemPrice());
        values.put(KEY_INVOICE_ITEM_QTY, filemodal.getInvoiceItemQty());
        values.put(KEY_INVOICE_ITEM_PAYMENT_TYPE, filemodal.getInvoicePaymentType());

        return db.update(TABLE_INVOICE, values, KEY_INVOICE_ID + " = ?", new String[] { String.valueOf(filemodal.getInvoiceID()) });
    }


    public void deleteAllRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE, null, null);
        db.delete(TABLE_MEDICINE_CATEGORY, null, null);
        db.delete(TABLE_MANUFACTURER, null, null);
        db.delete(TABLE_CUSTOMER, null, null);
        db.delete(TABLE_STORE, null, null);
        db.delete(TABLE_INVOICE, null, null);
        db.close();
    }


    public void deleteMedicine(medicines filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE, KEY_MED_ID + " = ?", new String[] { String.valueOf(filemodal.getID()) });
        db.close();
    }


    public void deleteMedicineCategory(medicinesCategory filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE_CATEGORY, KEY_MED_CAT_ID + " = ?", new String[] { String.valueOf(filemodal.getID()) });
        db.close();
    }


    public void deleteManufacturer(manufacturer filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MANUFACTURER, KEY_MANUFACTURER_ID + " = ?", new String[] { String.valueOf(filemodal.getManufacturerID()) });
        db.close();
    }



    public void deleteCustomer(customer filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, KEY_CUSTOMER_ID + " = ?", new String[] { String.valueOf(filemodal.getCustomerID()) });
        db.close();
    }

    public void deleteStore(store filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORE, KEY_STORE_ID + " = ?", new String[] { String.valueOf(filemodal.getStoreID()) });
        db.close();
    }

    public void deleteInvoice(invoice filemodal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVOICE, KEY_INVOICE_ID + " = ?", new String[] { String.valueOf(filemodal.getInvoiceID()) });
        db.close();
    }


    public void deleteMedicine(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE, KEY_MED_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }


    public void deleteMedicineCategory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE_CATEGORY, KEY_MED_CAT_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }


    public void deleteManufacturer(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MANUFACTURER, KEY_MANUFACTURER_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }



    public void deleteStore(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORE, KEY_STORE_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }


    public void deleteCustomer(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, KEY_CUSTOMER_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteInvoice(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVOICE, KEY_INVOICE_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteCategory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE_CATEGORY, KEY_MED_CAT_NAME + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }



    public int getMedicineCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_MEDICINE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getManufacturerCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_MANUFACTURER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int getCustomerCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getMedicineCategoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MEDICINE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


    public int getStoreCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


    public int getInvoiceCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_INVOICE + " GROUP BY " + KEY_INVOICE_DATE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int getOutOfStockMedicinesCount() {
        int count = 0;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String countQuery = "SELECT  * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_QUANTITY + " < '" + prefs.getString("outOfMedicineLimit", "5") + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int getExpiredMedicinesCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_EXPIRY_DATE + " < '" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }




    public List<medicinesCategory> getMedicineCategory() {
        List<medicinesCategory> contactList = new ArrayList<medicinesCategory>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE_CATEGORY;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicinesCategory filemodal = new medicinesCategory();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineCategoryName(cursor.getString(1));
                filemodal.setMedicineCategoryImage(cursor.getBlob(2));
                filemodal.setMedCatAddDate(cursor.getString(3));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<medicines> getMedicine() {
        List<medicines> contactList = new ArrayList<medicines>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicines filemodal = new medicines();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineName(cursor.getString(1));
                filemodal.setMedicineBarcode(cursor.getString(2));
                filemodal.setMedicineCategory(cursor.getString(3));
                filemodal.setMedicineCompany(cursor.getString(4));
                filemodal.setMedicineStrength(cursor.getString(5));
                filemodal.setMedicineDescription(cursor.getString(6));
                filemodal.setMedicineLocation(cursor.getString(7));
                filemodal.setMedicineQuantity(cursor.getString(8));
                filemodal.setMedicineExpiry(cursor.getString(9));
                filemodal.setMedicineSellingPrice(cursor.getString(10));
                filemodal.setMedicineImage(cursor.getBlob(11));
                filemodal.setMedicineAddDate(cursor.getString(12));
                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }




    public List<medicines> getOutOfStockMedicine() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        List<medicines> contactList = new ArrayList<medicines>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_QUANTITY + " < '" + prefs.getString("outOfMedicineLimit", "5") + "'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicines filemodal = new medicines();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineName(cursor.getString(1));
                filemodal.setMedicineBarcode(cursor.getString(2));
                filemodal.setMedicineCategory(cursor.getString(3));
                filemodal.setMedicineCompany(cursor.getString(4));
                filemodal.setMedicineStrength(cursor.getString(5));
                filemodal.setMedicineDescription(cursor.getString(6));
                filemodal.setMedicineLocation(cursor.getString(7));
                filemodal.setMedicineQuantity(cursor.getString(8));
                filemodal.setMedicineExpiry(cursor.getString(9));
                filemodal.setMedicineSellingPrice(cursor.getString(10));
                filemodal.setMedicineImage(cursor.getBlob(11));
                filemodal.setMedicineAddDate(cursor.getString(12));
                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public List<medicines> getExpiredMedicine() {
        List<medicines> contactList = new ArrayList<medicines>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_EXPIRY_DATE + " < '" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + "'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicines filemodal = new medicines();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineName(cursor.getString(1));
                filemodal.setMedicineBarcode(cursor.getString(2));
                filemodal.setMedicineCategory(cursor.getString(3));
                filemodal.setMedicineCompany(cursor.getString(4));
                filemodal.setMedicineStrength(cursor.getString(5));
                filemodal.setMedicineDescription(cursor.getString(6));
                filemodal.setMedicineLocation(cursor.getString(7));
                filemodal.setMedicineQuantity(cursor.getString(8));
                filemodal.setMedicineExpiry(cursor.getString(9));
                filemodal.setMedicineSellingPrice(cursor.getString(10));
                filemodal.setMedicineImage(cursor.getBlob(11));
                filemodal.setMedicineAddDate(cursor.getString(12));
                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }






    public List<medicines> searchMedicines(String medicine_name) {
        List<medicines> contactList = new ArrayList<medicines>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_NAME + " LIKE '" + medicine_name + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicines filemodal = new medicines();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineName(cursor.getString(1));
                filemodal.setMedicineBarcode(cursor.getString(2));
                filemodal.setMedicineCategory(cursor.getString(3));
                filemodal.setMedicineCompany(cursor.getString(4));
                filemodal.setMedicineStrength(cursor.getString(5));
                filemodal.setMedicineDescription(cursor.getString(6));
                filemodal.setMedicineLocation(cursor.getString(7));
                filemodal.setMedicineQuantity(cursor.getString(8));
                filemodal.setMedicineExpiry(cursor.getString(9));
                filemodal.setMedicineSellingPrice(cursor.getString(10));
                filemodal.setMedicineImage(cursor.getBlob(11));
                filemodal.setMedicineAddDate(cursor.getString(12));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public List<invoice> searchInvoice(String invoice_desc) {
        List<invoice> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INVOICE + " WHERE " + KEY_INVOICE_DESC + " LIKE '" + invoice_desc + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                invoice filemodal = new invoice();
                filemodal.setInvoiceID(Integer.parseInt(cursor.getString(0)));
                filemodal.setInvoiceDesc(cursor.getString(1));
                filemodal.setInvoiceDate(cursor.getString(2));
                filemodal.setInvoiceItemName(cursor.getString(3));
                filemodal.setInvoiceItemPrice("" + Float.parseFloat(cursor.getString(4)) * Float.parseFloat(cursor.getString(5)));
                filemodal.setInvoiceItemQty(cursor.getString(5));
                filemodal.setInvoicePaymentType(cursor.getString(6));
                filemodal.setInvoiceAddDate(cursor.getString(7));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }




    public List<manufacturer> searchManufacturer(String manufacturer_name) {
        List<manufacturer> contactList = new ArrayList<manufacturer>();
        String selectQuery = "SELECT * FROM " + TABLE_MANUFACTURER + " WHERE " + KEY_MANUFACTURER_NAME + " LIKE '" + manufacturer_name + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                manufacturer filemodal = new manufacturer();
                filemodal.setManufacturerID(Integer.parseInt(cursor.getString(0)));
                filemodal.setManufacturerName(cursor.getString(1));
                filemodal.setManufacturerMobile(cursor.getString(2));
                filemodal.setManufacturerAddress(cursor.getString(3));
                filemodal.setManufacturerImage(cursor.getBlob(4));
                filemodal.setManufacturerAddDate(cursor.getString(5));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<customer> searchCustomer(String customer_name) {
        List<customer> contactList = new ArrayList<customer>();
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + KEY_CUSTOMER_NAME + " LIKE '" + customer_name + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                customer filemodal = new customer();
                filemodal.setCustomerID(Integer.parseInt(cursor.getString(0)));
                filemodal.setCustomerName(cursor.getString(1));
                filemodal.setCustomerEmail(cursor.getString(2));
                filemodal.setCustomerAddress(cursor.getString(3));
                filemodal.setCustomerPhone(cursor.getString(4));
                filemodal.setCustomerImage(cursor.getBlob(5));
                filemodal.setCustomerAddDate(cursor.getString(6));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<medicines> searchOutOfStockMedicine(String medicine_name) {
        List<medicines> contactList = new ArrayList<medicines>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_QUANTITY + " < " + prefs.getString("outOfMedicineLimit", "5") + " AND " + KEY_MED_NAME + " LIKE '" + medicine_name + "%'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicines filemodal = new medicines();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineName(cursor.getString(1));
                filemodal.setMedicineBarcode(cursor.getString(2));
                filemodal.setMedicineCategory(cursor.getString(3));
                filemodal.setMedicineCompany(cursor.getString(4));
                filemodal.setMedicineStrength(cursor.getString(5));
                filemodal.setMedicineDescription(cursor.getString(6));
                filemodal.setMedicineLocation(cursor.getString(7));
                filemodal.setMedicineQuantity(cursor.getString(8));
                filemodal.setMedicineExpiry(cursor.getString(9));
                filemodal.setMedicineSellingPrice(cursor.getString(10));
                filemodal.setMedicineImage(cursor.getBlob(11));
                filemodal.setMedicineAddDate(cursor.getString(12));
                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }






    public int searchMedicineCount(String medicine_name) {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_NAME + " LIKE '" + medicine_name + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int searchInvoiceCount(String invoice_desc) {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_INVOICE + " WHERE " + KEY_INVOICE_DESC + " LIKE '" + invoice_desc + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int searchManufacturerCount(String manufacturer_name) {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_MANUFACTURER + " WHERE " + KEY_MANUFACTURER_NAME + " LIKE '" + manufacturer_name + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int searchCustomerCount(String customer_name) {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + KEY_CUSTOMER_NAME + " LIKE '" + customer_name + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int searchOutOfStockMedicineCount(String med_name) {
        int count = 0;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String countQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_QUANTITY + " < " + prefs.getString("outOfMedicineLimit", "5") + " AND " + KEY_MED_NAME + " LIKE '" + med_name + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }









    public List<manufacturer> getManufacturer() {
        List<manufacturer> contactList = new ArrayList<manufacturer>();
        String selectQuery = "SELECT * FROM " + TABLE_MANUFACTURER;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                manufacturer filemodal = new manufacturer();
                filemodal.setManufacturerID(Integer.parseInt(cursor.getString(0)));
                filemodal.setManufacturerName(cursor.getString(1));
                filemodal.setManufacturerMobile(cursor.getString(2));
                filemodal.setManufacturerAddress(cursor.getString(3));
                filemodal.setManufacturerImage(cursor.getBlob(4));
                filemodal.setManufacturerAddDate(cursor.getString(5));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }




    public List<customer> getCustomer() {
        List<customer> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                customer filemodal = new customer();
                filemodal.setCustomerID(Integer.parseInt(cursor.getString(0)));
                filemodal.setCustomerName(cursor.getString(1));
                filemodal.setCustomerEmail(cursor.getString(2));
                filemodal.setCustomerAddress(cursor.getString(3));
                filemodal.setCustomerPhone(cursor.getString(4));
                filemodal.setCustomerImage(cursor.getBlob(5));
                filemodal.setCustomerAddDate(cursor.getString(6));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<store> getStore() {
        List<store> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STORE;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                store filemodal = new store();
                filemodal.setStoreID(Integer.parseInt(cursor.getString(0)));
                filemodal.setStoreName(cursor.getString(1));
                filemodal.setStoreAddress(cursor.getString(2));
                filemodal.setStoreCity(cursor.getString(3));
                filemodal.setStorePhone(cursor.getString(4));
                filemodal.setStoreImage(cursor.getBlob(5));
                filemodal.setStoreAddDate(cursor.getString(6));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<invoice> getInvoiceList() {
        List<invoice> contactList = new ArrayList<>();
        String selectQuery = "SELECT *, SUM(" + KEY_INVOICE_ITEM_PRICE + ") FROM " + TABLE_INVOICE + " GROUP BY " + KEY_INVOICE_DATE;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                invoice filemodal = new invoice();
                filemodal.setInvoiceID(Integer.parseInt(cursor.getString(0)));
                filemodal.setInvoiceDesc(cursor.getString(1));
                filemodal.setInvoiceDate(cursor.getString(2));
                filemodal.setInvoiceItemName(cursor.getString(3));
//                filemodal.setInvoiceItemPrice(cursor.getString(4));
                filemodal.setInvoiceItemQty(cursor.getString(5));
                filemodal.setInvoicePaymentType(cursor.getString(6));
                filemodal.setInvoiceAddDate(cursor.getString(7));
                filemodal.setInvoiceItemPrice("" + Float.parseFloat(cursor.getString(9)) * Float.parseFloat(cursor.getString(5)));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<store> getStore(String id) {
        List<store> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STORE + " WHERE " + KEY_STORE_ID + " = " + id ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                store filemodal = new store();
                filemodal.setStoreID(Integer.parseInt(cursor.getString(0)));
                filemodal.setStoreName(cursor.getString(1));
                filemodal.setStoreAddress(cursor.getString(2));
                filemodal.setStoreCity(cursor.getString(3));
                filemodal.setStorePhone(cursor.getString(4));
                filemodal.setStoreImage(cursor.getBlob(5));
                filemodal.setStoreAddDate(cursor.getString(6));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public List<medicines> getMedicine(String id) {
        List<medicines> contactList = new ArrayList<medicines>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE + " WHERE " + KEY_MED_ID + " = " + id ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicines filemodal = new medicines();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineName(cursor.getString(1));
                filemodal.setMedicineBarcode(cursor.getString(2));
                filemodal.setMedicineCategory(cursor.getString(3));
                filemodal.setMedicineCompany(cursor.getString(4));
                filemodal.setMedicineStrength(cursor.getString(5));
                filemodal.setMedicineDescription(cursor.getString(6));
                filemodal.setMedicineLocation(cursor.getString(7));
                filemodal.setMedicineQuantity(cursor.getString(8));
                filemodal.setMedicineExpiry(cursor.getString(9));
                filemodal.setMedicineSellingPrice(cursor.getString(10));
                filemodal.setMedicineImage(cursor.getBlob(11));
                filemodal.setMedicineAddDate(cursor.getString(12));
                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<medicinesCategory> getMedicineCategory(String id) {
        List<medicinesCategory> contactList = new ArrayList<medicinesCategory>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE_CATEGORY + " WHERE " + KEY_MED_CAT_ID + " = " + id ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                medicinesCategory filemodal = new medicinesCategory();
                filemodal.setID(Integer.parseInt(cursor.getString(0)));
                filemodal.setMedicineCategoryName(cursor.getString(1));
                filemodal.setMedicineCategoryImage(cursor.getBlob(2));
                filemodal.setMedCatAddDate(cursor.getString(3));
                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }




    public List<manufacturer> getManufacturer(String id) {
        List<manufacturer> contactList = new ArrayList<manufacturer>();
        String selectQuery = "SELECT * FROM " + TABLE_MANUFACTURER + " WHERE " + KEY_MANUFACTURER_ID + " = " + id ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                manufacturer filemodal = new manufacturer();
                filemodal.setManufacturerID(Integer.parseInt(cursor.getString(0)));
                filemodal.setManufacturerName(cursor.getString(1));
                filemodal.setManufacturerMobile(cursor.getString(2));
                filemodal.setManufacturerAddress(cursor.getString(3));
                filemodal.setManufacturerImage(cursor.getBlob(4));
                filemodal.setManufacturerAddDate(cursor.getString(5));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }



    public List<customer> getCustomer(String id) {
        List<customer> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER+ " WHERE " + KEY_CUSTOMER_ID + " = " + id ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                customer filemodal = new customer();
                filemodal.setCustomerID(Integer.parseInt(cursor.getString(0)));
                filemodal.setCustomerName(cursor.getString(1));
                filemodal.setCustomerEmail(cursor.getString(2));
                filemodal.setCustomerAddress(cursor.getString(3));
                filemodal.setCustomerPhone(cursor.getString(4));
                filemodal.setCustomerImage(cursor.getBlob(5));
                filemodal.setCustomerAddDate(cursor.getString(6));

                contactList.add(filemodal);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


}