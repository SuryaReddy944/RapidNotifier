package com.sg.rapid.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.NoticeServices.CreateUserNotice;
import com.sg.rapid.NoticeServices.NoticeService;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.SpinnerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

public class CreateNotice extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public BottomNavigationView navigationupdateprofile;
    private TextView  lblmynotice, mtitle, mDes, mStart, mEnd;
    public static TextView mStartDate, mEndDate;
    private Button mSave;
    private EditText mEditTitle, mEditDes;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;
    private UserProfileResponse mProfileResponse;

    private String base64data = "";
    private byte[] imageBytes;
    private File serverimage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_notice);

        navigationupdateprofile = findViewById(R.id.navigationcreatenotice);
        View view = navigationupdateprofile.findViewById(R.id.navigation_explore);
        view.performClick();
        navigationupdateprofile.setOnNavigationItemSelectedListener(this);
        initView();
        setFonts(this);


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnsave:
                    CreateUserNotice mNotice = new CreateUserNotice();
                    saveButtonCLicked(CreateNotice.this, mNotice);

                    break;

                case R.id.startdate:
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                    break;

                case R.id.enddate:
                    if (mStartDate.getText().toString() != "") {
                        DialogFragment newFragmentend = new DatePickerFragmentEnd();
                        newFragmentend.show(getSupportFragmentManager(), "datePicker");
                    } else {
                        Toast.makeText(CreateNotice.this, "Please select start date", Toast.LENGTH_LONG).show();
                    }


                    break;
                default:
                    break;
            }
        }
    };


    private void saveButtonCLicked(Context mContext, CreateUserNotice mUserNotice) {

        String title = mEditTitle.getText().toString();
        String message = mEditDes.getText().toString();
        String startdate = mStartDate.getText().toString();
        String enddate = mEndDate.getText().toString();

        if (title.isEmpty()) {
            mEditTitle.setError(getString(R.string.error_field_required));
            mEditTitle.requestFocus();
            return;
        }
        if (message.isEmpty()) {
            mEditDes.setError(getString(R.string.error_field_required));
            mEditDes.requestFocus();
            return;
        }

        if (startdate.isEmpty()) {
            Toast.makeText(CreateNotice.this, "Please select start date.", Toast.LENGTH_LONG).show();
            return;
        }

        if (enddate.isEmpty()) {
            Toast.makeText(CreateNotice.this, "Please select end date.", Toast.LENGTH_LONG).show();
            return;
        }

        String[] startdates = startdate.split("-");
        String[] enddates = enddate.split("-");

        String formatedstart = startdates[2] +"-" +startdates[1] +"-"+ startdates[0];
        String formatedend = enddates[2] +"-" +enddates[1] +"-"+ enddates[0];

        CreateUserNotice mNotice = new CreateUserNotice();
        mNotice.setTitle(title);
        mNotice.setMessage(message);
        mNotice.setStartDate(formatedstart);
        mNotice.setEndDate(formatedend);
        if(Helper.hasNetworkConnection(this)) {
            sendNotice(CreateNotice.this, mNotice);
        }else {
            Toast.makeText(this, R.string.noconnection, Toast.LENGTH_LONG).show();

        }


    }

    private void initView() {
        lblmynotice = (TextView) findViewById(R.id.lblmynotice);
        mtitle = (TextView) findViewById(R.id.lbltitle);
        mDes = (TextView) findViewById(R.id.lbldescription);
        mStart = (TextView) findViewById(R.id.start);
        mEnd = (TextView) findViewById(R.id.end);
        mStartDate = (TextView) findViewById(R.id.startdate);
        mEndDate = (TextView) findViewById(R.id.enddate);
        mEditTitle = (EditText) findViewById(R.id.editTextTitle);
        mEditDes = (EditText) findViewById(R.id.editTextcdes);

        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));

        mSave = (Button) findViewById(R.id.btnsave);
        mStartDate.setOnClickListener(onClickListener);
        mEndDate.setOnClickListener(onClickListener);
        mSave.setOnClickListener(onClickListener);

    }

    private void setFonts(Context mContext) {
        lblmynotice.setTypeface(CustomFonts.getNexaBold(mContext));
        mSave.setTypeface(CustomFonts.getNexaBold(mContext));
        mtitle.setTypeface(CustomFonts.getNexaBold(mContext));
        mDes.setTypeface(CustomFonts.getNexaBold(mContext));
        mStart.setTypeface(CustomFonts.getNexaBold(mContext));
        mEnd.setTypeface(CustomFonts.getNexaBold(mContext));
        mStartDate.setTypeface(CustomFonts.getNexaBold(mContext));
        mEndDate.setTypeface(CustomFonts.getNexaBold(mContext));
        mEditTitle.setTypeface(CustomFonts.getNexaBold(mContext));
        mEditDes.setTypeface(CustomFonts.getNexaBold(mContext));

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
                break;

            case R.id.navigation_reports:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View reports = HomePage.navigation.findViewById(R.id.navigation_reports);
                reports.performClick();
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
             dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int months = month + 1;
            String selected = day + "-" + months + "-" + year;
            mStartDate.setText(selected);
            mEndDate.setText("");
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
            String mdate = mStartDate.getText().toString();
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
            mEndDate.setText(selected);
        }
    }

    private void sendNotice(final Context mContext, CreateUserNotice mCreateNotice) {

        SpinnerManager.showSpinner(mContext, "Loading...");
        NoticeService.postNotice(mCreateNotice, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Toast.makeText(mContext,"Notice has been successfully added",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);

            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                Toast.makeText(mContext,"Server error, please try again later",Toast.LENGTH_LONG).show();


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
