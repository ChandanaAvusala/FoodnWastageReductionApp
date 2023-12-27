package com.ravi.fit.donatefood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;


class DonatorAdapter extends RecyclerView.Adapter<DonatorAdapter.MyHolder> {
    List<DonatorClass> donatorClassList;
    Context context;

    public DonatorAdapter(List<DonatorClass> donatorClassList, Context context) {
        this.donatorClassList = donatorClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donator_layout, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, @SuppressLint("RecyclerView") final int i) {

        myHolder.do_title.setText(donatorClassList.get(i).getD_title());
        myHolder.do_decription.setText(donatorClassList.get(i).getD_decription());
        myHolder.do_nop.setText(donatorClassList.get(i).getD_nop());
        myHolder.do_email.setText(donatorClassList.get(i).getD_email());
        myHolder.do_fddate.setText(donatorClassList.get(i).getD_fdate());
        myHolder.do_fdtime.setText(donatorClassList.get(i).getD_ftime());
        myHolder.do_mob.setText(donatorClassList.get(i).getD_mobile());
        myHolder.do_city.setText(donatorClassList.get(i).getD_city());
        myHolder.do_address.setText(donatorClassList.get(i).getD_address());


        myHolder.do_callimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){}

                String phno=donatorClassList.get(i).getD_mobile();

                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phno));
                context.startActivity(i);

            }
        });
        myHolder.do_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phno=donatorClassList.get(i).getD_mobile();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",phno,null));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donatorClassList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView do_title, do_decription, do_nop, do_email, do_fddate, do_fdtime, do_mob, do_city,do_address;

        ImageButton do_callimg,do_message,do_delete;

        public MyHolder(@NonNull View v) {
            super(v);
            do_title = v.findViewById(R.id.dona_title);
            do_decription = v.findViewById(R.id.dona_decription);
            do_nop = v.findViewById(R.id.dona_nope);
            do_email = v.findViewById(R.id.dona_email);
            do_city = v.findViewById(R.id.dona_city);
            do_address = v.findViewById(R.id.dona_address);
            do_fddate = v.findViewById(R.id.dona_fdate);
            do_fdtime = v.findViewById(R.id.dona_time);
            do_mob = v.findViewById(R.id.dona_mobile);
            do_callimg = v.findViewById(R.id.dona_call);
            do_message = v.findViewById(R.id.dona_message);

        }
    }
}
