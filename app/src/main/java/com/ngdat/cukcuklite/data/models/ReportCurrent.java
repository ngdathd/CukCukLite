package com.ngdat.cukcuklite.data.models;

import android.content.Context;

import java.util.Date;

import com.ngdat.cukcuklite.CukCukLiteApplication;
import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.cukcukenum.ParamReportEnum;
import com.ngdat.cukcuklite.utils.DateUtil;

/**
 * Đối tượng báo cáo gần đây
 * Created at 17/04/2019
 */
public class ReportCurrent {

  private Date fromDate;
  private Date toDate;
  private String titleReportDetail;
  private ParamReportEnum paramType;
  private long amount;

  public ReportCurrent(Builder builder) {
    fromDate = builder.fromDate;
    toDate = builder.toDate;
    titleReportDetail = builder.titleReportDetail;
    amount = builder.amount;
    paramType = builder.paramType;
    setData();
  }

  public ReportCurrent(ParamReportEnum paramType) {
    this.paramType = paramType;
    setData();
  }

  private void setData() {
    Date[] dates = new Date[2];
    Context context = CukCukLiteApplication.getInstance();
    switch (paramType) {
      case TODAY:
        titleReportDetail = context.getString(R.string.param_report_today);
        dates = DateUtil.getToday();
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
      case THIS_MONTH:
        titleReportDetail = context
            .getString(R.string.param_report_this_month);
        dates = DateUtil.getThisMonth();
        break;
    }
    fromDate = dates[0];
    toDate = dates[1];
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public String getTitleReportDetail() {
    return titleReportDetail;
  }

  public void setTitleReportDetail(String titleReportDetail) {
    this.titleReportDetail = titleReportDetail;
  }

  public ParamReportEnum getParamType() {
    return paramType;
  }

  public void setParamType(ParamReportEnum paramType) {
    this.paramType = paramType;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public static class Builder{
    private Date fromDate;
    private Date toDate;
    private String titleReportDetail;
    private ParamReportEnum paramType;
    private long amount;

    public ReportCurrent build(){
      return new ReportCurrent(this);
    }

    public Builder setFromDate(Date fromDate) {
      this.fromDate = fromDate;
      return this;
    }

    public Builder setToDate(Date toDate) {
      this.toDate = toDate;
      return this;
    }

    public Builder setTitleReportDetail(String titleReportDetail) {
      this.titleReportDetail = titleReportDetail;
      return this;
    }

    public Builder setParamType(ParamReportEnum paramType) {
      this.paramType = paramType;
      return this;
    }

    public Builder setAmount(long amount) {
      this.amount = amount;
      return this;
    }
  }
}
