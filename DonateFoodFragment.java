package com.ravi.fit.donatefood;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DonateFoodFragment extends Fragment {
    EditText u_donatefoodtitle, u_donatefooddec, u_donatemanypeopleeat, u_donateemail, u_donatemobile, u_donatecity, u_donateaddress, u_donatedate, u_donatetime;
    Button u_donatefood_button;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    SharedPreferences sharedPreferences;
    private int year, month, day;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    boolean isAllFieldsChecked = false;


    public DonateFoodFragment() {
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
        View v = inflater.inflate(R.layout.fragment_donate_food, container, false);

        sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);


        u_donatefood_button = v.findViewById(R.id.u_donatefood_button);
        u_donatedate = v.findViewById(R.id.u_donatedate);
        u_donatetime = v.findViewById(R.id.u_donatetime);
        u_donatefoodtitle = v.findViewById(R.id.u_donatefoodtitle);
        u_donatefooddec = v.findViewById(R.id.u_donatefooddec);
        u_donatemanypeopleeat = v.findViewById(R.id.u_donatemanypeopleeat);
        u_donateemail = v.findViewById(R.id.u_donateemail);
        u_donatemobile = v.findViewById(R.id.u_donatemobile);
        u_donatecity = v.findViewById(R.id.u_donatecity);
        u_donateaddress = v.findViewById(R.id.u_donateaddress);


        u_donatefoodtitle.setText(sharedPreferences.getString("fullname", null));
        u_donatemobile.setText(sharedPreferences.getString("mobile", null));
        u_donatecity.setText(sharedPreferences.getString("city", null));
        u_donateemail.setText(sharedPreferences.getString("email", null));
        u_donateaddress.setText(sharedPreferences.getString("address", null));


        //selecting the Date
        u_donatedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selecting the date
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                u_donatedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        //selecting the time
        u_donatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        u_donatetime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });
        u_donatefood_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
//                    Toast.makeText(getActivity(), "all fiel checked", Toast.LENGTH_SHORT).show();
                    fodddonated();
                }

            }
        });
        return v;
    }


    private boolean CheckAllFields() {

        final String food_title = u_donatefoodtitle.getText().toString().trim();
        final String food_description = u_donatefooddec.getText().toString().trim();
        final String food_count = u_donatemanypeopleeat.getText().toString().trim();
        final String food_donoremail = u_donateemail.getText().toString().trim();
        final String food_donatedate = u_donatedate.getText().toString().trim();
        final String food_donatetime = u_donatetime.getText().toString().trim();
        final String food_donormobile = u_donatemobile.getText().toString().trim();
        final String food_donorcity = u_donatecity.getText().toString().trim();
        final String food_donoraddress = u_donateaddress.getText().toString().trim();

        if(food_title.isEmpty())
        {
            u_donatefoodtitle.setError("Enter Food Title");
            u_donatefoodtitle.requestFocus();
            return false;
        } else if (food_description.isEmpty()) {
            u_donatefooddec.setError("Enter Food Description");
            u_donatefooddec.requestFocus();
            return false;
        } else if (food_description.length() < 15) {
            u_donatefooddec.setError("Description should be atleast 15 characters!");
            return false;
        } else if (food_count.isEmpty()) {
            u_donatemanypeopleeat.setError("Enter For How Many People");
            u_donatemanypeopleeat.requestFocus();
            return false;
        } else if (food_donoremail.isEmpty()) {
            u_donateemail.setError("Enter Email id");
            u_donateemail.requestFocus();
            return false;
        } else if(!(Patterns.EMAIL_ADDRESS.matcher(food_donoremail).matches()))
        {
            u_donateemail.setError("Enter a valid email");
            u_donateemail.requestFocus();
            return false;
        }
        else if (food_donatedate.isEmpty()) {
            u_donatedate.setError("Select Date");
            u_donatedate.requestFocus();
            return false;
        }

        else if (food_donatetime.isEmpty()) {
            u_donatetime.setError("Select Time");
            u_donatetime.requestFocus();
            return false;
        } else if (food_donormobile.isEmpty()) {
            u_donatemobile.setError("Enter Mobile Number");
            u_donatemobile.requestFocus();
            return false;
        } else if(!(Patterns.PHONE.matcher(food_donormobile).matches()))
        {
            u_donatemobile.setError("Enter a valid Mobile Number");
            u_donatemobile.requestFocus();
            return false;
        }
        else if (food_donorcity.isEmpty()) {
            u_donatecity.setError("Enter City");
            u_donatecity.requestFocus();
            return false;
        } else if (food_donoraddress.isEmpty()) {
            u_donateaddress.setError("Enter Full Address");
            u_donateaddress.requestFocus();
            return false;
        }
        else {
            // after all validation return true.
            return true;
        }
    }

    private void fodddonated() {

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


                        u_donatefooddec.setText("");
                        u_donatemanypeopleeat.setText("");
                        u_donatedate.setText("");
                        u_donatetime.setText("");


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
                final String ud_title = u_donatefoodtitle.getText().toString().trim();
                final String ud_fdec = u_donatefooddec.getText().toString().trim();
                final String ud_nope = u_donatemanypeopleeat.getText().toString().trim();
                final String ud_email = u_donateemail.getText().toString().trim();
                final String ud_mob = u_donatemobile.getText().toString().trim();
                final String ud_city = u_donatecity.getText().toString().trim();
                final String ud_add = u_donateaddress.getText().toString().trim();
                final String ud_date = u_donatedate.getText().toString().trim();
                final String ud_time = u_donatetime.getText().toString().trim();

                Map<String, String> data = new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds

                data.put("fooddonation_donations_add", "1");
                data.put("accesskey", "1234");
                data.put("title", ud_title);
                data.put("description", ud_fdec);
                data.put("numberofpeople", ud_nope);
                data.put("email", ud_email);
                data.put("fdate", ud_date);
                data.put("ftime", ud_time);
                data.put("mobile", ud_mob);
                data.put("city", ud_city);
                data.put("address", ud_add);
                Log.i("TAG", "getParams: " + data);
                return data;
            }
        };
        //TO add request to Volley
        mRequestQueue.add(mStringRequest);
    }
}
