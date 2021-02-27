package com.uclick.technician.NewWork.New_Work_Details;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.Home.Home_Activity;
import com.uclick.technician.NewWork.New_Work_Activity;
import com.uclick.technician.R;
import com.uclick.technician.Services.JsonPlaceHolder;
import com.uclick.technician.Services.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New_Work_Details_Activity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView OrderId, AppointmentDate, ScheduleTime, Price, PaymentStatus, WorkStatus, TechnicianDetails, Total, FinalAmount;
    TextView category_txtview, subcategory_txtview; //custom add_btn
    TextView orderId_view, orderIdview_view, successmsg_view; //custom order_success
    Button home_btn; //custom order_success
    ImageView work_success;
    String order, date, time, price, payment, work, tech_details, totalAmount, finalAmount;
    Button add;
    Button submit_btn, cmplt_submit_btn;
    Spinner category, subcategory;
    RecyclerView recyclerView;
    Toolbar toolbar;

    List<Spinner_Category_model> category_spinner;
    List<Spinner_Service_Model> subcategory_spinner;
    // To Display names in category list
    ArrayList<String> category_arrayList;
    ArrayList<String> subcategory_arrayList;

    New_Work_Details_Adapter adapter;
    List<New_Work_Details_Model> modelList;
    //----service order list--model class----
    List<Service_Order_List_Model> serviceOrderModelList;
    // String categoryID;
    SharedPreferences preferences;
    String service_order_id, service_id, category_id;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newwork__details_);

        toolbar = findViewById(R.id.newwork_Details_Toolbar);
        setSupportActionBar(toolbar);
        Drawable backArrow = this.getResources().getDrawable(R.drawable.white_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setTitle("New Work");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(New_Work_Details_Activity.this);


        //-------------SharedPreference-------
        preferences = getSharedPreferences("db", MODE_PRIVATE);
        service_order_id = preferences.getString("service_order_id", null);
        category_id = preferences.getString("category_id", "null");
        service_id = preferences.getString("service_id", "null");

        OrderId = findViewById(R.id.orderId_view);
        AppointmentDate = findViewById(R.id.appointmentDate_view);
        ScheduleTime = findViewById(R.id.scheduleTime_view);
        Price = findViewById(R.id.price_view);
        PaymentStatus = findViewById(R.id.paymentStatus_view);
        WorkStatus = findViewById(R.id.workStatus_view);
        TechnicianDetails = findViewById(R.id.technicianDetails_view);
        Total = findViewById(R.id.total_view);
        FinalAmount = findViewById(R.id.finalAmount_view);
        add = findViewById(R.id.add_Btn);
        cmplt_submit_btn = findViewById(R.id.cmplt_submit_btn);

        modelList = new ArrayList<>();
        recyclerView = findViewById(R.id.OrderDetails_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //-----static representation form-------
        //modelList.add(new New_Work_Details_Model("Fuse Replacement"));
        // modelList.add(new New_Work_Details_Model("Bulb Replacement"));

        //calling methods()
        Workview();
        ServiceOrderList();


        //-------cmplt payment service call-----------------
        cmplt_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(New_Work_Details_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.ordersuccess_custom_dialog, null);
                orderId_view = mView.findViewById(R.id.custom_orderId);
                orderIdview_view = mView.findViewById(R.id.custom_orderId_view);
                work_success = mView.findViewById(R.id.custom_image);
                successmsg_view = mView.findViewById(R.id.custom_success);
                home_btn = mView.findViewById(R.id.custome_home);
                orderIdview_view.setText(order);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                home_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cmplt_submit_intent = new Intent(New_Work_Details_Activity.this, Home_Activity.class);
                        startActivity(cmplt_submit_intent);
                    }
                });

                //--------service call----
                JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
                Call<ResponseBody> responseBodyCall = jsonPlaceHolder.complete_payment(service_order_id);

                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String responseString = new String(response.body().bytes());
                            JSONObject jsonObject = new JSONObject(responseString);

                            if (jsonObject.getString("status").equals("valid")) {
                                String message = jsonObject.getString("message");

                                Toast.makeText(New_Work_Details_Activity.this, "" + message, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(New_Work_Details_Activity.this, "Failed! Service not completed", Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                alertDialog.show();
                // CategoryListSpinner();
                //ServiceListSpinner();
            }
        });
        //-------------------------------cmplt payment service call---------------------------------
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent onBack = new Intent(New_Work_Details_Activity.this, New_Work_Activity.class);
        startActivity(onBack);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent backPressed = new Intent(New_Work_Details_Activity.this, New_Work_Activity.class);
        startActivity(backPressed);
        finish();
        super.onBackPressed();
    }

    public void AddBtn(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(New_Work_Details_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.addbtn_custom_dialog, null);
        category_txtview = mView.findViewById(R.id.category_spinner);
        subcategory_txtview = mView.findViewById(R.id.sub_category_spinner);
        category = mView.findViewById(R.id.spinner_1);
        subcategory = mView.findViewById(R.id.spinner_2);
        submit_btn = mView.findViewById(R.id.submitbtn_add);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    Spinner_Category_model category_Model = category_spinner.get(i - 1);
                    String categoryId = category_Model.getCategory_id();
                    String categoryName = category_Model.getCategory_name();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("category_id", categoryId);
                    editor.putString("category_name", categoryName);
                    editor.apply();
                    ServiceListSpinner();
                    // Toast.makeText(New_Work_Details_Activity.this, "" + categoryId, Toast.LENGTH_LONG).show();
                    // category_id = categoryID;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    Spinner_Service_Model service_Model = subcategory_spinner.get(i - 1);
                    String serviceId = service_Model.getService_id();
                    String serviceName = service_Model.getService_name();

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("service_id", serviceId);
                    editor.putString("service_name", serviceName);
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //--------------------------add service item----service calling-------===---------------------->
        submit_btn.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            if (category.getSelectedItem().toString().trim().equals("select category")) {
                progressDialog.dismiss();
                Toast.makeText(New_Work_Details_Activity.this, "Please Select category", Toast.LENGTH_SHORT).show();
                return;
            } else if (category.getSelectedItem().toString().trim().equals("select service")) {
                progressDialog.dismiss();
                Toast.makeText(New_Work_Details_Activity.this, "Please Select SubCategory", Toast.LENGTH_SHORT).show();
                return;
            }
            //<-----------------Add service item service calling-----------------------------
            service_order_id = preferences.getString("service_order_id", null);
            category_id = preferences.getString("category_id", "null");
            service_id = preferences.getString("service_id", "null");
            Map<String, String> add_service_Map = new HashMap<>();
            add_service_Map.put("service_order_id", service_order_id);
            add_service_Map.put("service_id", service_id);

            JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
            Call<ResponseBody> responseBodyCall = jsonPlaceHolder.add_service(add_service_Map);

            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = new String(response.body().bytes());
                        JSONObject jsonObject = new JSONObject(responseString);

                        if (jsonObject.getString("status").equals("valid")) {
                            String message = jsonObject.getString("message");
                            progressDialog.dismiss();
                            ServiceOrderList();
                            Workview();

                            Toast.makeText(New_Work_Details_Activity.this, "Sucess! Service items added", Toast.LENGTH_LONG).show();
                        } else if(jsonObject.getString("status").equals("invalid")) {
                            progressDialog.dismiss();
                            Toast.makeText(New_Work_Details_Activity.this, "Failed! Items not added", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        });
        alertDialog.show();
        CategoryListSpinner();
        ServiceListSpinner();
    }

    // <-------------------Newworks Inside screen Service Calling-----------------------------------
    public void Workview() {
        JsonPlaceHolder jsonPlaceHolder1 = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall1 = jsonPlaceHolder1.work_view(service_order_id);

        responseBodyCall1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(responseString);

                    if (jsonObject.getString("status").equals("valid")) {
                        String dataString = jsonObject.getString("data");
                        JSONObject dataJSONObject = new JSONObject(dataString);

                        // To Get
                        order = dataJSONObject.getString("order_id");
                        date = dataJSONObject.getString("appointment_date");
                        time = dataJSONObject.getString("timings");
                        price = dataJSONObject.getString("price");
                        payment = dataJSONObject.getString("payment_status");
                        work = dataJSONObject.getString("work_status");
                        tech_details = dataJSONObject.getString("technician_details");
                        totalAmount = dataJSONObject.getString("total_amount");
                        finalAmount = dataJSONObject.getString("final_amount");

                        // Toast.makeText(New_Work_Details_Activity.this,""+order,Toast.LENGTH_LONG).show();

                        // To Display
                        OrderId.setText(order);
                        AppointmentDate.setText(date);
                        ScheduleTime.setText(time);
                        Price.setText(price);
                        PaymentStatus.setText(payment);
                        WorkStatus.setText(work);
                        TechnicianDetails.setText(tech_details);
                        Total.setText(totalAmount);
                        FinalAmount.setText(finalAmount);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        //-------------------------newwork Inside screen Service Calling--------------------------->
    }

    //<-----------------------(category List spinner method with service) -----------------------
    public void CategoryListSpinner() {
        category_spinner = new ArrayList<>();
        category_arrayList = new ArrayList<>();
        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.category_list();

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(responseString);

                    if (jsonObject.getString("status").equals("valid")) {
                        Log.d("relationSpinnerResponse", "success ");
                        JSONArray dataString = jsonObject.getJSONArray("data");
                        //JSONObject dataJSONObject = jsonObject.getJSONArray(dataString);
                        category_arrayList.add("select category");
                        for (int i = 0; i < dataString.length(); i++) {
                            JSONObject inner_JsonObject = dataString.getJSONObject(i);
                            String categoryId = inner_JsonObject.getString("category_id");
                            String categoryName = inner_JsonObject.getString("name");

                            category_spinner.add(new Spinner_Category_model(categoryId, categoryName));
                            // To Display names in category list
                            category_arrayList.add(categoryName);
                        }

                    }
                    ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(New_Work_Details_Activity.this, R.layout.spinner_textview, category_arrayList);
                    categoryAdapter.setDropDownViewResource(R.layout.spinner_textview);
                    category.setAdapter(categoryAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //------------------------(category List spinner method with service) ------------------------->

    //-----------------------(service List spinner method with service)-----------------------------
    public void ServiceListSpinner() {
        category_id = preferences.getString("category_id", "null");
        subcategory_spinner = new ArrayList<>();
        subcategory_arrayList = new ArrayList<>();
        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.service_list(category_id);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(responseString);

                    if (jsonObject.getString("status").equals("valid")) {
                        JSONArray dataString = jsonObject.getJSONArray("data");

                        subcategory_arrayList.add("select service");
                        for (int i = 0; i < dataString.length(); i++) {
                            JSONObject inner_JsonObject = dataString.getJSONObject(i);
                            String serviceId = inner_JsonObject.getString("service_id");
                            String serviceName = inner_JsonObject.getString("service_name");

                            subcategory_spinner.add(new Spinner_Service_Model(serviceId, serviceName));
                            // To Display names in service list
                            subcategory_arrayList.add(serviceName);
                        }
                        //  Toast.makeText(New_Work_Details_Activity.this,"",Toast.LENGTH_LONG).show();
                    }
                    ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(New_Work_Details_Activity.this, R.layout.spinner_textview, subcategory_arrayList);
                    serviceAdapter.setDropDownViewResource(R.layout.spinner_textview);
                    subcategory.setAdapter(serviceAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //-------------------------(service List spinner method with service) ------------------------->

    //<------------------------(Service Order List method with service) ----------------------------
    public void ServiceOrderList() {

        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.service_order_list(service_order_id);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = null;
                    if (response.body() != null) {
                        responseString = new String(response.body().bytes());

                        JSONObject jsonObject = new JSONObject(responseString);

                        if (jsonObject.getString("status").equals("valid")) {
                            String dataString = jsonObject.getString("data");

                            JSONArray jsonArray = new JSONArray(dataString);
                            serviceOrderModelList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dataJSONOject = jsonArray.getJSONObject(i);
                                serviceOrderModelList.add(new Service_Order_List_Model(
                                        dataJSONOject.getString("service_name"),
                                        dataJSONOject.getString("amount")
                                ));
                            }
                        }
                        adapter = new New_Work_Details_Adapter(New_Work_Details_Activity.this, serviceOrderModelList);
                        recyclerView.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //------------------------(Service Order List method with service) ---------------------------->


}










