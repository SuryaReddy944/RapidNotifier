package com.sg.rapid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sg.rapid.Models.AlaramData;
import com.sg.rapid.R;

import java.util.List;

import SectionedList.SectionRecyclerViewAdapter;

/**
 * Created by Surya on 30/10/18.
 */

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, AlaramData, SectionViewHolder, ChildViewHolder> {

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
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int sectionPosition, int childPosition, AlaramData child) {
        childViewHolder.date.setText(child.getDate());
        childViewHolder.time.setText(child.getTime());
        childViewHolder.title.setText(child.getTitle());
        childViewHolder.description.setText(child.getDescription());
        childViewHolder.info.setText(child.getInfo());

        if(child.getStatus().equalsIgnoreCase("normal")){
            childViewHolder.circle.setBackgroundResource(R.drawable.green_circle);

        }else if(child.getStatus().equalsIgnoreCase("low")){
            childViewHolder.circle.setBackgroundResource(R.drawable.red_circle);
        }else if(child.getStatus().equalsIgnoreCase("high")){
            childViewHolder.circle.setBackgroundResource(R.drawable.yellow_circle);
        }else{
            childViewHolder.circle.setBackgroundResource(R.drawable.green_circle);
        }






    }
}
