package com.example.medicplus.database;

@SuppressWarnings("ALL")
public class store {
    int med_store_id;
    String med_store_name;
    String med_store_address;
    String med_store_city;
    String med_store_phone;
    byte[] med_store_image;
    private String store_add_date;


    public store(String med_store_name, String med_store_address, String med_store_city,
                 String med_store_phone, byte[] med_store_image){
        this.med_store_name = med_store_name;
        this.med_store_address = med_store_address;
        this.med_store_city = med_store_city;
        this.med_store_phone = med_store_phone;
        this.med_store_image = med_store_image;
    }

    public store(int med_store_id, String med_store_name, String med_store_address, String med_store_city,
                 String med_store_phone, byte[] med_store_image){
        this.med_store_id = med_store_id;
        this.med_store_name = med_store_name;
        this.med_store_address = med_store_address;
        this.med_store_city = med_store_city;
        this.med_store_phone = med_store_phone;
        this.med_store_image = med_store_image;
    }

    public store() {

    }

    public int getStoreID(){
        return this.med_store_id;
    }
    public void setStoreID(int id){
        this.med_store_id = id;
    }


    public String getStoreName(){
        return this.med_store_name;
    }
    public void setStoreName(String med_store_name){
        this.med_store_name = med_store_name;
    }


    public String getStoreAddress(){
        return this.med_store_address;
    }
    public void setStoreAddress(String med_store_address){
        this.med_store_address = med_store_address;
    }


    public String getStoreCity(){
        return this.med_store_city;
    }
    public void setStoreCity(String med_store_city){
        this.med_store_city = med_store_city;
    }

    public String getStorePhone(){
        return this.med_store_phone;
    }
    public void setStorePhone(String med_store_phone){
        this.med_store_phone = med_store_phone;
    }

    public byte[] getStoreImage() { return this.med_store_image; }
    public void setStoreImage(byte[] image) { this.med_store_image = image; }


    public String getStoreAddDate(){
        return this.store_add_date;
    }
    public void setStoreAddDate(String add_date){
        this.store_add_date = add_date;
    }

}