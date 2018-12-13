package com.sg.rapid.EventsAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sg.rapid.Fragments.NotificationsFragment;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

public class EventChildViewHolder extends RecyclerView.ViewHolder {

    public TextView date,time,title,description,info,circle,verticleline;
    public View mCircle,mLine;

    public EventChildViewHolder(View itemView) {
        super(itemView);
        date  = (TextView) itemView.findViewById(R.id.lbldate);
        time = (TextView) itemView.findViewById(R.id.lbltime);
        title = (TextView) itemView.findViewById(R.id.lbltitle);
        description = (TextView) itemView.findViewById(R.id.lbldescription);
        info = (TextView) itemView.findViewById(R.id.lblinfo);
        circle = (TextView) itemView.findViewById(R.id.viewcircle);
        verticleline = (TextView) itemView.findViewById(R.id.verticalline);

        //fonts

        date.setTypeface(CustomFonts.getNexaBold(NotificationsFragment.mContext));
        time.setTypeface(CustomFonts.getNexaRegular(NotificationsFragment.mContext));
        title.setTypeface(CustomFonts.getNexaBold(NotificationsFragment.mContext));
        description.setTypeface(CustomFonts.getNexaRegular(NotificationsFragment.mContext));
        info.setTypeface(CustomFonts.getNexaBold(NotificationsFragment.mContext));

    }
}

