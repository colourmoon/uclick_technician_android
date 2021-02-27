package com.uclick.technician.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.CompletedWork.Completed_Work_Activity;
import com.uclick.technician.Login.Login_Activity;
import com.uclick.technician.NewWork.New_Work_Activity;
import com.uclick.technician.R;
import com.uclick.technician.Settings.Setting_Activity;


import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity implements Home_Menu_Adapter.onMenuClickListener {

    TextView technician_name, technician_address;
    String name, address;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView logoutBtn;

    Home_Menu_Adapter adapter;
    List<Home_Menu_Model> modelList;

    SharedPreferences preferences;
    String db_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        toolbar = findViewById(R.id.Home_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Anajaniputra Worker Dashboard");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //-------------SharedPreference--------
        preferences = getSharedPreferences("db", MODE_PRIVATE);
        name = preferences.getString("name", null);
        address = preferences.getString("address", null);

        //---------------To get text---> name and address in Home Activity-----------
        technician_name = findViewById(R.id.technicianName);
        technician_address = findViewById(R.id.technicianAddress);
        technician_name.setText(name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            technician_address.setText(Html.fromHtml(address, Html.FROM_HTML_MODE_COMPACT));
        } else {
            technician_address.setText(Html.fromHtml(address));
        }
        // technician_address.setText(address);

        logoutBtn = findViewById(R.id.logoutBtn);

        modelList = new ArrayList<>();

        recyclerView = findViewById(R.id.home_MenuRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        modelList.add(new Home_Menu_Model(R.drawable.home_new_works, "New Works"));
        modelList.add(new Home_Menu_Model(R.drawable.home_completed_works, "Completed Works"));
        modelList.add(new Home_Menu_Model(R.drawable.home_settings, "Settings"));

        adapter = new Home_Menu_Adapter(Home_Activity.this, modelList);
        recyclerView.setAdapter(adapter);
        adapter.setOnMenuClickListener(Home_Activity.this);
    }

    @Override
    public void onItemClick(int position) {

        if (position == 0) {
            Intent new_work = new Intent(Home_Activity.this, New_Work_Activity.class);
            startActivity(new_work);
        }

        if (position == 1) {
            Intent delivery = new Intent(Home_Activity.this, Completed_Work_Activity.class);
            startActivity(delivery);
        }
        if (position == 2) {
            Intent settings = new Intent(Home_Activity.this, Setting_Activity.class);
            startActivity(settings);
        }
    }

    public void Logout(View view) {
        SharedPreferences preferences = getSharedPreferences("db", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogged", false);
        editor.apply();
        startActivity(new Intent(this, Login_Activity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Home_Activity.this.finishAffinity();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.mipmap.ic_app_icon);
        alertDialog.show();
    }

}