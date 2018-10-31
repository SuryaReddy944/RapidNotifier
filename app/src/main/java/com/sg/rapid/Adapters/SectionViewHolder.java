package com.sg.rapid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sg.rapid.Fragments.NotificationsFragment;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

/**
 * Created by Surya on 30/10/18.
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    public SectionViewHolder(View itemView) {
        super(itemView);
         name = (TextView) itemView.findViewById(R.id.sectionHeader);
        name.setTypeface(CustomFonts.getNexaBold(NotificationsFragment.mContext));
    }
}
