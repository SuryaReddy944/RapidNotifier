package com.sg.rapid.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.EventServices.EventsResponse;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.DateUtils;

import okhttp3.internal.Util;

public class EventDeatilsPage extends Activity {

    private ImageView mBack;
    private TextView mAlaramName,mAlaramType,mDescription,mCreateddate,mCreatedTime,mAckNotes,mTitle,ackdate,acktime;
    private EventsResponse mEventsResponse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alara_detailed_page);
        initViews();
        setFonts(this);
        mEventsResponse = (EventsResponse)getIntent().getSerializableExtra("eventInfo");

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAlaramName.setText("Event name : " +mEventsResponse.getAssetName());
        mAlaramType.setText("Event type : " +mEventsResponse.getAssetType());
        mDescription.setText("Event descrption : " +mEventsResponse.getEventDecription());
        mCreateddate.setText("Created date: " + DateUtils.getDay(mEventsResponse.getCreateDate()));
        mCreatedTime.setText("Created time : " +DateUtils.getTime(mEventsResponse.getCreateDate()));

        String note = (String) mEventsResponse.getAcknowledgeNotes();
        if(note != null){
            mAckNotes.setText("Acknowledgement notes: " + note);
            ackdate.setText("Acknowledgement date : " + DateUtils.getDay((String) mEventsResponse.getAcknowledgedOn()));
            acktime.setText("Acknowledgement time : " + DateUtils.getTime((String) mEventsResponse.getAcknowledgedOn()));

        }else{
            mAckNotes.setText("Acknowledgement notes: " + "NA");
            ackdate.setVisibility(View.GONE);
            acktime.setVisibility(View.GONE);
        }


    }

    private void initViews(){
        mBack = (ImageView) findViewById(R.id.backbutton);
        mAlaramName = (TextView)findViewById(R.id.alaramname);
        mAlaramType = (TextView)findViewById(R.id.alaramtype);
        mDescription = (TextView)findViewById(R.id.alaramdescription);
        mCreateddate = (TextView)findViewById(R.id.createddate);
        mCreatedTime = (TextView)findViewById(R.id.cretaedtime);
        mAckNotes = (TextView)findViewById(R.id.notesdata);
        mTitle = (TextView)findViewById(R.id.title);
        ackdate = (TextView)findViewById(R.id.ackdate);
        acktime = (TextView)findViewById(R.id.acktime);
    }
    private void setFonts(Context mContext){
        mAlaramName.setTypeface(CustomFonts.getNexaBold(mContext));
        mAlaramType.setTypeface(CustomFonts.getNexaBold(mContext));
        mDescription.setTypeface(CustomFonts.getNexaBold(mContext));
        mCreateddate.setTypeface(CustomFonts.getNexaBold(mContext));
        mCreatedTime.setTypeface(CustomFonts.getNexaBold(mContext));
        mAckNotes.setTypeface(CustomFonts.getNexaBold(mContext));
        mTitle.setTypeface(CustomFonts.getNexaBold(mContext));
        ackdate.setTypeface(CustomFonts.getNexaBold(mContext));
        acktime.setTypeface(CustomFonts.getNexaBold(mContext));
    }
}
