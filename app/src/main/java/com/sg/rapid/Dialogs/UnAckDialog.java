package com.sg.rapid.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sg.rapid.Activity.SplashScreen;
import com.sg.rapid.AlaramService.UnAckRequest;
import com.sg.rapid.AlaramService.UnAcknowledgeService;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.Fragments.AlaramFragment;
import com.sg.rapid.Fragments.EventsFragment;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.SpinnerManager;

public class UnAckDialog extends Dialog implements
        View.OnClickListener {


    public Activity c;
    private TextView mTitle, mDes;
    private Button mCancel, buttonproceed;
    private SharedPreferences pref;
    private String id;
    private boolean isAlarm ;

    public UnAckDialog(Activity a, String id,boolean isAlarm) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        this.id = id;
        this.isAlarm = isAlarm;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unack_dialog);
        initViews();
        applyFonts();


    }

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.promotitle);
        mDes = (TextView) findViewById(R.id.promodes);

        mCancel = (Button) findViewById(R.id.buttoncancel);
        buttonproceed = (Button) findViewById(R.id.buttonproceed);
        mCancel.setOnClickListener(this);
        buttonproceed.setOnClickListener(this);


    }

    private void applyFonts() {
        mTitle.setTypeface(CustomFonts.getNexaBold(c));
        mDes.setTypeface(CustomFonts.getNexaRegular(c));

        mCancel.setTypeface(CustomFonts.getNexaRegular(c));
        buttonproceed.setTypeface(CustomFonts.getNexaRegular(c));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttoncancel:
                dismiss();
                break;
            case R.id.buttonproceed:
                dismiss();
                UnAckRequest request = new UnAckRequest();
                request.setID(id);
                if(isAlarm){
                    request.setQType("un-ackalarms");
                }else {
                    request.setQType("un-ackevents");
                }

                request.setAckNotes("");
                sendUnAck(c,request);


                break;


            default:
                break;
        }
        //dismiss();
    }

    private void sendUnAck(final Context mContext, final UnAckRequest mRequest) {
        SpinnerManager.showSpinner(mContext, "Loading...");
        UnAcknowledgeService.unAcknowledge(mRequest, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                if(isAlarm){
                    AlaramFragment.isUnack = true;
                }else {
                    EventsFragment.isEventUnack =  true;
                }
                 c.finish();


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
}
