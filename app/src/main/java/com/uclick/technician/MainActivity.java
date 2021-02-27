package com.uclick.technician;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.uclick.technician.Home.Home_Activity;
import com.uclick.technician.Login.Login_Activity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    Boolean isLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .playOn(findViewById(R.id.splash_img_view));

        preferences = getSharedPreferences("db", MODE_PRIVATE);
        isLogged = preferences.getBoolean("isLogged", false);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isLogged) {
                    startActivity(new Intent(MainActivity.this, Home_Activity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                }

                /*startActivity(new Intent(MainActivity.this, Login_Activity.class));*/
                finish();
            }
        }, 3000);
    }

    //---StatusBar Colour ----
    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));

        }
    }
}
