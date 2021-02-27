package com.uclick.technician.CompletedWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.CompletedWork.Completed_Work_Details.Completed_Work_Details_Activity;
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

public class Completed_Work_Activity extends AppCompatActivity implements Completed_Work_Adapter.onCompletedworkClick {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Completed_Work_Adapter completed_work_adapter;

    List<Completed_Work_Model> completed_work_models;

    SharedPreferences preferences;
    String technician_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedwork);

        toolbar = findViewById(R.id.completedwork_Toolbar);
        setSupportActionBar(toolbar);
        Drawable backArrow = this.getResources().getDrawable(R.drawable.white_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setTitle("Completed Works");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("db", MODE_PRIVATE);
        technician_id = preferences.getString("technician_id", null);

        recyclerView = findViewById(R.id.completedwork_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       /* //------------without service ( static form)
        completed_work_models = new ArrayList<>();
        completed_work_models.add(new Completed_Work_Model("Prashanth","Dwaraka Nagar, Vizag, test","2019-07-17","7:00 AM to 11:00AM","Completed"));
        completed_work_models.add(new Completed_Work_Model("Arjun","Dwaraka Nagar, Vizag, test","2019-07-17","7:00 AM to 11:00AM","Completed"));
        completed_work_models.add(new Completed_Work_Model("Dheeraj","Dwaraka Nagar, Vizag, test","2019-07-17","7:00 AM to 11:00AM","Completed"));
        completed_work_models.add(new Completed_Work_Model("Omkar","Dwaraka Nagar, Vizag, test","2019-07-17","7:00 AM to 11:00AM","Completed"));

        completed_work_adapter = new Completed_Work_Adapter(Completed_Work_Activity.this, completed_work_models);
        recyclerView.setAdapter(completed_work_adapter);
        // It will go to next intent--onclick
        completed_work_adapter.setCompletedworkClick(Completed_Work_Activity.this);*/

      // -------------------Completed work Service Calling-------------------
        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.worker_completed_works(technician_id);

          responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Response", "Success");

                try {
                    String responseString = new String(response.body().bytes());
                    JSONObject responseJsonObject = new JSONObject(responseString);

                    if (responseJsonObject.getString("status").equals("valid")) {
                        Log.d("Response", "Success");
                        String dataString=responseJsonObject.getString("data");
                        JSONArray dataJsonArray=new JSONArray(dataString);
                        completed_work_models=new ArrayList<>();
                        for(int i=0;i<dataJsonArray.length();i++){
                            JSONObject dataJsonObject=dataJsonArray.getJSONObject(i);
                            completed_work_models.add(new Completed_Work_Model(
                                 dataJsonObject.getString("user_name"),
                                 dataJsonObject.getString("address") ,
                                 dataJsonObject.getString("appointment_date")  ,
                                 dataJsonObject.getString("timings")  ,
                                 dataJsonObject.getString("work_status"),
                                 dataJsonObject.getString("service_order_id") ,
                                 dataJsonObject.getString("order_id") ,
                                 dataJsonObject.getString("technician_id"),
                                 dataJsonObject.getString("user_mobile"),
                                 dataJsonObject.getString("payment_status")
                            ));
                        }
                    }
                    if(completed_work_models!=null){
                        completed_work_adapter=new Completed_Work_Adapter(Completed_Work_Activity.this,completed_work_models);
                        recyclerView.setAdapter(completed_work_adapter);
                        completed_work_adapter.setCompletedworkClick(Completed_Work_Activity.this);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //-----------------------Completedwork Service Calling------------------------------------------------
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(int position) {
        String service=completed_work_models.get(position).getService_order_id();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("service_order_id",service);
        editor.apply();
        Intent completedwork_Intent = new Intent(this, Completed_Work_Details_Activity.class);
        startActivity(completedwork_Intent);
        finish();
    }
}

