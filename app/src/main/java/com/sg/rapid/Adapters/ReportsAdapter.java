package com.sg.rapid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sg.rapid.R;
import com.sg.rapid.ReportServices.ReportData;
import com.sg.rapid.Utilities.CustomFonts;

import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder> {

    private Context mContext;
    private List<ReportData> mData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView assetname, assettype,description,count;


        public MyViewHolder(View view) {
            super(view);
            assetname = (TextView) view.findViewById(R.id.assetname);
            assettype = (TextView) view.findViewById(R.id.assettype);
            description = (TextView) view.findViewById(R.id.description);
            count = (TextView) view.findViewById(R.id.count);

            assetname.setTypeface(CustomFonts.getNexaBold(mContext));
            assettype.setTypeface(CustomFonts.getNexaBold(mContext));
            description.setTypeface(CustomFonts.getNexaBold(mContext));
            count.setTypeface(CustomFonts.getNexaBold(mContext));

        }
    }



    public ReportsAdapter(Context mContext, List<ReportData> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ReportData repodata = mData.get(position);
        holder.assetname.setText("Asset Name: "+repodata.getAssetName());
        holder.assettype.setText("Asset Type: "+ repodata.getAssetType());
        holder.count.setText("Occurance count: " + repodata.getOccurance());
        holder.description.setText("Description: " + repodata.getFaultDecription());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
