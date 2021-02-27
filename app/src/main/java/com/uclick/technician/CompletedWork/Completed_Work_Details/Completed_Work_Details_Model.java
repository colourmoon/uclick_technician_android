package com.uclick.technician.CompletedWork.Completed_Work_Details;

public class Completed_Work_Details_Model {
    private String serviceName;
    private String serviceAmount;

    public Completed_Work_Details_Model(String serviceName, String serviceAmount) {
        this.serviceName = serviceName;
        this.serviceAmount = serviceAmount;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }
}

