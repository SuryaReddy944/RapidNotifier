package com.sg.rapid.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sg.rapid.Adapters.AdapterSectionRecycler;
import com.sg.rapid.Adapters.SectionHeader;
import com.sg.rapid.Models.AlaramData;
import com.sg.rapid.R;

import java.util.ArrayList;
import java.util.List;

public class AlaramFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSectionRecycler adapterRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.alaram_frag, null);
        //initialize RecyclerView
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        //setLayout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        List<AlaramData> childList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            AlaramData dummy = new AlaramData();

            if (i == 0) {
                dummy.setDate("24 Oct");
                dummy.setTime("07:45:32");
                dummy.setTitle("AHU 1-1 Trip Alarm");
                dummy.setDescription("AHU 1-1 Tripped");
                dummy.setStatus("normal");
                dummy.setInfo("Tripped");
            } else if (i == 1) {
                dummy.setDate("");
                dummy.setTime("05:32:59");
                dummy.setTitle("MFGR Room Temp High");
                dummy.setDescription("Manufacturing Room Temp High Alarm\n" +
                        "ACKED 07:31:02 by Jernigan Loh\n" +
                        "NOTES: Production Operator left door ajar. Informed\n" +
                        "Shift supervisor to close door.");
                dummy.setStatus("low");
                dummy.setInfo("22.6 °C");
            } else if (i == 2) {


                dummy.setDate("12 Sep");
                dummy.setTime("07:45:32");
                dummy.setTitle("CH-1 DP Rtn to Normal");
                dummy.setDescription("Chiller 1 Differential Pressure Return to Normal\n" +
                        "ACKED 14:34:02 by Ginda Pohan");
                dummy.setStatus("high");
                dummy.setInfo("8.61 kPa");


            } else {

                dummy.setDate("12 Sep");
                dummy.setTime("07:45:32");
                dummy.setTitle("CH-1 DP Rtn to Normal");
                dummy.setDescription("Chiller 1 Differential Pressure Return to Normal\n" +
                        "ACKED 14:34:02 by Ginda Pohan");
                dummy.setStatus("low");
                dummy.setInfo("8.61 kPa");

            }

            childList.add(dummy);
        }


        //Create a List of Section DataModel implements Section
        List<SectionHeader> sections = new ArrayList<>();
        sections.add(new SectionHeader(childList, "2018", 1));


        adapterRecycler = new AdapterSectionRecycler(getActivity(), sections);
        recyclerView.setAdapter(adapterRecycler);


        return mView;
    }
}