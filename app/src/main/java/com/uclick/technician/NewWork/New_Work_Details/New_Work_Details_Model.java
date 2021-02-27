package com.uclick.technician.NewWork.New_Work_Details;

public class New_Work_Details_Model {

    private String order_id;
    private String appointment_date;
    private String timings;
    private String price;
    private String payment_status;
    private String work_status;
    private String address;
    private String service_order_id;
    private String payment_mode;
    private String technician_id;
    private String user_name;


    public New_Work_Details_Model(String order_id, String appointment_date, String timings, String price, String payment_status, String work_status, String address, String service_order_id, String payment_mode, String technician_id, String user_name) {
        this.order_id = order_id;
        this.appointment_date = appointment_date;
        this.timings = timings;
        this.price = price;
        this.payment_status = payment_status;
        this.work_status = work_status;
        this.address = address;
        this.service_order_id = service_order_id;
        this.payment_mode = payment_mode;
        this.technician_id = technician_id;
        this.user_name = user_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public String getTimings() {
        return timings;
    }

    public String getPrice() {
        return price;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getWork_status() {
        return work_status;
    }

    public String getAddress() {
        return address;
    }

    public String getService_order_id() {
        return service_order_id;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getTechnician_id() {
        return technician_id;
    }

    public String getUser_name() {
        return user_name;
    }
}
