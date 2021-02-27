package com.uclick.technician.Notification;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;
/*
import com.colourmoon.anajaniputra.Services.JsonPlaceHolder;
import com.colourmoon.anajaniputra.Services.RetrofitInstance;
*/

import java.util.ArrayList;
import java.util.List;

/*
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
*/

public class Notification_Activity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    Notification_Adapter adapter;
    List<Notification_Model> notificationModel;

    SharedPreferences preferences;
    String db_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);

        toolbar = findViewById(R.id.notification_ToolBar);
        setSupportActionBar(toolbar);
        Drawable backArrow = this.getResources().getDrawable(R.drawable.white_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("db", MODE_PRIVATE);
        db_id = preferences.getString("db_id", null);


        recyclerView = findViewById(R.id.notification_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationModel = new ArrayList<>();

       /* notificationModel.add(new Notification_Model("Lorem Ipsum is simply dummy text of the printing\n" +
                "and typesetting industry."));
        notificationModel.add(new Notification_Model("Lorem Ipsum is simply dummy text of the printing\n" +
                "and typesetting industry."));

        notificationModel.add(new Notification_Model("Lorem Ipsum is simply dummy text of the printing\n" +
                "and typesetting industry."));*/
//---------------
       /* JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.getNotification(db_id);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(responseBody);

                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    JSONObject inner_JsonObject = jsonArray.getJSONObject(0);

                    if (inner_JsonObject.getString("status").equals("Valid")) {
                        JSONArray inner_JsonArray = inner_JsonObject.getJSONArray("notifications");

                        for (int i = 0; i < inner_JsonArray.length(); i++) {
                            JSONObject dataObject = inner_JsonArray.getJSONObject(i);
                            notificationModel.add(new Notification_Model(dataObject.getString("notification")));
                        }
                    }
                    adapter = new Notification_Adapter(Notification_Activity.this, notificationModel);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(Notification_Activity.this, DividerItemDecoration.VERTICAL));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
