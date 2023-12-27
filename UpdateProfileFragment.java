package com.ravi.fit.donatefood;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ravi.fit.donatefood.Network.API;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileFragment extends Fragment {

    EditText update_name, update_mobile, update_city, update_emailid, update_address;
    Button update_button;
    SharedPreferences sharedPreferences;
    boolean isAllFieldsChecked = false;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences("pref", MODE_PRIVATE);


        update_name = v.findViewById(R.id.update_name);
        update_mobile = v.findViewById(R.id.update_mobile);
        update_city = v.findViewById(R.id.update_city);
        update_emailid = v.findViewById(R.id.update_emailid);
        update_address = v.findViewById(R.id.update_address);
        update_button = v.findViewById(R.id.update_button);


        update_name.setText(sharedPreferences.getString("fullname", null));
        update_mobile.setText(sharedPreferences.getString("mobile", null));
        update_city.setText(sharedPreferences.getString("city", null));
        update_emailid.setText(sharedPreferences.getString("email", null));
        update_address.setText(sharedPreferences.getString("address", null));

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    UpdateProfiledata();
                }
            }
        });
        return v;
    }

    private boolean CheckAllFields() {
        if (update_name.length() == 0) {
            update_name.setError("Enter Full Name");
            update_name.requestFocus();
            return false;
        }
        if (update_mobile.length() == 0) {
            update_mobile.setError("Mobile Number is required");
            update_mobile.requestFocus();
            return false;
        }
        if (update_mobile.length() < 10) {
            update_mobile.setError("Mobile Number must be minimum 10 characters");
            update_mobile.requestFocus();
            return false;
        }
        if (update_emailid.length() == 0) {
            update_emailid.setError("Email is required");
            update_emailid.requestFocus();
            return false;
        }
        if (update_city.length() == 0) {
            update_city.setError("Email is required");
            update_city.requestFocus();
            return false;
        }
        if (update_address.length() == 0) {
            update_address.setError("Email is required");
            update_address.requestFocus();
            return false;
        }

        // after all validation return true.
        return true;
    }
    private void UpdateProfiledata() {
        mRequestQueue = Volley.newRequestQueue(getActivity());

        //calling url
        String serverurl = API.register;
        //sending request to url for response Or Request Constructer with 4 parameters
        mStringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("result");//result should be matched with url link response ie,{"result":"success"}
                    if (res.equals("success")) //array key
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "successfully insert : " + res, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
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
                final String us_name = update_name.getText().toString().trim();
                final String us_mobile = update_mobile.getText().toString().trim();
                final String us_email = update_emailid.getText().toString().trim();
                final String us_city = update_city.getText().toString().trim();
                final String us_address = update_address.getText().toString().trim();

                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", us_email);
                editor.putString("fullname", us_name);
                editor.putString("mobile", us_mobile);
                editor.putString("city", us_city);
                editor.putString("address", us_address);
                editor.apply();

                data.put("fooddonation_user_update_profile", "1");
                data.put("accesskey", "1234");
                data.put("fullname", us_name);
                data.put("email", us_email);
                data.put("mobile", us_mobile);
                data.put("address", us_address);
                data.put("city", us_city);
                data.put("id", sharedPreferences.getString("id", null));
                Log.i("TAG", "getParams: " + data);
                return data;
            }
        };
        //TO add request to Volley
        mRequestQueue.add(mStringRequest);
    }
}