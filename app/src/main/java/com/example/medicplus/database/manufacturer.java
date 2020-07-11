package com.example.medicplus.database;

@SuppressWarnings("ALL")
public class manufacturer {
    int med_manufacturer_id;
    String med_manufacturer_name;
    String med_manufacturer_mobile;
    String med_manufacturer_address;
    byte[] med_manufacturer_image;
    String manufacturer_add_date;


    public manufacturer(String manufacturer_name, String manufacturer_mobile, String manufacturer_address, byte[] manufacturer_image){
        this.med_manufacturer_name = manufacturer_name;
        this.med_manufacturer_mobile = manufacturer_mobile;
        this.med_manufacturer_address = manufacturer_address;
        this.med_manufacturer_image = manufacturer_image;
    }

    public manufacturer(int manufacturer_id, String manufacturer_name, String manufacturer_mobile, String manufacturer_address, byte[] manufacturer_image){
        this.med_manufacturer_id = manufacturer_id;
        this.med_manufacturer_name = manufacturer_name;
        this.med_manufacturer_mobile = manufacturer_mobile;
        this.med_manufacturer_address = manufacturer_address;
        this.med_manufacturer_image = manufacturer_image;
    }

    public manufacturer() {

    }

    public int getManufacturerID(){
        return this.med_manufacturer_id;
    }
    public void setManufacturerID(int id){
        this.med_manufacturer_id = id;
    }

    public String getManufacturerName(){
        return this.med_manufacturer_name;
    }
    public void setManufacturerName(String manufacturer_name){
        this.med_manufacturer_name = manufacturer_name;
    }

    public String getManufacturerMobile(){
        return this.med_manufacturer_mobile;
    }
    public void setManufacturerMobile(String manufacturer_mobile){
        this.med_manufacturer_mobile = manufacturer_mobile;
    }

    public String getManufacturerAddress(){
        return this.med_manufacturer_address;
    }
    public void setManufacturerAddress(String manufacturer_address){
        this.med_manufacturer_address = manufacturer_address;
    }

    public byte[] getManufacturerImage() { return this.med_manufacturer_image; }
    public void setManufacturerImage(byte[] image) { this.med_manufacturer_image = image; }


    public String getManufacturerAddDate(){
        return this.manufacturer_add_date;
    }
    public void setManufacturerAddDate(String add_date){
        this.manufacturer_add_date = add_date;
    }
}