package com.sg.rapid.EventsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sg.rapid.Adapters.ChildViewHolder;
import com.sg.rapid.Adapters.SectionHeader;
import com.sg.rapid.Adapters.SectionViewHolder;
import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.EventServices.EventsResponse;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.DateUtils;

import java.util.List;

import SectionedList.SectionRecyclerViewAdapter;

public class EventSectionAdapter  extends SectionRecyclerViewAdapter<EventSectionHeader, EventsResponse, EventSectionViewHolder, EventChildViewHolder> {

    Context context;

    public EventSectionAdapter(Context context, List<EventSectionHeader> sectionHeaderItemList) {
        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public EventSectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_item, sectionViewGroup, false);
        return new EventSectionViewHolder(view);
    }

    @Override
    public EventChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alaram_item, childViewGroup, false);
        return new EventChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(EventSectionViewHolder sectionViewHolder, int sectionPosition, EventSectionHeader sectionHeader) {
        sectionViewHolder.name.setText(sectionHeader.sectionText);
    }

    @Override
    public void onBindChildViewHolder(EventChildViewHolder childViewHolder, int sectionPosition, int childPosition, EventsResponse child) {
        childViewHolder.date.setText(DateUtils.getDay(child.getCreateDate()));
        childViewHolder.time.setText(DateUtils.getTime(child.getCreateDate()));
        childViewHolder.title.setText(child.getAssetName());
        childViewHolder.description.setText(child.getAssetType()+"\n"+child.getEventDecription());
        childViewHolder.info.setText(child.getEventValues());

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
