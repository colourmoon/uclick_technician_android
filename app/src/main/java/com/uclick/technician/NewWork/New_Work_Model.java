package com.uclick.technician.NewWork;

public class New_Work_Model {
    private String user_name;
    private String address;
    private String appointment_date;
    private String timings;
    private String work_status;
    private String service_order_id;
    private String technician_id;
    private String order_id;
    private String mobile_number;

    public New_Work_Model(String user_name, String address, String appointment_date, String timings, String work_status, String service_order_id, String technician_id, String order_id, String mobile_number) {
        this.user_name = user_name;
        this.address = address;
        this.appointment_date = appointment_date;
        this.timings = timings;
        this.work_status = work_status;
        this.service_order_id = service_order_id;
        this.technician_id = technician_id;
        this.order_id = order_id;
        this.mobile_number = mobile_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getAddress() {
        return address;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public String getTimings() {
        return timings;
    }

    public String getWork_status() {
        return work_status;
    }

    public String getService_order_id() {
        return service_order_id;
    }

    public String getTechnician_id() {
        return technician_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }
}
