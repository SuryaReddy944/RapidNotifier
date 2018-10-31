package com.sg.rapid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sg.rapid.Models.Notify;
import com.sg.rapid.R;

import java.util.ArrayList;

public class NotificationAdapter extends PagerAdapter {
    public Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<Notify> mData;



    public NotificationAdapter(Context mContext, ArrayList<Notify> mData) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = mData;


    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        TextView mTitle = (TextView)itemView.findViewById(R.id.titlenoti);
        TextView mDes = (TextView)itemView.findViewById(R.id.desnoti);

        Notify mNotify = mData.get(position);
        mTitle.setText(mNotify.getTitle());
        mDes.setText(mNotify.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}