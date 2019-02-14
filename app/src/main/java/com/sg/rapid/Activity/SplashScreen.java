package com.sg.rapid.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.sg.rapid.FireBaseServices.Config;
import com.sg.rapid.R;
/**
 * Created by Surya on 24-10-2018.
 */
public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 10;
    private  static SharedPreferences pref;
    public static String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        pref  = PreferenceManager.getDefaultSharedPreferences(this);
        userType = pref.getString("UserType","");

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                String accesstoken = pref.getString("token","");
                if(accesstoken !=null && !accesstoken.equalsIgnoreCase("")){
                    Intent homepage = new Intent(SplashScreen.this,HomePage.class);
                    startActivity(homepage);
                    finish();
                }else{
                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public  static String getAccessToken(){
        String token = "";
        token = pref.getString("token","");
        return token;
    }

}

