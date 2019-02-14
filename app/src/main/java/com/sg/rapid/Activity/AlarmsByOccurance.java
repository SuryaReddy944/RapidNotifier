package com.sg.rapid.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.Adapters.RangeAdapter;
import com.sg.rapid.Adapters.SeverityAdapter;
import com.sg.rapid.Adapters.SubsysAdapter;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.R;
import com.sg.rapid.ReportServices.OccuranceRequest;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.DateUtils;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.SpinnerManager;
import com.sg.rapid.filteredAlaramServices.Rangeresponse;
import com.sg.rapid.filteredAlaramServices.SeverityResponse;
import com.sg.rapid.filteredAlaramServices.SubsystemResponse;
import com.sg.rapid.filteredAlaramServices.SubsystemService;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

public class AlarmsByOccurance extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{
    public BottomNavigationView navigationfilteredalarms;
    private TextView mlblfiltered,mlblsubsystem,mlblseverity,mdaterange,lblstart,lblend;
    private static TextView startdate,enddate;
    private Spinner mSubsystemSpinner,mSeveritySpinner,mDaterangeSpinner;
    private LinearLayout llstartend;
    private SeverityAdapter severityAdapter;
    private RangeAdapter rangeAdapter;
    private  String selectedDateRangeitem;
    private List<SubsystemResponse> mSubsysData;
    private SubsysAdapter subsysAdapter;
    private String selectedsubsys,selectedseverity,selectedrange,daterangestart,daterangeend;
    private Button mViewAlarms;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;
    private UserProfileResponse mProfileResponse;

    private String base64data = "";
    private byte[] imageBytes;
    private File serverimage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmsby_occurance);

        navigationfilteredalarms = findViewById(R.id.navigationalarmbyoccurance);
        View view = navigationfilteredalarms.findViewById(R.id.navigation_reports);
        view.performClick();
        navigationfilteredalarms.setOnNavigationItemSelectedListener(this);
        initViews();
        applyFonts(this);
        loadSeveritySpinner();
        loadRangeSpinner();
        getSubsystems(this);
        mSubsystemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SubsystemResponse subsys = subsysAdapter.getItem(i);
                selectedsubsys =   String.valueOf(subsys.getFaultsTypeId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSeveritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SeverityResponse severity = severityAdapter.getItem(i);
                selectedseverity = severity.getPriority();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDaterangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Rangeresponse item = rangeAdapter.getItem(i);
                selectedrange = item.getRangeName();
                if(selectedrange.equalsIgnoreCase("Custom Range")){
                    llstartend.setVisibility(View.VISIBLE);
                }else {
                    llstartend.setVisibility(View.GONE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.startdate:
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                    break;

                case R.id.enddate:
                    if (startdate.getText().toString() != "") {
                        DialogFragment newFragmentend = new DatePickerFragmentEnd();
                        newFragmentend.show(getSupportFragmentManager(), "datePicker");
                    } else {
                        Toast.makeText(AlarmsByOccurance.this, "Please select start date", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.btnview:
                    onClickofViewButton();

                    break;
                default:
                    break;
            }
        }
    };


    private void onClickofViewButton(){
        if(selectedsubsys.equalsIgnoreCase("0")){
            Toast.makeText(AlarmsByOccurance.this,"Please select subsystem",Toast.LENGTH_LONG).show();
            return;
        }

        if(selectedseverity.equalsIgnoreCase("")){
            Toast.makeText(AlarmsByOccurance.this,"Please select severity",Toast.LENGTH_LONG).show();
            return;
        }

        if(selectedrange.equalsIgnoreCase("Select Range")){
            Toast.makeText(AlarmsByOccurance.this,"Please select daterange",Toast.LENGTH_LONG).show();
            return;

        }

        if(selectedrange.equalsIgnoreCase("Select Range")){
            Toast.makeText(AlarmsByOccurance.this,"Please select daterange",Toast.LENGTH_LONG).show();
            return;
        }

        if(selectedrange.equalsIgnoreCase("Current Month")){

           try {
               daterangestart = DateUtils.getFirstDay(new Date());
               daterangeend = DateUtils.getCurrentDate(new Date());
           }catch (Exception e){
               e.printStackTrace();
           }
        }

        if(selectedrange.equalsIgnoreCase("Last 3 Months")){

            try {
                daterangestart = DateUtils.getLapseMonths(new Date(),3);
                daterangeend = DateUtils.getCurrentDate(new Date());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(selectedrange.equalsIgnoreCase("Last 6 Months")){

            try {
                daterangestart = DateUtils.getLapseMonths(new Date(),6);
                daterangeend = DateUtils.getCurrentDate(new Date());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(selectedrange.equalsIgnoreCase("One Year")){

            try {
                daterangestart = DateUtils.getLapseMonths(new Date(),12);
                daterangeend = DateUtils.getCurrentDate(new Date());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(selectedrange.equalsIgnoreCase("Custom Range")){

            try {
                String start = startdate.getText().toString();
                String end = enddate.getText().toString();
                if(start != null && start.equalsIgnoreCase("")){
                    Toast.makeText(AlarmsByOccurance.this,"Please select start date",Toast.LENGTH_LONG).show();

                }

                if(end != null && end.equalsIgnoreCase("")){
                    Toast.makeText(AlarmsByOccurance.this,"Please select start date",Toast.LENGTH_LONG).show();

                }

                String formatstart[] =  start.split("-");

                String framatedstart = formatstart[2] + "-"+formatstart[1]+"-"+formatstart[0];
                String formatend[] =  end.split("-");

                String framatedend = formatend[2] + "-"+formatend[1]+"-"+formatend[0];

                daterangestart = framatedstart;
                daterangeend = framatedend;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        OccuranceRequest request = new OccuranceRequest();
        request.setStartDate(daterangestart);
        request.setEndDate(daterangeend);
        request.setFaultType(selectedsubsys);
        request.setPriority(selectedseverity);

       // Log.e("request data",""+request.toString());

        Intent mResults = new Intent(AlarmsByOccurance.this,OccuranceAlarmResult.class) ;
        mResults.putExtra("requestdate",request);
        startActivity(mResults);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();

    }

    private void initViews(){
        mlblfiltered  = (TextView)findViewById(R.id.lblfiltered);
        mlblsubsystem  = (TextView)findViewById(R.id.lblsubsys);
        mlblseverity = (TextView)findViewById(R.id.lblseverity);
        mdaterange  = (TextView)findViewById(R.id.lbldaterange);
        lblstart = (TextView)findViewById(R.id.start);
        lblend = (TextView)findViewById(R.id.end);
        startdate = (TextView)findViewById(R.id.startdate);
        enddate = (TextView)findViewById(R.id.enddate);
        mSubsystemSpinner = (Spinner) findViewById(R.id.spinnersubsystem);
        mSeveritySpinner = (Spinner) findViewById(R.id.spinnerseverity);
        mDaterangeSpinner = (Spinner) findViewById(R.id.spinnerdaterange);
        llstartend = (LinearLayout)findViewById(R.id.lldaterange);
        mViewAlarms = (Button)findViewById(R.id.btnview) ;

        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));
        
        startdate.setOnClickListener(onClickListener);
        enddate.setOnClickListener(onClickListener);
        mViewAlarms.setOnClickListener(onClickListener);

    }

    private void applyFonts(Context mContext){
        mlblsubsystem.setTypeface(CustomFonts.getNexaBold(mContext));
        mlblfiltered.setTypeface(CustomFonts.getNexaBold(mContext));
        mlblseverity.setTypeface(CustomFonts.getNexaBold(mContext));
        mdaterange.setTypeface(CustomFonts.getNexaBold(mContext));
        lblstart.setTypeface(CustomFonts.getNexaBold(mContext));
        lblend.setTypeface(CustomFonts.getNexaBold(mContext));
        startdate.setTypeface(CustomFonts.getNexaBold(mContext));
        enddate.setTypeface(CustomFonts.getNexaBold(mContext));
        mViewAlarms.setTypeface(CustomFonts.getNexaBold(mContext));
    }


    private void loadSeveritySpinner(){
        List<SeverityResponse> severityData = new ArrayList<>();

        SeverityResponse s1 = new SeverityResponse();
        s1.setSeverityNmae("Select Severity");
        s1.setPriority("");
        severityData.add(s1);

        SeverityResponse s2 = new SeverityResponse();
        s2.setSeverityNmae("Normal");
        s2.setPriority("0");
        severityData.add(s2);

        SeverityResponse s3 = new SeverityResponse();
        s3.setSeverityNmae("Low");
        s3.setPriority("1");
        severityData.add(s3);

        SeverityResponse s4 = new SeverityResponse();
        s4.setSeverityNmae("Medium");
        s4.setPriority("2");
        severityData.add(s4);


        SeverityResponse s5 = new SeverityResponse();
        s5.setSeverityNmae("High");
        s5.setPriority("3");
        severityData.add(s5);

        severityAdapter = new SeverityAdapter(this,severityData);
        mSeveritySpinner.setAdapter(severityAdapter);
    }

    private void loadRangeSpinner(){
        List<Rangeresponse> rangedata = new ArrayList<>();

        Rangeresponse m1 = new Rangeresponse();
        m1.setRangeName("Select Range");
        rangedata.add(m1);
        Rangeresponse m2 = new Rangeresponse();
        m2.setRangeName("Current Month");
        rangedata.add(m2);

        Rangeresponse m3 = new Rangeresponse();
        m3.setRangeName("Last 3 Months");
        rangedata.add(m3);

        Rangeresponse m4 = new Rangeresponse();
        m4.setRangeName("Last 6 Months");
        rangedata.add(m4);

        Rangeresponse m5 = new Rangeresponse();
        m5.setRangeName("One Year");
        rangedata.add(m5);

        Rangeresponse m6 = new Rangeresponse();
        m6.setRangeName("Custom Range");
        rangedata.add(m6);



        rangeAdapter = new RangeAdapter(AlarmsByOccurance.this,rangedata);
        mDaterangeSpinner.setAdapter(rangeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View home = HomePage.navigation.findViewById(R.id.navigation_home);
                home.performClick();
                break;

            case R.id.navigation_explore:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View reports = HomePage.navigation.findViewById(R.id.navigation_explore);
                reports.performClick();
                break;

            case R.id.navigation_reports:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                break;
            case R.id.navigation_profile:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View profile = HomePage.navigation.findViewById(R.id.navigation_profile);
                profile.performClick();


                break;
        }

        return false;
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            // dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int months = month + 1;
            String selected = day + "-" + months + "-" + year;
            startdate.setText(selected);
            enddate.setText("");
        }
    }

    public static class DatePickerFragmentEnd extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            Calendar mycal = Calendar.getInstance();
            String mdate = startdate.getText().toString();
            String[] dateparts = mdate.split("-");
            int mday = Integer.parseInt(dateparts[0]) ;
            int mmonth = Integer.parseInt(dateparts[1])-1;
            int myear = Integer.parseInt(dateparts[2]);
            mycal.set(myear, mmonth, mday);//Year,Mounth -1,Day
            dialog.getDatePicker().setMinDate(mycal.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int months = month + 1;
            String selected = day + "-" + months + "-" + year;
            enddate.setText(selected);
        }
    }


    private void getSubsystems(final Context mContext){
        SpinnerManager.showSpinner(mContext,"Loading...");

        SubsystemService.fetchSubsystems(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<SubsystemResponse>> mres = (Response<List<SubsystemResponse>>)response;
                mSubsysData = mres.body();
                SubsystemResponse select = new SubsystemResponse();
                select.setFaultsType("Select Subsystem");
                select.setFaultsTypeId(0);
                mSubsysData.add(0,select);
                subsysAdapter = new SubsysAdapter(mContext,mSubsysData);
                mSubsystemSpinner.setAdapter(subsysAdapter);


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

    private void getProfile(final Context mContext) {
        // SpinnerManager.showSpinner(mContext, "Loading");
        UserProfileService.fetchProfile(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                // SpinnerManager.hideSpinner(mContext);
                Response<List<UserProfileResponse>> mres = (Response<List<UserProfileResponse>>) response;
                List<UserProfileResponse> mData = mres.body();
                mProfileResponse = mData.get(0);
                mUsername.setText(mProfileResponse.getUserName());

                try {
                    base64data = mProfileResponse.getProfilepicture().toString();
                    if (base64data != null && base64data != "") {
                        imageBytes = Base64.decode(base64data, Base64.DEFAULT);
                        serverimage = new File(mContext.getCacheDir(), "Profilepic");
                        serverimage.createNewFile();

                        FileOutputStream fos = new FileOutputStream(serverimage);
                        fos.write(imageBytes);
                        fos.flush();
                        fos.close();
                        ImageCapture.loadprofilepic(Uri.fromFile(serverimage),mProfilepic,mContext);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                // Toast.makeText(UpdateProfile.this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();

            }
        });

    }
}
