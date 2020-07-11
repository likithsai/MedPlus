package com.example.medicplus.database;

@SuppressWarnings("ALL")
public class medicinesCategory {
    int med_cat_id;
    String med_cat_name;
    String med_cat_add_date;
    byte[] med_cat_image;


    public medicinesCategory(String med_name){ this.med_cat_name = med_name; }

    public medicinesCategory(int cat_id, String cat_name, byte[] cat_image) {
        this.med_cat_id = cat_id;
        this.med_cat_name = cat_name;
        this.med_cat_image = cat_image;
    }

    public medicinesCategory() {

    }

    public medicinesCategory(String cat_name, byte[] cat_image) {
        this.med_cat_name = cat_name;
        this.med_cat_image = cat_image;
    }

    public int getID(){
        return this.med_cat_id;
    }
    public void setID(int id){
        this.med_cat_id = id;
    }

    public String getMedicineCategoryName(){
        return this.med_cat_name;
    }
    public void setMedicineCategoryName(String cat_name){
        this.med_cat_name = cat_name;
    }

    public byte[] getMedicineCategoryImage() { return this.med_cat_image; }
    public void setMedicineCategoryImage(byte[] cat_image) { this.med_cat_image = cat_image; }

    public String getMedCatAddDate(){
        return this.med_cat_add_date;
    }
    public void setMedCatAddDate(String add_date){
        this.med_cat_add_date = add_date;
    }
}