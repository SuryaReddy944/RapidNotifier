package com.sg.rapid.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.rapid.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private LinearLayout llalaram,llevents,llfilter;
    private TextView lblalaram,lblalaramcount,lblevents,lbleventcount,lblfilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.notifications_frag, null);

        this.initViews(mView);
        loadFragment(new AlaramFragment());


        return mView;
    }


    private void initViews(View view){
        llalaram = (LinearLayout)view.findViewById(R.id.llalaram);
        llevents = (LinearLayout)view.findViewById(R.id.llevents);
        llfilter = (LinearLayout)view.findViewById(R.id.llfilter);

        lblalaram = (TextView)view.findViewById(R.id.lblalaram);
        lblalaramcount = (TextView)view.findViewById(R.id.lblalaramcount);
        lblevents = (TextView)view.findViewById(R.id.lbevents);
        lbleventcount = (TextView)view.findViewById(R.id.lbleventscount);
        lblfilter = (TextView)view.findViewById(R.id.lblfilter);


        llalaram.setOnClickListener(myClicklistner);
        llevents.setOnClickListener(myClicklistner);
        llfilter.setOnClickListener(myClicklistner);
    }


    private View.OnClickListener myClicklistner = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            switch (v.getId()) {
                case R.id.llalaram:
                    llalaram.setBackgroundResource(R.drawable.tab_selected);
                    llevents.setBackgroundResource(R.drawable.non_selected_tab);
                    llfilter.setBackgroundResource(R.drawable.non_selected_tab);

                    lblalaram.setTextColor(getResources().getColor(R.color.block));
                    lblalaramcount.setBackgroundResource(R.drawable.alaram_count);

                    lblevents.setTextColor(getResources().getColor(R.color.block));
                    lbleventcount.setBackgroundResource(R.drawable.non_selected_count);
                    lblfilter.setTextColor(getResources().getColor(R.color.block));
                    loadFragment(new AlaramFragment());

                    break;

                case R.id.llevents:

                    llalaram.setBackgroundResource(R.drawable.non_selected_tab);
                    llevents.setBackgroundResource(R.drawable.tab_selected);
                    llfilter.setBackgroundResource(R.drawable.non_selected_tab);

                    lblalaram.setTextColor(getResources().getColor(R.color.block));
                    lblalaramcount.setBackgroundResource(R.drawable.non_selected_count);

                    lblevents.setTextColor(getResources().getColor(R.color.block));
                    lbleventcount.setBackgroundResource(R.drawable.alaram_count);
                    lblfilter.setTextColor(getResources().getColor(R.color.block));
                    loadFragment(new EventsFragment());

                    break;
                case R.id.llfilter:

                    llalaram.setBackgroundResource(R.drawable.non_selected_tab);
                    llevents.setBackgroundResource(R.drawable.non_selected_tab);
                    llfilter.setBackgroundResource(R.drawable.tab_selected);

                    lblalaram.setTextColor(getResources().getColor(R.color.block));
                    lblalaramcount.setBackgroundResource(R.drawable.non_selected_count);

                    lblevents.setTextColor(getResources().getColor(R.color.block));
                    lbleventcount.setBackgroundResource(R.drawable.non_selected_count);
                    lblfilter.setTextColor(getResources().getColor(R.color.block));

                    loadFragment(new FilterFragment());
                    break;

                default:
                    break;
            }
        }
    };


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tabs_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}