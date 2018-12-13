package com.sg.rapid.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.Models.AlaramData;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.DateUtils;

import java.util.List;

import SectionedList.SectionRecyclerViewAdapter;

/**
 * Created by Surya on 30/10/18.
 */

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, AlaramResponse, SectionViewHolder, ChildViewHolder> {

    Context context;

    public AdapterSectionRecycler(Context context, List<SectionHeader> sectionHeaderItemList) {
        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_item, sectionViewGroup, false);

        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alaram_item, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader sectionHeader) {
        sectionViewHolder.name.setText(sectionHeader.sectionText);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int sectionPosition, final int childPosition, AlaramResponse child) {
        childViewHolder.date.setText(DateUtils.getDay(child.getCreateDate()));
        childViewHolder.time.setText(DateUtils.getTime(child.getCreateDate()));
        childViewHolder.title.setText(child.getAssetName());
        childViewHolder.description.setText(child.getAssetType()+ "\n"+child.getFaultDecription());
        childViewHolder.info.setText(child.getAlarmValues());





        if(child.getAPriority().equalsIgnoreCase("High")){
            childViewHolder.circle.setBackgroundResource(R.drawable.red_circle);
        }else if(child.getAPriority().equalsIgnoreCase("Normal")){
            childViewHolder.circle.setBackgroundResource(R.drawable.green_circle);
        }else if(child.getAPriority().equalsIgnoreCase("Low")){
            childViewHolder.circle.setBackgroundResource(R.drawable.yellow_circle);
        }else if(child.getAPriority().equalsIgnoreCase("Medium")){
            childViewHolder.circle.setBackgroundResource(R.drawable.orange_cirlce);
        }else{
            childViewHolder.circle.setBackgroundResource(R.drawable.green_circle);
        }

        int notes = child.getAcknowledgementStatus();
        if(notes == 0 ){
            childViewHolder.date.setTextColor(context.getResources().getColor(R.color.block));
            childViewHolder.title.setTextColor(context.getResources().getColor(R.color.block));
            childViewHolder.time.setTextColor(context.getResources().getColor(R.color.block));
            childViewHolder.description.setTextColor(context.getResources().getColor(R.color.block));
            childViewHolder.info.setTextColor(context.getResources().getColor(R.color.block));
        }else{
            childViewHolder.date.setTextColor(context.getResources().getColor(R.color.grey));
            childViewHolder.title.setTextColor(context.getResources().getColor(R.color.grey));
            childViewHolder.time.setTextColor(context.getResources().getColor(R.color.grey));
            childViewHolder.description.setTextColor(context.getResources().getColor(R.color.grey));
            childViewHolder.info.setTextColor(context.getResources().getColor(R.color.grey));
        }



    }


}
