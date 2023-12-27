package com.ravi.fit.donatefood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register_Volunteer extends AppCompatActivity {
    EditText name, mobile, city, password, confirmpassword, emailid, address;
    TextView register_login_text;
    Button submit;
    SharedPreferences sharedPreferences;
    boolean isAllFieldsChecked = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__volunteer);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, VolunteerHome.class));
            return;
        }
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        name = findViewById(R.id.vol_name);
        mobile = findViewById(R.id.vol_mobile);
        emailid = findViewById(R.id.vol_emailid);
        password = findViewById(R.id.vol_password);
        confirmpassword = findViewById(R.id.confirm_vol);
        city = findViewById(R.id.vol_city);
        address = findViewById(R.id.vol_address);
        submit = findViewById(R.id.reg_button);
        register_login_text = findViewById(R.id.register_login_text);

        register_login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Volunteer_Login.class);
                startActivity(i);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_email = emailid.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", f_email);
                editor.apply();
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    RegisterVolunteer();
                }
            }
        });
    }
    private boolean CheckAllFields() {
        final String ch_name = name.getText().toString().trim();
        final String ch_mobile = mobile.getText().toString().trim();
        final String ch_city = city.getText().toString().trim();
        final String ch_password = password.getText().toString().trim();
        final String c_password = confirmpassword.getText().toString().trim();
        final String ch_emailid = emailid.getText().toString().trim();
        final String ch_address = address.getText().toString().trim();

        if (ch_name.isEmpty()) {
            name.setError("Enter Full Name");
            name.requestFocus();
            return false;
        } else if (ch_mobile.isEmpty()) {
            mobile.setError("Mobile Number is required");
            mobile.requestFocus();
            return false;
        } else if (ch_emailid.isEmpty()) {
            emailid.setError("Email is required");
            emailid.requestFocus();
            return false;
        } else if(ch_password.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        } else if (c_password.isEmpty()) {
            confirmpassword.setError("Confirm Password is required");
            confirmpassword.requestFocus();
            return false;
        } else if (ch_city.isEmpty()) {
            city.setError("Email is required");
            city.requestFocus();
            return false;
        } else if (ch_address.isEmpty()) {
            address.setError("Email is required");
            address.requestFocus();
            return false;
        } else if(c_password.compareTo(ch_password) != 0) {
            confirmpassword.setError("ConfirmPassword mismatched with Password!");
            confirmpassword.requestFocus();
            return false;
        } else if (!ch_password.matches(".*[A-Z].*") || !ch_password.matches(".*[a-z].*") || !ch_password.matches(".*\\d.*") || !ch_password.matches(".*[!@#\\$%^&*].*")) {
            password.setError("Password must contain at least one uppercase letter,one lowercase letter, one digit, and one special character!");
            password.requestFocus();
            return false;
        } else if(!(Patterns.EMAIL_ADDRESS.matcher(ch_emailid).matches())) {
            emailid.setError("Enter a valid email");
            emailid.requestFocus();
            return false;
        } else if(!(Patterns.PHONE.matcher(ch_mobile).matches())) {
            mobile.setError("Enter a valid Mobile Number");
            mobile.requestFocus();
            return false;
        }
        // after all validation return true.
        return true;
    }
    private void RegisterVolunteer() {
        final String f_name = name.getText().toString().trim();
        final String f_mobile = mobile.getText().toString().trim();
        final String f_city = city.getText().toString().trim();
        final String f_password = password.getText().toString().trim();
        final String f_emailid = emailid.getText().toString().trim();
        final String f_address = address.getText().toString().trim();

        //calling url
        String serverurl = API.register;
        //sending request to url for response Or Request Constructer with 4 parameters
        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        Toast.makeText(getApplicationContext(), "Registration " + res, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Volunteer_Login.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
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
                data.put("fooddonation_user_registration", "1");
                data.put("accesskey", "1234");
                data.put("fullname", f_name);
                data.put("email", f_emailid);
                data.put("password", f_password);
                data.put("mobile", f_mobile);
                data.put("city", f_city);
                data.put("address", f_address);
                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }
}
