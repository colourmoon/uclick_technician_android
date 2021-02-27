package com.uclick.technician.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uclick.technician.Home.Home_Activity;
import com.uclick.technician.R;
import com.uclick.technician.Services.JsonPlaceHolder;
import com.uclick.technician.Services.RetrofitInstance;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    Toolbar toolbar;
    TextView forgotPassword;
    EditText userLogin, userPassword;
    SharedPreferences preferences;
    String technician_id, technician_name, technician_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        toolbar = findViewById(R.id.Login_Toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Login");
        Drawable backArrow = this.getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userLogin = findViewById(R.id.loginId);
        userPassword = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgot_password);

        //------------SharedPreferences---------------
        preferences = getSharedPreferences("db", MODE_PRIVATE);
    }


    public void LoginBtn(final View view) {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = null;
        if (cm != null) {
            nInfo = cm.getActiveNetworkInfo();
        }
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        if (connected) {
            //---------------------------services----------------
            String login_Id = userLogin.getText().toString();
            String login_Password = userPassword.getText().toString();

            Map<String, String> login_Details = new HashMap<>();
            login_Details.put("username", login_Id);
            login_Details.put("password", login_Password);

            JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
            Call<ResponseBody> responseBodyCall = jsonPlaceHolder.postLogin(login_Details);

            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = new String(response.body().bytes());
                        JSONObject responseJsonObject = new JSONObject(responseString);

                        if (responseJsonObject.getString("status").equals("valid")) {
                            String dataString = responseJsonObject.getString("data");
                            JSONObject dataJsonObject = new JSONObject(dataString);

                            technician_id = dataJsonObject.getString("technician_id");
                            technician_name = dataJsonObject.getString("name");
                            technician_address = dataJsonObject.getString("address");

                            //--------------put text to display in Home activity--------
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("technician_id", technician_id);
                            editor.putString("name", technician_name);
                            editor.putString("address", technician_address);
                            editor.putBoolean("isLogged", true);
                            editor.apply();
                            Intent loginIntent = new Intent(Login_Activity.this, Home_Activity.class);
                            startActivity(loginIntent);
                            Toast.makeText(Login_Activity.this, "Login Success", Toast.LENGTH_LONG).show();
                            finish();
                        } else if (responseJsonObject.getString("status").equals("Invalid")) {
                            String message = responseJsonObject.getString("message");

                            Toast.makeText(Login_Activity.this, "" + message, Toast.LENGTH_LONG).show();
                        }

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
        } else {
            Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }
     /*   Intent loginIntent = new Intent(Login_Activity.this, Home_Activity.class);
        startActivity(loginIntent);
*/

    //---------Email Validation-----------
    private boolean validateEmail() {
        String emailInput = userLogin.getText().toString().trim();

        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                "\\@" +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                "(" +

                "\\." +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                ")+";
        Matcher matcher = Pattern.compile(validemail).matcher(emailInput);

        if (emailInput.isEmpty()) {
            userLogin.setError("Field can't be empty");
            return false;
        } else if (!matcher.matches()) {
            userLogin.setError("Please enter a valid email address");
            return false;
        } else {
            userLogin.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = userPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            userPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            userPassword.setError("Password too weak");
            return false;
        } else {
            userPassword.setError(null);
            return true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

   public void forgotpassword(View view)
   {
                  final AlertDialog.Builder alert = new AlertDialog.Builder(Login_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.forgotpassword_custom_dialog, null);
                final EditText text_input = (EditText) mView.findViewById(R.id.text_input);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                Button submit_btn = (Button) mView.findViewById(R.id.submit_btn);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancel_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                submit_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        final String mobile = text_input.getText().toString();
                        //----------Service Calling for forgot password----------
                        JsonPlaceHolder jsonPlaceHolder = RetrofitInstance.getInstance().create(JsonPlaceHolder.class);
                        Call<ResponseBody> responseBodyCall = jsonPlaceHolder.forgot_password(mobile);
                        responseBodyCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String responseString = new String(response.body().bytes());
                                    JSONObject jsonObject = new JSONObject(responseString);

                                    if (jsonObject.getString("status").equals("valid")) {
                                        String message = jsonObject.getString("message");
                                        alertDialog.dismiss();
                                        Snackbar.make(v, "New password sent to your mobile number", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    } else {
                                        Snackbar.make(v, "Password Sending failed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
                });
                alertDialog.show();
            }
            //---------------forgot password service call----------
   }

