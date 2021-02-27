package com.uclick.technician.Services;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolder {
    public final String ROOT_URL = "http://uclick.co.in/api/worker/";

    @FormUrlEncoded
    @POST("worker_login")
    Call<ResponseBody> postLogin(@FieldMap Map<String, String> login);

    //--------------------------New work-----------------------
    @FormUrlEncoded
    @POST("worker_new_works")
    Call<ResponseBody> worker_new_works(@Field("technician_id") String technicianId);

    @FormUrlEncoded
    @POST("work_view")
    Call<ResponseBody> work_view(@Field("service_order_id") String service_order_id);

    //--------------category spinner list---------------------
    @GET("work_view/category_list")
    Call<ResponseBody> category_list();

    //--------service spinner list (subcategory)---------------------------
    @FormUrlEncoded
    @POST("work_view/service_list")
    Call<ResponseBody> service_list(@Field("category_id") String category_id);

    //-----------order details--(submit--cat and subcategory)-------------
    @FormUrlEncoded
    @POST("work_view/service_order_list/")
    Call<ResponseBody> service_order_list(@Field("service_order_id") String service_order_id);


    //----------------Completed Work-------------------------
    @FormUrlEncoded
    @POST("worker_completed_works/")
    Call<ResponseBody> worker_completed_works(@Field("technician_id") String technicianId);

    //-------------completework inside screen---------
    @FormUrlEncoded
    @POST("completed_work_view")
    Call<ResponseBody> completed_work_view(@Field("service_order_id") String service_order_id);


    //-------------Settings-----------------------------------
    @FormUrlEncoded
    @POST("update_password")
    Call<ResponseBody> update_password(@FieldMap Map<String, String> update_password);

    //----------------Forgot password----------------------
    @FormUrlEncoded
    @POST("forgot_password")
    Call<ResponseBody> forgot_password(@Field("mobile") String mobile);

    //-------------------add service------------
    @FormUrlEncoded
    @POST("add_service")
    Call<ResponseBody> add_service(@FieldMap Map<String, String> add_service_Map);

    //-----------------complete payment service call---------------
    @FormUrlEncoded
    @POST("complete_payment")
    Call<ResponseBody> complete_payment(@Field("service_order_id") String service_order_id);
}
