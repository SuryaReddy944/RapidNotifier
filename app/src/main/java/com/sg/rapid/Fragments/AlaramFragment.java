package com.sg.rapid.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sg.rapid.Adapters.AdapterSectionRecycler;
import com.sg.rapid.Adapters.SectionHeader;
import com.sg.rapid.Models.AlaramData;
import com.sg.rapid.R;

import java.util.ArrayList;
import java.util.List;

public class AlaramFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSectionRecycler adapterRecycler;
    private FirebaseDatabase mFirebaseDatabase ;
    private DatabaseReference mDatabaseReference;
    private  List<SectionHeader> sections;
    private   List<AlaramData> childList ;

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
        sections = new ArrayList<>();
        childList = new ArrayList<>();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();


        // Read from the database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("", "onChildChanged:" + dataSnapshot.getKey());
                sections.clear();
                 childList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    AlaramData    alaramData =  postSnapshot.getValue(AlaramData.class);
                   int x = 0;
                    // here you can access to name property like university.name
                    childList.add(alaramData);
                }


                Log.d("", "Value is: " + childList);
                //Create a List of Section DataModel implements Section

                sections.add(new SectionHeader(childList, "2018", 1));
                adapterRecycler.notifyDataChanged(sections);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });



        adapterRecycler = new AdapterSectionRecycler(getActivity(), sections);
        recyclerView.setAdapter(adapterRecycler);
        /*List<AlaramData> childList = new ArrayList<>();

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
*/




        return mView;
    }
}