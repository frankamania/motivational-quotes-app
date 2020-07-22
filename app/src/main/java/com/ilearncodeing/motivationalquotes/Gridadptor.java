package com.ilearncodeing.motivationalquotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

class Gridadptor extends BaseAdapter {

    ArrayList<Category> cattegorsyarraylist;
    Context context;

    public Gridadptor(ArrayList<Category> cattegorsyarraylist, Activity context) {

        this.cattegorsyarraylist = cattegorsyarraylist;
        this.context = context;


    }

    @Override
    public int getCount() {
        return cattegorsyarraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return cattegorsyarraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {




        view = LayoutInflater.from(context).inflate(R.layout.simple_grid_items_layout,null);
        ImageView categoryimage = view.findViewById(R.id.categoryimage);
        TextView categoryname = view.findViewById(R.id.categoryname);


        Glide.with(context)
                .load(cattegorsyarraylist.get(i).getThumbUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(categoryimage);

        categoryname.setText(cattegorsyarraylist.get(i).getCategoryName());



        LinearLayout clicklinier = view.findViewById(R.id.clicklinier);

        clicklinier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(context, QuotesActivity.class);
                myintent.putExtra("category", cattegorsyarraylist.get(i).getCategoryName());
                context.startActivity(myintent);



            }
        });



        return view;
    }
}
