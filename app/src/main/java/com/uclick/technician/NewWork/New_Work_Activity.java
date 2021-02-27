package com.uclick.technician.NewWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.NewWork.New_Work_Details.New_Work_Details_Activity;
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

public class New_Work_Activity extends AppCompatActivity implements New_Work_Adapter.onNewworkClick {

    Toolbar toolbar;
    RecyclerView recyclerView;
    New_Work_Adapter new_work_adapter;

    List<New_Work_Model> new_work_modelList;

    SharedPreferences preferences;
    String technician_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newwork_);

        toolbar = findViewById(R.id.newwork_Toolbar);
        setSupportActionBar(toolbar);
        Drawable backArrow = this.getResources().getDrawable(R.drawable.white_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setTitle("New Works");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("db", MODE_PRIVATE);
        technician_id = preferences.getString("technician_id", null);

        recyclerView = findViewById(R.id.newwork_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // -------------------New work screen Service Calling-------------------
        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.worker_new_works(technician_id);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseString = new String(response.body().bytes());
                    JSONObject responseJsonObject = new JSONObject(responseString);

                    if (responseJsonObject.getString("status").equals("valid"))
                    {
                        String dataString = responseJsonObject.getString("data");
                        JSONArray dataJsonArray = new JSONArray(dataString);
                        new_work_modelList = new ArrayList<>();
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject dataJsonObject = dataJsonArray.getJSONObject(i);
                            new_work_modelList.add(new New_Work_Model(
                                    dataJsonObject.getString("user_name"),
                                    dataJsonObject.getString("address"),
                                    dataJsonObject.getString("appointment_date"),
                                    dataJsonObject.getString("timings"),
                                    dataJsonObject.getString("work_status"),
                                    dataJsonObject.getString("service_order_id"),
                                    dataJsonObject.getString("order_id"),
                                    dataJsonObject.getString("technician_id"),
                                    dataJsonObject.getString("user_mobile")
                            ));
                        }
                        //recyclerView.addItemDecoration(new DividerItemDecoration(New_Work_Activity.this, DividerItemDecoration.VERTICAL));
                    }

                    if (new_work_modelList!=null){
                        new_work_adapter = new New_Work_Adapter(New_Work_Activity.this, new_work_modelList);
                        recyclerView.setAdapter(new_work_adapter);
                        new_work_adapter.setNewworkClick(New_Work_Activity.this);
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
    //-------------------------------------Newwork screen Service Calling------------------------------------------------

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(int position) {
        String service = new_work_modelList.get(position).getService_order_id();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("service_order_id", service);
        editor.apply();
        Intent newwork_Intent = new Intent(this, New_Work_Details_Activity.class);
        // newwork_Intent.putExtra("service", service);
        startActivity(newwork_Intent);
        finish();
    }
}
