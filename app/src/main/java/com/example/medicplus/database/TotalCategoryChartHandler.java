package com.example.medicplus.database;

public class TotalCategoryChartHandler {

    String category_name;
    float category_value;

    public TotalCategoryChartHandler(String month, float sales) {
        this.category_name = month;
        this.category_value = sales;
    }

    public TotalCategoryChartHandler() {

    }

    public String getCategoryName() { return this.category_name; }
    public void setCategoryName(String month) { this.category_name = month; }

    public float getCategoryValue() { return this.category_value; }
    public void setCategoryValue(float sales) { this.category_value = sales; }

}
