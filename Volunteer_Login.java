package com.ravi.fit.donatefood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.ravi.fit.donatefood.Network.API;
import com.ravi.fit.donatefood.Network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Volunteer_Login extends AppCompatActivity {
    TextView register_volun;
    EditText userids, passwords;
    Button login_btn;
    SharedPreferences shre;
    String user_name, password;
    ProgressBar progressBar;
    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer__login);
        shre = getSharedPreferences("pref", MODE_PRIVATE);
        register_volun = findViewById(R.id.register_volunteer);
        login_btn = findViewById(R.id.vol_login);
        userids = findViewById(R.id.userids_vol);
        passwords = findViewById(R.id.passwords_vol);
        progressBar = findViewById(R.id.progress);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    volunteerLogin();
                }
            }
        });
        register_volun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), Register_Volunteer.class);
                startActivity(i);
            }

        });
    }

    private boolean CheckAllFields() {
        if (userids.length() == 0) {
            userids.setError("Email is required");
            userids.requestFocus();
            return false;
        }
        else if (passwords.length() == 0) {
            passwords.setError("Password is required");
            passwords.requestFocus();
            return false;
        } else if (passwords.length() < 8) {
            passwords.setError("Password must be minimum 8 characters");
            return false;
        }
        // after all validation return true.
        return true;
    }

    private void volunteerLogin() {

        user_name = userids.getText().toString();
        password = passwords.getText().toString();


        //calling url
        String serverurl = API.login;
        //sending request to url for response Or Request Constructer with 4 parameters
        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        JSONObject user = (JSONObject) jsonObject.getJSONArray("data").get(0);
                        String userid = user.getString("id");
                        String fullname = user.getString("fullname");
                        String email = user.getString("email");
                        String mobile = user.getString("mobile");
                        String city = user.getString("city");
                        String address = user.getString("address");
                        Toast.makeText(getApplicationContext(), "Login " + res, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = shre.edit();
                        editor.putString("id", userid);
                        editor.putString("email", email);
                        editor.putString("fullname", fullname);
                        editor.putString("mobile", mobile);
                        editor.putString("city", city);
                        editor.putString("address", address);
                        editor.apply();
                        Intent i = new Intent(getApplicationContext(), VolunteerHome.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Log.e("ERROR", "Exception");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put("fooddonation_user_login", "1");
                data.put("accesskey", "1234");
                data.put("email", user_name);
                data.put("password", password);
                return data;
            }
        };
        //TO add request to Volley
        progressBar.setVisibility(View.VISIBLE);
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }

}
