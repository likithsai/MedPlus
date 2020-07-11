package com.example.medicplus.database;

@SuppressWarnings("ALL")
public class customer {
    int med_customer_id;
    String med_customer_name;
    String med_customer_email;
    String med_customer_address;
    String med_customer_phone;
    byte[] med_image;
    String customer_add_date;


    public customer(String med_customer_name, String med_customer_email, String med_customer_address,
                    String med_customer_phone, byte[] med_image){
        this.med_customer_name = med_customer_name;
        this.med_customer_email = med_customer_email;
        this.med_customer_address = med_customer_address;
        this.med_customer_phone = med_customer_phone;
        this.med_image = med_image;
    }


    public customer(int med_customer_id, String med_customer_name, String med_customer_email, String med_customer_address,
                    String med_customer_phone, byte[] med_image){
        this.med_customer_id = med_customer_id;
        this.med_customer_name = med_customer_name;
        this.med_customer_email = med_customer_email;
        this.med_customer_address = med_customer_address;
        this.med_customer_phone = med_customer_phone;
        this.med_image = med_image;
    }

    public customer() {

    }

    public int getCustomerID(){
        return this.med_customer_id;
    }
    public void setCustomerID(int id){
        this.med_customer_id = id;
    }


    public String getCustomerName(){
        return this.med_customer_name;
    }
    public void setCustomerName(String med_customer_name){
        this.med_customer_name = med_customer_name;
    }


    public String getCustomerEmail(){
        return this.med_customer_email;
    }
    public void setCustomerEmail(String med_customer_email){
        this.med_customer_email = med_customer_email;
    }


    public String getCustomerAddress(){
        return this.med_customer_address;
    }
    public void setCustomerAddress(String med_customer_address){
        this.med_customer_address = med_customer_address;
    }


    public String getCustomerPhone(){
        return this.med_customer_phone;
    }
    public void setCustomerPhone(String med_customer_phone){
        this.med_customer_phone = med_customer_phone;
    }

    public byte[] getCustomerImage() { return this.med_image; }
    public void setCustomerImage(byte[] image) { this.med_image = image; }


    public String getCustomerAddDate(){
        return this.customer_add_date;
    }
    public void setCustomerAddDate(String add_date){
        this.customer_add_date = add_date;
    }

}