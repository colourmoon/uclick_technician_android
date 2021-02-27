package com.uclick.technician.NewWork.New_Work_Details;

public class Service_Order_List_Model {
    private String service_name;
    private String amount;

    public Service_Order_List_Model(String service_name, String amount) {
        this.service_name = service_name;
        this.amount = amount;
    }

    public String getService_name() {
        return service_name;
    }

    public String getAmount() {
        return amount;
    }
}
