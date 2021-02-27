package com.uclick.technician.NewWork.New_Work_Details;

public class Spinner_Category_model {
    String Category_id;
    String Category_name;

    public Spinner_Category_model(String category_id, String category_name) {
        Category_id = category_id;
        Category_name = category_name;
    }

    public String getCategory_id() {
        return Category_id;
    }

    public String getCategory_name() {
        return Category_name;
    }
}
