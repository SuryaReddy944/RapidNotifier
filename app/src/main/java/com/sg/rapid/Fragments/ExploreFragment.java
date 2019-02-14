package com.sg.rapid.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sg.rapid.Activity.MyNoticeBoard;
import com.sg.rapid.Activity.SplashScreen;
import com.sg.rapid.R;

public class ExploreFragment extends Fragment {

    private Button mManageNotice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.explore_frag, null);

        mManageNotice = (Button)mView.findViewById(R.id.btnmanagenotice);

        if(!SplashScreen.userType.equalsIgnoreCase("A")){
            mManageNotice.setAlpha(.5f);
            mManageNotice.setClickable(false);
            mManageNotice.setTextColor(getActivity().getResources().getColor(R.color.grey));
            mManageNotice.setOnClickListener(myClicklistner);
        }else {
           // button.setAlpha(.5f);
           // button.setClickable(false);
            mManageNotice.setOnClickListener(myClicklistner);

        }



        return mView;
    }


    private View.OnClickListener myClicklistner = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            switch (v.getId()) {
                case R.id.btnmanagenotice:
                    if(!SplashScreen.userType.equalsIgnoreCase("A")){

                        Toast.makeText(getActivity(),"Sorry you are not authorized to access this option",Toast.LENGTH_LONG).show();
                    }else {
                        Intent mExplore = new Intent(getActivity(), MyNoticeBoard.class);
                        startActivity(mExplore);
                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }


                    break;



                default:
                    break;
            }
        }
    };

}