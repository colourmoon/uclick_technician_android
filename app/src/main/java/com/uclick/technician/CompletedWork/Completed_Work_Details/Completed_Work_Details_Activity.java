package com.uclick.technician.CompletedWork.Completed_Work_Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;
import com.uclick.technician.Services.JsonPlaceHolder;
import com.uclick.technician.Services.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Completed_Work_Details_Activity extends AppCompatActivity {

    TextView OrderId, AppointmentDate, ScheduleTime, Price, PaymentStatus, WorkStatus, TechnicianDetails, Total,CompletedDate;
    Toolbar toolbar;
    private String clickedButton;
    RecyclerView recyclerView;
    String order, date, time, price, payment, work, tech_details, totalAmount,completedDate;

    Completed_Work_Details_Adapter adapter;
    List<Completed_Work_Details_Model> list;

    SharedPreferences preferences;
    String service_order_id;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedwork__details);

        toolbar = findViewById(R.id.completedwork_Details_Toolbar);
        setSupportActionBar(toolbar);
        Drawable backArrow = this.getResources().getDrawable(R.drawable.white_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setTitle("Completed Work");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-------------SharedPreference--------
        preferences = getSharedPreferences("db", MODE_PRIVATE);
        service_order_id = preferences.getString("service_order_id", null);

        OrderId = findViewById(R.id.orderId_view_cw);
        AppointmentDate = findViewById(R.id.appointmentDate_view_cw);
        ScheduleTime = findViewById(R.id.scheduleTime_view_cw);
        Price = findViewById(R.id.price_view_cw);
        PaymentStatus = findViewById(R.id.paymentStatus_view);
        WorkStatus = findViewById(R.id.workstatus_view_cw);
        TechnicianDetails = findViewById(R.id.technicianDetails_view_cw);
        Total = findViewById(R.id.total_view_cw);
        CompletedDate=findViewById(R.id.completeddate_cw_view);


        recyclerView=findViewById(R.id.OrderDetails_RecyclerView_cw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CompletedworkView();

    }

    //-----------------completedwork inside screen service calling-------------
    public void CompletedworkView() {
        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.completed_work_view(service_order_id);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(responseString);

                    if (jsonObject.getString("status").equals("valid")) {
                        String dataString = jsonObject.getString("data");
                        JSONObject dataJSONObject = new JSONObject(dataString);

                        //To Get
                        order = dataJSONObject.getString("order_id");
                        date = dataJSONObject.getString("appointment_date");
                        time = dataJSONObject.getString("timings");
                        price = dataJSONObject.getString("price");
                        payment = dataJSONObject.getString("payment_status");
                        work = dataJSONObject.getString("work_status");
                        tech_details = dataJSONObject.getString("technician_details");
                        totalAmount = dataJSONObject.getString("total_amount_to_pay");
                        completedDate=dataJSONObject.getString("completed_date");

                        // To Display
                        OrderId.setText(order);
                        AppointmentDate.setText(date);
                        ScheduleTime.setText(time);
                        Price.setText(price);
                        PaymentStatus.setText(payment);
                        WorkStatus.setText(work);
                        TechnicianDetails.setText(tech_details);
                        Total.setText(totalAmount);
                        CompletedDate.setText(completedDate);

                        JSONArray jsonArray = dataJSONObject.getJSONArray("service_list");

                        list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject inner_Object = jsonArray.getJSONObject(i);
                            list.add(new Completed_Work_Details_Model(
                                    inner_Object.getString("service_name"),
                                    inner_Object.getString("amount")
                            ));
                        }
                    }
                    adapter = new Completed_Work_Details_Adapter(Completed_Work_Details_Activity.this, list);
                    recyclerView.setAdapter(adapter);

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
    }
    //--------------completedwork inside screen service calling--------

    @Override
    public boolean onSupportNavigateUp() {
        Intent backPressed = new Intent(Completed_Work_Details_Activity.this, com.uclick.technician.CompletedWork.Completed_Work_Activity.class);
        startActivity(backPressed);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent backPressed = new Intent(Completed_Work_Details_Activity.this, com.uclick.technician.CompletedWork.Completed_Work_Activity.class);
        startActivity(backPressed);
        finish();
        super.onBackPressed();
    }
}
