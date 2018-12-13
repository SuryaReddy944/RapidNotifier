package com.sg.rapid.EventsAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sg.rapid.Fragments.NotificationsFragment;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

public class EventSectionViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    public EventSectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.sectionHeader);
        name.setTypeface(CustomFonts.getNexaBold(NotificationsFragment.mContext));
    }
}