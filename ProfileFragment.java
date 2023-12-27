package com.ravi.fit.donatefood;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    TextView us_fulname, us_mobile, us_email, us_city, us_passw, us_address, us_id;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = v.findViewById(R.id.progress);
        us_fulname = v.findViewById(R.id.u_name);
        us_mobile = v.findViewById(R.id.u_mobile);
        us_city = v.findViewById(R.id.u_city);
        us_email = v.findViewById(R.id.u_emailid);
        us_address = v.findViewById(R.id.u_address);
        us_id = v.findViewById(R.id.u_id);

        sharedPreferences = getActivity().getSharedPreferences("pref", MODE_PRIVATE);

        us_id.setText(sharedPreferences.getString("id", null));
        us_fulname.setText(sharedPreferences.getString("fullname", null));
        us_mobile.setText(sharedPreferences.getString("mobile", null));
        us_city.setText(sharedPreferences.getString("city", null));
        us_email.setText(sharedPreferences.getString("email", null));
        us_address.setText(sharedPreferences.getString("address", null));

        return v;
    }
}
