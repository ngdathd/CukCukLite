package com.ngdat.cukcuklite.data.models;

import android.content.Context;

import java.io.Serializable;
import java.util.Date;

import com.ngdat.cukcuklite.CukCukLiteApplication;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.cukcukenum.ParamReportEnum;
import com.ngdat.cukcuklite.utils.DateUtil;

/**
 * Đối tượng các loại báo các
 * Created at 17/04/2019
 */
public class ParamReport implements Serializable {

    private Date fromDate;
    private boolean isSelected;
    private ParamReportEnum paramType;
    private String titleReportDetail;
    private Date toDate;

    public ParamReport(ParamReportEnum paramType) {
        this.paramType = paramType;
        setData();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ParamReportEnum getParamType() {
        return paramType;
    }

    public void setParamType(ParamReportEnum paramType) {
        this.paramType = paramType;
    }

    public String getTitleReportDetail() {
        return titleReportDetail;
    }

    public void setTitleReportDetail(String titleReportDetail) {
        this.titleReportDetail = titleReportDetail;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    private void setData() {
        Date[] dates = new Date[2];
        Context context = CukCukLiteApplication.getInstance();
        switch (paramType) {
            case OTHER:
                titleReportDetail = context.getString(R.string.param_report_other);
                break;
            case CURRENT:
                titleReportDetail = context.getString(R.string.param_report_current);
                break;
            case TODAY:
                titleReportDetail = context.getString(R.string.param_report_today);
                dates = DateUtil.getToday();
                break;
            case LAST_WEEK:
                titleReportDetail = context
                    .getString(R.string.param_report_last_week);
                dates = DateUtil.getLastWeek();
                break;
            case LAST_YEAR:
                titleReportDetail = context
                        .getString(R.string.param_report_last_year);
                dates = DateUtil.getLastYear();
                break;
            case THIS_WEEK:
                titleReportDetail = context
                        .getString(R.string.param_report_this_week);
                dates = DateUtil.getThisWeek();
                break;
            case THIS_YEAR:
                titleReportDetail = context
                        .getString(R.string.param_report_this_year);
                dates = DateUtil.getThisYear();
                break;
            case YESTERDAY:
                titleReportDetail = context
                        .getString(R.string.param_report_yesterday);
                dates = DateUtil.getYesterday();
                break;
            case LAST_MONTH:
                titleReportDetail = context
                        .getString(R.string.param_report_last_month);
                dates = DateUtil.getLastMonth();
                break;
            case THIS_MONTH:
                titleReportDetail = context
                        .getString(R.string.param_report_this_month);
                dates = DateUtil.getThisMonth();
                break;
        }
        fromDate = dates[0];
        toDate = dates[1];
    }
}
