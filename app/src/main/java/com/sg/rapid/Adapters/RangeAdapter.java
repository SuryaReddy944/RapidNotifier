package com.sg.rapid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.filteredAlaramServices.Rangeresponse;
import com.sg.rapid.filteredAlaramServices.SubsystemResponse;

import java.util.List;

public class RangeAdapter  extends BaseAdapter {

    private Context mContext;
    private List<Rangeresponse> mData;
    private LayoutInflater mInflater;

    public RangeAdapter(Context mContext,List<Rangeresponse> mData){
        this.mContext = mContext;
        this.mData = mData;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Rangeresponse getItem(int i) {
        return mData.get(i);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mView = null;
        if(mView == null){
            mView = mInflater.inflate(R.layout.subsystem_row,null);

        }

        TextView subsysname = (TextView)mView.findViewById(R.id.subsysname);
        Rangeresponse msub = mData.get(i);
        subsysname.setText(msub.getRangeName());
        subsysname.setTypeface(CustomFonts.getNexaBold(mContext));
        return mView;
    }
}
