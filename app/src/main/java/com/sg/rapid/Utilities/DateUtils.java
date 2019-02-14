package com.sg.rapid.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String getDay(String utcdate){
        String day = "";
       try {

           final Date date = new Date();
           final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
           final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
           Date theDate = sdf.parse(utcdate);

           SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
           month_date.setTimeZone(TimeZone.getDefault());
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

        try
        {   String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(utcdate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy'T'HH:mm a"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            utcdate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
            String[] dateArray = utcdate.split("T");
            time = dateArray[1];
        }
        catch (Exception e)
        {e.printStackTrace();
            utcdate = "00-00-0000 00:00";
        }
        return time;
    }


    public static String getFirstDay(Date d) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(dddd);
    }

    public static  String getCurrentDate(Date d)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    public static String getLapseMonths(Date d,int numberofmonths){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, - numberofmonths);
        Date result = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(result);

    }
}
