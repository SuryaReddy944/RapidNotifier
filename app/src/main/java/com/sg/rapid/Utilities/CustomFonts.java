package com.sg.rapid.Utilities;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Surya on 24-10-2018.
 */
public class CustomFonts {

    private static Typeface mTypeface;

    public static Typeface getNexaBold(Context mContext){

        mTypeface = Typeface.createFromAsset(mContext.getAssets(),"fontfabric-nexa-bold.ttf");
        return mTypeface;
    }


    public static Typeface getNexaRegular(Context mContext){

        mTypeface = Typeface.createFromAsset(mContext.getAssets(),"fontfabric-nexa-regular.ttf");
        return mTypeface;
    }
}
