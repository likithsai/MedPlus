package com.example.medicplus.database;

public class TotalSalesChartHandler {

    String sales_month;
    float total_Sales;

    public TotalSalesChartHandler(String month, float sales) {
        this.sales_month = month;
        this.total_Sales = sales;
    }

    public TotalSalesChartHandler() {

    }

    public String getSalesMonth() { return this.sales_month; }
    public void setSalesMonth(String month) { this.sales_month = month; }

    public float getTotalSales() { return this.total_Sales; }
    public void setTotalSales(float sales) { this.total_Sales = sales; }

}
