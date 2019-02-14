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
import android.widget.TextView;

import com.sg.rapid.Activity.AlarmsByOccurance;
import com.sg.rapid.Activity.AlarmsBySeverity;
import com.sg.rapid.Activity.AlarmsBySubsystem;
import com.sg.rapid.Activity.FilteredAlarms;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

public class ReportsFragment extends Fragment {


    private Button filteredalarm,alarmbyoccurance,alarmbysubsystem,alarmbyseverity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.reports_frag, null);

          //intialize views
        filteredalarm = (Button)mView.findViewById(R.id.buttonfilteredalarms);
        alarmbyoccurance  = (Button)mView.findViewById(R.id.buttonalarmbyoccurance);
        alarmbysubsystem  = (Button)mView.findViewById(R.id.buttonalarmbysubsystem);
        alarmbyseverity  = (Button)mView.findViewById(R.id.alarmbyseverity);

        //set fonts
        alarmbyoccurance.setTypeface(CustomFonts.getNexaBold(getActivity()));
        alarmbysubsystem.setTypeface(CustomFonts.getNexaBold(getActivity()));
        filteredalarm.setTypeface(CustomFonts.getNexaBold(getActivity()));
        alarmbyseverity.setTypeface(CustomFonts.getNexaBold(getActivity()));

        //set onclick event
        filteredalarm.setOnClickListener(myClicklistner);
        alarmbyoccurance.setOnClickListener(myClicklistner);
        alarmbysubsystem.setOnClickListener(myClicklistner);
        alarmbyseverity.setOnClickListener(myClicklistner);
        return mView;
    }


    private View.OnClickListener myClicklistner = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            switch (v.getId()) {
                case R.id.buttonfilteredalarms:
                    Intent filteralarms = new Intent(getActivity(), FilteredAlarms.class);
                    startActivity(filteralarms);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    break;


                case R.id.buttonalarmbyoccurance:
                    Intent alarmsbyoccurance = new Intent(getActivity(), AlarmsByOccurance.class);
                    startActivity(alarmsbyoccurance);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    break;

                case R.id.alarmbyseverity:
                    Intent AlarmsBySeverity = new Intent(getActivity(), AlarmsBySeverity.class);
                    startActivity(AlarmsBySeverity);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    break;

                case R.id.buttonalarmbysubsystem:
                    Intent AlarmsBySubsystem = new Intent(getActivity(), AlarmsBySubsystem.class);
                    startActivity(AlarmsBySubsystem);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    break;
                default:
                    break;
            }
        }
    };
}