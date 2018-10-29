package com.sg.rapid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.rapid.Models.AlaramData;
import com.sg.rapid.R;

import java.util.List;

public class AlaramAdapter  extends RecyclerView.Adapter<AlaramAdapter.MyViewHolder> {

    private List<AlaramData> alaramlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,time,title,description,info,year;
        public LinearLayout llyear,lldata;

        public MyViewHolder(View view) {
            super(view);
            year =  (TextView) view.findViewById(R.id.lblyear);
            date  = (TextView) view.findViewById(R.id.lbldate);
            time = (TextView) view.findViewById(R.id.lbltime);
            title = (TextView) view.findViewById(R.id.lbltitle);
            description = (TextView) view.findViewById(R.id.lbldescription);
            info = (TextView) view.findViewById(R.id.lblinfo);
            llyear = (LinearLayout)view.findViewById(R.id.llyear);
            lldata = (LinearLayout)view.findViewById(R.id.llinfo);
        }
    }


    public AlaramAdapter(List<AlaramData> alaramlist) {
        this.alaramlist = alaramlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alaram_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AlaramData mData = alaramlist.get(position);




        holder.date.setText(mData.getDate());
        holder.time.setText(mData.getTime());
        holder.title.setText(mData.getTitle());
        holder.description.setText(mData.getDescription());
        holder.info.setText(mData.getInfo());





    }

    @Override
    public int getItemCount() {
        return alaramlist.size();
    }
}