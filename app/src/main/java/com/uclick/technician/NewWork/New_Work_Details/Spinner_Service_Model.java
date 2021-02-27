package com.uclick.technician.NewWork.New_Work_Details;

public class Spinner_Service_Model {

    private String Service_id;
    private String Service_name;

    public Spinner_Service_Model(String service_id, String service_name) {
        Service_id = service_id;
        Service_name = service_name;
    }

    public String getService_id() {
        return Service_id;
    }

    public String getService_name() {
        return Service_name;
    }
}
