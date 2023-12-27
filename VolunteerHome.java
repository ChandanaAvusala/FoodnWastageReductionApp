package com.ravi.fit.donatefood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class VolunteerHome extends AppCompatActivity {
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        cardView1 = findViewById(R.id.find_req);
        cardView2 = findViewById(R.id.view_prof);
        cardView3 = findViewById(R.id.log_out);
        cardView4 = findViewById(R.id.donatefooduser);
        cardView5 = findViewById(R.id.MyDonation);
        cardView6 = findViewById(R.id.Updateprofilecard);

        SwitchFrag(new ProfileFragment());
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchFrag(new ProfileFragment());
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchFrag(new MyDonationFragment());
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchFrag(new DonateFoodFragment());
            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchFrag(new RequestsFragment());
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchFrag(new UpdateProfileFragment());
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Volunteer_Login.class);
                SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", "");
                editor.putString("fullname", "");
                editor.putString("mobile", "");
                editor.putString("city", "");
                editor.putString("address", "");
                editor.apply();

                startActivity(i);
                finish();
            }
        });
    }

    public void SwitchFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
