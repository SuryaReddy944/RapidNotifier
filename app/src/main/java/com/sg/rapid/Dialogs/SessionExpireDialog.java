package com.sg.rapid.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sg.rapid.Activity.SplashScreen;
import com.sg.rapid.FireBaseServices.Config;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

public class SessionExpireDialog extends Dialog implements
        View.OnClickListener {


    public Activity c;
    private TextView mTitle,mDes;
    private Button mGo;
    private SharedPreferences pref;
    public SessionExpireDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        pref  = PreferenceManager.getDefaultSharedPreferences(c);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.session_expire);
        initViews();
        applyFonts();



    }

    private void initViews() {
        mTitle = (TextView)findViewById(R.id.promotitle);
        mDes = (TextView)findViewById(R.id.promodes);


        mGo = (Button)findViewById(R.id.buttongo);

        mGo.setOnClickListener(this);



    }

    private void applyFonts() {
        mTitle.setTypeface(CustomFonts.getNexaBold(c));
        mDes.setTypeface(CustomFonts.getNexaRegular(c));
        mGo.setTypeface(CustomFonts.getNexaRegular(c));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttongo:
                dismiss();
                SharedPreferences.Editor mEditor = pref.edit();
                mEditor.putString("token","");
                mEditor.putString("UserType","");
                mEditor.apply();
                Intent mSplash = new Intent(c, SplashScreen.class);
                c.startActivity(mSplash);
                c.finish();

                break;


            default:
                break;
        }
        //dismiss();
    }
}
