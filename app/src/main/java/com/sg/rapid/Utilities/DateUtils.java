package com.sg.rapid.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {

    public static String getDay(String utcdate){
        String day = "";
       try {

           final Date date = new Date();
           final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
           final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
           Date theDate = sdf.parse(utcdate);

           SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
           Calendar myCal = new GregorianCalendar();
           myCal.setTime(theDate);

           String monthName = new SimpleDateFormat("MMM").format(myCal.getTime());

           day = ""+ myCal.get(Calendar.DAY_OF_MONTH) + "  " + monthName;

       }catch (Exception e){
           e.printStackTrace();
       }
        return day;
    }



    public static String getTime(String utcdate){
        String time = "";
        String[] dateArray = utcdate.split("T");
        time = dateArray[1];
        return time;
    }
}
