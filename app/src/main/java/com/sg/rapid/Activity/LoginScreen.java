package com.sg.rapid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;

/**
 * Created by Surya on 25-10-2018.
 */
public class LoginScreen extends AppCompatActivity {

    private TextView mLabel, mForgetpass;

    private EditText mEmail, mPass;

    private Button mSignIn;

    private CheckBox mRememberme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        this.initViews();
        this.applyFonts();
    }

    private void initViews() {
        mLabel = (TextView) findViewById(R.id.txtlabel);
        mForgetpass = (TextView) findViewById(R.id.forgetpass);
        mEmail = (EditText) findViewById(R.id.editTextemail);
        mPass = (EditText) findViewById(R.id.editTextpass);
        mSignIn = (Button) findViewById(R.id.buttonsignin);
        mRememberme = (CheckBox) findViewById(R.id.rememberme);
        mForgetpass.setOnClickListener(myClicklistner);
        mSignIn.setOnClickListener(myClicklistner);

    }

    private void applyFonts() {
        mLabel.setTypeface(CustomFonts.getNexaBold(this));
        mForgetpass.setTypeface(CustomFonts.getNexaBold(this));
        mEmail.setTypeface(CustomFonts.getNexaRegular(this));
        mPass.setTypeface(CustomFonts.getNexaRegular(this));
        mSignIn.setTypeface(CustomFonts.getNexaRegular(this));
        mRememberme.setTypeface(CustomFonts.getNexaRegular(this));

    }

    private View.OnClickListener myClicklistner = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            switch (v.getId()) {
                case R.id.buttonsignin:
                    doLogin();

                    break;
                default:
                    break;
            }
        }
    };


    private void doLogin(){
        String email = mEmail.getText().toString();
        String mpass = mPass.getText().toString();

        if(email.isEmpty()){
            mEmail.setError("Email is mandatory.");
            mEmail.requestFocus();
            return;
        }
        if(mpass.isEmpty()){
            mPass.setError("Please enter password.");
            mPass.requestFocus();
            return;
        }

        Intent homepage = new Intent(LoginScreen.this,HomePage.class);
        startActivity(homepage);
        finish();

    }

}
