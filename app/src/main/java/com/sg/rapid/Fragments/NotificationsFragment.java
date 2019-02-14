package com.sg.rapid.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.rapid.Adapters.NotificationAdapter;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.CountServices.AlarmCountResponse;
import com.sg.rapid.CountServices.CountService;
import com.sg.rapid.CountServices.EventCountResponse;
import com.sg.rapid.CustomControllers.FixedSpeedScroller;
import com.sg.rapid.Dialogs.SessionExpireDialog;
import com.sg.rapid.Models.Notify;
import com.sg.rapid.NoticeServices.AllNotices;
import com.sg.rapid.NoticeServices.NoticeService;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.SpinnerManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private LinearLayout llalaram, llevents, llfilter;
    public static LinearLayout lltopsection;
    private TextView lblalaram, lblevents, lblfilter;
    public static TextView lblalaramcount, lbleventcount, lbltitle;
    private TextView mPagerCount;
    public static Context mContext;
    private ArrayList<Notify> mNotidata;
    private NotificationAdapter mAdapter;
    public static ViewPager mViewPager;
    private List<AllNotices> mData;
    private ImageView mLefticon,mRighticon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.notifications_fragnew, null);

        this.initViews(mView);
        mContext = getActivity();
        loadFragment(new AlaramFragment());
        mNotidata = new ArrayList<>();
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        getAllNotices(getActivity());
        getUnackAlaramCounts();
        getUnackEventCounts();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int count= mViewPager.getCurrentItem()+1;
                mPagerCount.setText(""+count+"/"+mData.size());
                mRighticon.setVisibility(View.VISIBLE);
                if(count == 1){
                    mLefticon.setVisibility(View.GONE);

                }else {
                    mLefticon.setVisibility(View.VISIBLE);

                }

                if(count == mData.size()){
                    mLefticon.setVisibility(View.VISIBLE);
                    mRighticon.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        return mView;
    }


    private void initViews(View view) {
        llalaram = (LinearLayout) view.findViewById(R.id.llalaram);
        llevents = (LinearLayout) view.findViewById(R.id.llevents);
        llfilter = (LinearLayout) view.findViewById(R.id.llfilter);
        lltopsection = (LinearLayout) view.findViewById(R.id.layouttopsection);
        //lbltitle = (TextView) view.findViewById(R.id.title);
        lblalaram = (TextView) view.findViewById(R.id.lblalaram);
        lblalaramcount = (TextView) view.findViewById(R.id.lblalaramcount);
        lblevents = (TextView) view.findViewById(R.id.lbevents);
        lbleventcount = (TextView) view.findViewById(R.id.lbleventscount);
        lblfilter = (TextView) view.findViewById(R.id.lblfilter);
        mPagerCount = (TextView) view.findViewById(R.id.countpager);

        mLefticon = (ImageView)view.findViewById(R.id.left_nav) ;
        mRighticon = (ImageView)view.findViewById(R.id.right_nav) ;


        llalaram.setOnClickListener(myClicklistner);
        llevents.setOnClickListener(myClicklistner);
        llfilter.setOnClickListener(myClicklistner);

        //apply fonts
        lblalaram.setTypeface(CustomFonts.getNexaRegular(getActivity()));
        lblalaramcount.setTypeface(CustomFonts.geRegular(getActivity()));
        lblevents.setTypeface(CustomFonts.getNexaRegular(getActivity()));
        lbleventcount.setTypeface(CustomFonts.geRegular(getActivity()));
        lblfilter.setTypeface(CustomFonts.getNexaRegular(getActivity()));
       // lbltitle.setTypeface(CustomFonts.getNexaRegular(getActivity()));


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

                    lblevents.setTextColor(getResources().getColor(R.color.nontxtcolor));
                    lbleventcount.setBackgroundResource(R.drawable.non_selected_count);
                    lblfilter.setTextColor(getResources().getColor(R.color.block));
                    loadFragment(new AlaramFragment());

                    break;

                case R.id.llevents:

                    llalaram.setBackgroundResource(R.drawable.non_selected_tab);
                    llevents.setBackgroundResource(R.drawable.tab_selected);
                    llfilter.setBackgroundResource(R.drawable.non_selected_tab);

                    lblalaram.setTextColor(getResources().getColor(R.color.nontxtcolor));
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

    private void getAllNotices(final Context mContext) {
        SpinnerManager.showSpinner(mContext, "Loading...");
        NoticeService.fetchAllNotices(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<AllNotices>> mRes = (Response<List<AllNotices>>) response;
                mData = mRes.body();
                mAdapter = new NotificationAdapter(getActivity(), mData);
                mViewPager.setAdapter(mAdapter);
                mPagerCount.setText(""+mViewPager.getCurrentItem()+"/"+mData.size());

            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);

            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);

            }
        });
    }


    private void getUnackAlaramCounts() {
        CountService.fetchUnackAlarmCount(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {

                if(sttuscode == 401){
                    SessionExpireDialog mDialog = new SessionExpireDialog(getActivity());
                    mDialog.show();
                }else{
                    Response<List<AlarmCountResponse>> mRes = (Response<List<AlarmCountResponse>>)response;
                    List<AlarmCountResponse> mData = mRes.body();

                    AlarmCountResponse acr = mData.get(0);

                    if(acr.getAlarmCount() == 0){
                        lblalaramcount.setVisibility(View.GONE);
                    }else {
                        lblalaramcount.setText(String.valueOf(acr.getAlarmCount()));
                    }
                }


            }

            @Override
            public void onFailure(Throwable error) {
                lblalaramcount.setVisibility(View.GONE);
            }

            @Override
            public void failureResponse(Object response) {
                SessionExpireDialog mDialog = new SessionExpireDialog(getActivity());
                mDialog.show();
                lblalaramcount.setVisibility(View.GONE);
            }
        });
    }

    private void getUnackEventCounts() {
        CountService.fetchUnackEventCount(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
              Response<List<EventCountResponse>> mRes = (Response<List<EventCountResponse>>)response;
                List<EventCountResponse> mData = mRes.body();
                EventCountResponse evres = mData.get(0);

                if (evres.getEventCount() == 0){
                    lbleventcount.setVisibility(View.GONE);
                }else {
                    lbleventcount.setText(String.valueOf(evres.getEventCount()));
                }


            }

            @Override
            public void onFailure(Throwable error) {
                lbleventcount.setVisibility(View.GONE);
            }

            @Override
            public void failureResponse(Object response) {
                lbleventcount.setVisibility(View.GONE);
            }
        });
    }

}