package com.ravi.fit.donatefood;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ravi.fit.donatefood.Network.API;
import com.ravi.fit.donatefood.Network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDonationFragment extends Fragment {
    RecyclerView recyclerView;
    DonatorAdapter donatorAdapter;
    List<DonatorClass> classList = new ArrayList<>();
    ProgressBar progressBar;

    public MyDonationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_donation, container, false);
        recyclerView = v.findViewById(R.id.Myrecy_list);
        progressBar = v.findViewById(R.id.progress);
        getMyFood();
        return v;
    }
    private void getMyFood() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String serverurl = API.view_food;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String s_id = jsonObject1.getString("id");
                            String s_title = jsonObject1.getString("title");
                            String s_desc = jsonObject1.getString("description");
                            String s_nop = jsonObject1.getString("numberofpeople");
                            String s_email = jsonObject1.getString("email");
                            String s_fdate = jsonObject1.getString("fdate");
                            String s_ftime = jsonObject1.getString("ftime");
                            String s_mobile = jsonObject1.getString("mobile");
                            String s_city = jsonObject1.getString("city");
                            String s_address = jsonObject1.getString("address");

                            DonatorClass donatorClass = new DonatorClass(s_id, s_title, s_desc, s_nop, s_email, s_fdate, s_ftime, s_mobile, s_city, s_address);
                            classList.add(donatorClass);
                        }
                        donatorAdapter = new DonatorAdapter(classList, getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(donatorAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();//to bind group of data
//                //to insert data from edit feilds into table feilds
                data.put("fooddonation_donations_viewby_email", "1");
                data.put("accesskey", "1234");
                data.put("email", sharedPreferences.getString("email", null));
                return data;
            }
        };
        progressBar.setVisibility(View.VISIBLE);
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        queue.add(stringRequest);
    }
}