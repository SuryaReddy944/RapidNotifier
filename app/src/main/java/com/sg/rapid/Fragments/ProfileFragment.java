package com.sg.rapid.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.rapid.Activity.AlaramDetailedPage;
import com.sg.rapid.Activity.ChangePassword;
import com.sg.rapid.Activity.SplashScreen;
import com.sg.rapid.Activity.UpdateProfile;
import com.sg.rapid.Dialogs.LogOutDialog;
import com.sg.rapid.FireBaseServices.Config;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

public class ProfileFragment extends Fragment {

    private SharedPreferences pref;
    private Button mUpdateProfile,mChangepass,mLogout;
    private TextView mLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.profile_frag, null);
        mUpdateProfile = (Button)mView.findViewById(R.id.updateprofile);
        mChangepass = (Button)mView.findViewById(R.id.updatepass);
        mLogout = (Button) mView.findViewById(R.id.logout);
        mLabel = (TextView)mView.findViewById(R.id.label);
        mUpdateProfile.setTypeface(CustomFonts.getNexaBold(getContext()));
        mChangepass.setTypeface(CustomFonts.getNexaBold(getContext()));
        mLogout.setTypeface(CustomFonts.getNexaBold(getContext()));
        mLabel.setTypeface(CustomFonts.getNexaBold(getContext()));

        pref  = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);



        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateprofile = new Intent(getActivity(), UpdateProfile.class);
                startActivity(updateprofile);
                getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        mChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changepass = new Intent(getActivity(), ChangePassword.class);
                startActivity(changepass);
                getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOutDialog mdialog = new LogOutDialog(getActivity());
                mdialog.show();
            }
        });


        return mView;
    }
}