package com.sg.rapid.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sg.rapid.AcknowledgeServices.AckInfo;
import com.sg.rapid.Activity.SplashScreen;
import com.sg.rapid.FireBaseServices.Config;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

public class LogOutDialog extends Dialog implements
        View.OnClickListener {


    public Activity c;
    private TextView mTitle,mDes;
    private Button mCancel,mGo;
    private SharedPreferences pref;
    public LogOutDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        pref  = c.getSharedPreferences(Config.SHARED_PREF, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logout_dialog);
        initViews();
        applyFonts();



    }

    private void initViews() {
        mTitle = (TextView)findViewById(R.id.promotitle);
        mDes = (TextView)findViewById(R.id.promodes);

        mCancel = (Button)findViewById(R.id.buttoncancel);
        mGo = (Button)findViewById(R.id.buttongo);
        mCancel.setOnClickListener(this);
        mGo.setOnClickListener(this);



    }

    private void applyFonts() {
        mTitle.setTypeface(CustomFonts.getNexaBold(c));
        mDes.setTypeface(CustomFonts.getNexaRegular(c));

        mCancel.setTypeface(CustomFonts.getNexaRegular(c));
        mGo.setTypeface(CustomFonts.getNexaRegular(c));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttoncancel:
                dismiss();
                break;
            case R.id.buttongo:
                dismiss();
                SharedPreferences.Editor mEditor = pref.edit();
                mEditor.putString("token","");
                mEditor.commit();
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
