package com.sg.rapid.ReportServices;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.NoticeServices.CreateUserNotice;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class OccuranceService  extends BaseService {


    public static  void getResults(final OccuranceRequest reqdata, final ResponseListner mResponseListner){
        OccuranceResultsInterface anInterface = retrofit.create(OccuranceResultsInterface.class);
        Call<List<ReportResponse>> mCall = anInterface.fetchResults(reqdata);
        mCall.enqueue(new Callback<List<ReportResponse>>() {
            @Override
            public void onFailure(Call<List<ReportResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<ReportResponse>> call, Response<List<ReportResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }


    public static  void getSeverityResults(final OccuranceRequest reqdata, final ResponseListner mResponseListner){
        SeverityResultsInterface anInterface = retrofit.create(SeverityResultsInterface.class);
        Call<List<SeverityResultResponse>> mCall = anInterface.fetchSeverityResults(reqdata);
        mCall.enqueue(new Callback<List<SeverityResultResponse>>() {
            @Override
            public void onFailure(Call<List<SeverityResultResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<SeverityResultResponse>> call, Response<List<SeverityResultResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }


    public static  void getSubsystemResults(final OccuranceRequest reqdata, final ResponseListner mResponseListner){
        SubsystemResultsInterface anInterface = retrofit.create(SubsystemResultsInterface.class);
        Call<List<SeverityResultResponse>> mCall = anInterface.fetchSubsysResults(reqdata);
        mCall.enqueue(new Callback<List<SeverityResultResponse>>() {
            @Override
            public void onFailure(Call<List<SeverityResultResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<SeverityResultResponse>> call, Response<List<SeverityResultResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }



    public static void getFilteredResult(final OccuranceRequest reqdata, final ResponseListner mResponseListner){
        SubsystemResultsInterface anInterface = retrofit.create(SubsystemResultsInterface.class);
        Call<List<SeverityResultResponse>> mCall = anInterface.fetchSubsysResults(reqdata);
        mCall.enqueue(new Callback<List<SeverityResultResponse>>() {
            @Override
            public void onFailure(Call<List<SeverityResultResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<SeverityResultResponse>> call, Response<List<SeverityResultResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });
    }

    public interface SubsystemResultsInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("GetAlarmRptWithDateAndFType")
        Call<List<SeverityResultResponse>> fetchSubsysResults(@Body OccuranceRequest data);
    }
    public interface SeverityResultsInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("GetAlarmRptWithDateAndSeverity")
        Call<List<SeverityResultResponse>> fetchSeverityResults(@Body OccuranceRequest data);
    }

    public interface OccuranceResultsInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("GetAlarmRptWithDateAndFTypeAndSeverityByOccurance")
        Call<List<ReportResponse>> fetchResults(@Body OccuranceRequest data);
    }


    public interface FilteredeResultsInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("GetAlarmRptWithDateAndFTypeAndSeverity")
        Call<List<SeverityResultResponse>> fetchResults(@Body OccuranceRequest data);
    }
}
