package com.sg.rapid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sg.rapid.NoticeServices.AdminNotice;
import com.sg.rapid.NoticeServices.AdminNotices;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.DateUtils;

import java.util.List;

public class AdminNoticeAdapter extends RecyclerView.Adapter<AdminNoticeAdapter.MyViewHolder> {

    private Context mContext;
    private List<AdminNotices> adminList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message,fromdate,todate;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.admintitle);
            message = (TextView) view.findViewById(R.id.adminmessage);
            fromdate = (TextView) view.findViewById(R.id.startdate);
            todate = (TextView) view.findViewById(R.id.enddate);

        }
    }


    public AdminNoticeAdapter(Context mContext, List<AdminNotices> adminList) {
        this.mContext = mContext;
        this.adminList = adminList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_notice_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        AdminNotices adminNotice = adminList.get(position);
        holder.title.setText(adminNotice.getTitle());
        holder.message.setText(adminNotice.getMessageBody());
        holder.fromdate.setText("From: " + DateUtils.getDay(adminNotice.getStartDate()));
        holder.todate.setText("To: " + DateUtils.getDay(adminNotice.getEndDate()));


    }


    @Override
    public int getItemCount() {
        return adminList.size();
    }

}

