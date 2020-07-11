package com.example.medicplus.database;

@SuppressWarnings("ALL")
public class invoice {
    int invoice_id;
    String invoice_name;
    String invoice_desc;
    String invoice_date;
    String invoice_item_name;
    String invoice_item_price;
    String invoice_item_qty;
    String invoice_item_payment_type;
    String invoice_add_date;


    public invoice(String invoice_desc, String invoice_date, String invoice_item_name,
                   String invoice_item_price, String invoice_item_qty, String invoice_item_payment_type) {
        this.invoice_desc = invoice_desc;
        this.invoice_date = invoice_date;
        this.invoice_item_name = invoice_item_name;
        this.invoice_item_price = invoice_item_price;
        this.invoice_item_qty = invoice_item_qty;
        this.invoice_item_payment_type = invoice_item_payment_type;
    }

    public invoice() {

    }


    public int getInvoiceID(){
        return this.invoice_id;
    }
    public void setInvoiceID(int id){
        this.invoice_id = id;
    }


    public String getInvoiceDate(){
        return this.invoice_date;
    }
    public void setInvoiceDate(String invoice_date){
        this.invoice_date = invoice_date;
    }


    public String getInvoiceName(){
        return this.invoice_name;
    }
    public void setInvoiceName(String invoice_name){
        this.invoice_name = invoice_name;
    }


    public String getInvoiceDesc(){
        return this.invoice_desc;
    }
    public void setInvoiceDesc(String invoice_desc){
        this.invoice_desc = invoice_desc;
    }

    public String getInvoiceItemName(){
        return this.invoice_item_name;
    }
    public void setInvoiceItemName(String invoice_item_name){
        this.invoice_item_name = invoice_item_name;
    }


    public String getInvoiceItemPrice(){
        return this.invoice_item_price;
    }
    public void setInvoiceItemPrice(String invoice_item_price){
        this.invoice_item_price = invoice_item_price;
    }


    public String getInvoiceItemQty(){
        return this.invoice_item_qty;
    }
    public void setInvoiceItemQty(String invoice_item_qty){
        this.invoice_item_qty = invoice_item_qty;
    }


    public String getInvoiceAddDate(){
        return this.invoice_add_date;
    }
    public void setInvoiceAddDate(String invoice_add_date){
        this.invoice_add_date = invoice_add_date;
    }


    public String getInvoicePaymentType(){
        return this.invoice_item_payment_type;
    }
    public void setInvoicePaymentType(String invoice_payment_type){
        this.invoice_item_payment_type = invoice_payment_type;
    }

}