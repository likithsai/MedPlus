package com.example.medicplus.database;

@SuppressWarnings("ALL")

public class medicines {
    int med_id;
    String med_name;
    String med_barcode;
    String med_category;
    String med_company;
    String med_strength;
    String med_description;
    String med_location;
    String med_quantity;
    String med_expiry_date;
    String med_selling_price;
    byte[] med_image;
    String med_add_date;



    public medicines(String med_name, String med_barcode, String med_category, String med_company,
                     String med_strength, String med_description, String med_location, String med_quantity,
                     String med_expiry_date, String med_selling_price, byte[] med_image){
        this.med_name = med_name;
        this.med_barcode = med_barcode;
        this.med_category = med_category;
        this.med_company = med_company;
        this.med_strength = med_strength;
        this.med_description = med_description;
        this.med_location = med_location;
        this.med_quantity = med_quantity;
        this.med_expiry_date = med_expiry_date;
        this.med_selling_price = med_selling_price;
        this.med_image = med_image;
    }

    public medicines(int med_id, String med_name, String med_barcode, String med_category, String med_company,
                     String med_strength, String med_description, String med_location, String med_quantity,
                     String med_expiry_date, String med_selling_price, byte[] med_image){
        this.med_id = med_id;
        this.med_name = med_name;
        this.med_barcode = med_barcode;
        this.med_category = med_category;
        this.med_company = med_company;
        this.med_strength = med_strength;
        this.med_description = med_description;
        this.med_location = med_location;
        this.med_quantity = med_quantity;
        this.med_expiry_date = med_expiry_date;
        this.med_selling_price = med_selling_price;
        this.med_image = med_image;
    }


    public medicines(String med_name, String med_expiry_date, String med_selling_price, String med_quantity) {
        this.med_name = med_name;
        this.med_expiry_date = med_expiry_date;
        this.med_selling_price = med_selling_price;
        this.med_quantity = med_quantity;
    }



    public medicines() {

    }


    public int getID(){
        return this.med_id;
    }
    public void setID(int id){
        this.med_id = id;
    }

    public String getMedicineName(){
        return this.med_name;
    }
    public void setMedicineName(String med_name){
        this.med_name = med_name;
    }

    public String getMedicineBarcode(){
        return this.med_barcode;
    }
    public void setMedicineBarcode(String barcode){ this.med_barcode = barcode; }

    public String getMedicineCategory(){
        return this.med_category;
    }
    public void setMedicineCategory(String med_category){ this.med_category = med_category; }

    public String getMedicineCompany(){
        return this.med_company;
    }
    public void setMedicineCompany(String med_company){ this.med_company = med_company; }

    public String getMedicineStrength(){
        return this.med_strength;
    }
    public void setMedicineStrength(String med_strength){ this.med_strength = med_strength; }

    public String getMedicineDescription(){
        return this.med_description;
    }
    public void setMedicineDescription(String med_description){ this.med_description = med_description; }

    public String getMedicineQuantity(){ return this.med_quantity; }
    public void setMedicineQuantity(String med_quantity){ this.med_quantity = med_quantity; }

    public String getMedicineLocation(){
        return this.med_location;
    }
    public void setMedicineLocation(String med_location){ this.med_location = med_location; }

    public String getMedicineExpiry(){ return this.med_expiry_date; }
    public void setMedicineExpiry(String med_expiry){ this.med_expiry_date = med_expiry; }

    public String getMedicineSellingPrice(){
        return this.med_selling_price;
    }
    public void setMedicineSellingPrice(String med_selling_price){ this.med_selling_price = med_selling_price; }

    public byte[] getMedicineImage() { return this.med_image; }
    public void setMedicineImage(byte[] image) { this.med_image = image; }

    public String getMedicineAddDate(){
        return this.med_add_date;
    }
    public void setMedicineAddDate(String add_date){
        this.med_add_date = add_date;
    }
}