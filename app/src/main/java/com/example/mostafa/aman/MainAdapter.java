package com.example.mostafa.aman;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;

public class MainAdapter extends BaseAdapter{
    ArrayList<Model> data = new ArrayList<Model>() ;
    Context mContext ;
    String uri = null ;
    public MainAdapter(Context mContext ,ArrayList<Model> doctor_data) {
       this.mContext=mContext;
        data.clear();
        this.data = doctor_data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder =null;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custum_list,parent,false);
            holder = new ViewHolder();
            view.setTag(holder);
        }

        else {
            holder= (ViewHolder) view.getTag();
        }
        Log.d("CarTest", "data arry is "+data.size());
        holder.doctor_phone = (Button) view.findViewById(R.id.doctor_phone);
        holder.doctor_name = (TextView) view.findViewById(R.id.doctor_name);
        holder.doctor_address = (TextView) view.findViewById(R.id.doctor_address);
        holder.doctor_specailist = (TextView) view.findViewById(R.id.doctor_spcialist);
        holder.doc_img = (ImageView) view.findViewById(R.id.doc_img);

        final Model model =data.get(position);

        holder.doctor_specailist.setText(model.getDegree());
        holder.doctor_phone.setText(model.getPhoneNumber());
        holder.doctor_address.setText(model.getAddress());
        holder.doctor_name.setText(model.getDoc_name());
        holder.doc_img.setImageResource(R.drawable.doctor1);
//        holder.title.setText("محافظه"+""+ g+""+"قسم"+""+s);

        holder.doctor_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = "tel:" + model.getPhoneNumber();
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri
                        .parse(uri));
                Log.w("CarTest", uri);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    mContext.getApplicationContext().startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(mContext.getApplicationContext(),
                            "Your call has failed...", Toast.LENGTH_LONG)
                            .show();
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}


class ViewHolder {
Button doctor_phone ;
    TextView doctor_name ,doctor_address,doctor_specailist;
    ImageView doc_img;
}
