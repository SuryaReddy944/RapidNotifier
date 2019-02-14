package com.sg.rapid.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Helper {

    public static boolean hasNetworkConnection(Context mContext) {
        boolean isConnectedWifi = false;
        boolean isConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase( "WIFI" ))
                if (ni.isConnected())
                    isConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase( "MOBILE" ))
                if (ni.isConnected())
                    isConnectedMobile = true;
        }
        return isConnectedWifi || isConnectedMobile;
    }


    public static void CompressImage(String path){
        try {
            InputStream in = new FileInputStream(path);
            Bitmap bm2 = BitmapFactory.decodeStream(in);
            OutputStream stream = new FileOutputStream(path);
            bm2.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Bitmap rotateImage(String path) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return rotateImage(bitmap,path);
    }

    public static Bitmap rotateImage(Bitmap bitmap,String path) throws IOException {
        int rotate = 0;
        ExifInterface exif;
        exif = new ExifInterface(path);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }



}
