package com.uclick.technician.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uclick.technician.Login.Login_Activity;
import com.uclick.technician.R;
/*
import com.colourmoon.anajaniputra.Services.JsonPlaceHolder;
import com.colourmoon.anajaniputra.Services.RetrofitInstance;
*/
import com.uclick.technician.Services.JsonPlaceHolder;
import com.uclick.technician.Services.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
*/

public class Setting_Activity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    // "(?=.*[a-zA-Z])" +      //any letter
                    // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    // ".{8,}" +               //at least 4 characters
                    "$");

    String passwordInput, confirmPasswordInput;

    Toolbar toolbar;
    EditText oldPassword, newPassword, confirmPassword;
    String message;
    SharedPreferences preferences;
    String technician_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);

        toolbar = findViewById(R.id.settings_ToolBar);
        setSupportActionBar(toolbar);
        Drawable backArrow = this.getResources().getDrawable(R.drawable.white_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("db", MODE_PRIVATE);
        technician_id = preferences.getString("technician_id", null);

        oldPassword = findViewById(R.id.old_Password);
        newPassword = findViewById(R.id.new_Password);
        confirmPassword = findViewById(R.id.confirm_Password);
    }


    public void UpdatePassword(final View view) {

        if (!validatePassword() | !validateConfirmPassword()) {
            return;
        } else {

            String old_Password = oldPassword.getText().toString();
            String new_Password = newPassword.getText().toString();

            Map<String, String> updatePassword = new HashMap<>();

            updatePassword.put("technician_id", technician_id);
            updatePassword.put("old_password", old_Password);
            updatePassword.put("new_password", new_Password);

            //------------------Service call------------------
            JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
            Call<ResponseBody> responseBodyCall = jsonPlaceHolder.update_password(updatePassword);

            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responsestring = new String(response.body().bytes());
                        JSONObject jsonObject = new JSONObject(responsestring);
                        message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equals("valid")) {
                            Toast.makeText(Setting_Activity.this, message, Toast.LENGTH_SHORT).show();
                            // To clear the text
                            oldPassword.getText().clear();
                            newPassword.getText().clear();
                            confirmPassword.getText().clear();
                            Logout();
                        } else {
                            Toast.makeText(Setting_Activity.this, message, Toast.LENGTH_SHORT).show();
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
    }

    public void Logout() {
        SharedPreferences preferences = getSharedPreferences("db", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogged", false);
        editor.apply();
        startActivity(new Intent(this, Login_Activity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    //---------------Service call----------------------

    //-------Password--------
    private boolean validatePassword() {
        passwordInput = newPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            newPassword.setError("Field can't be empty");
            return false;

        } else {
            newPassword.setError(null);
            return true;
        }
    }

    //---------Confirm Password-----------
    private boolean validateConfirmPassword() {
        confirmPasswordInput = confirmPassword.getText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {
            confirmPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(confirmPasswordInput).matches()) {
            if (!confirmPasswordInput.equals(passwordInput)) {
                confirmPassword.setError("Password Not Matched");
                return false;
            } else {
                confirmPassword.setError(null);
                return true;
            }
        } else {
            confirmPassword.setError(null);
            return true;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
