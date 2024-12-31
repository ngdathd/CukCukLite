package com.ngdat.cukcuklite.data.models;

import java.io.Serializable;
import java.util.Date;

import com.ngdat.cukcuklite.data.cukcukenum.ReportTotalEnum;

/**
 * Đối tượng báo cáo theo mốc thời gian
 * Created at 17/04/2019
 */
public class ReportTotal implements Serializable {

  private Date fromDate;
  private Date toDate;
  private String titleReportDetail;
  private long amount;
  private ReportTotalEnum mType;

  public ReportTotal(Builder builder) {
    fromDate = builder.fromDate;
    toDate = builder.toDate;
    titleReportDetail = builder.titleReportDetail;
    amount = builder.amount;
    mType = builder.mType;
  }

  public ReportTotal(ReportTotalEnum type) {
    mType = type;
  }

  public ReportTotalEnum getType() {
    return mType;
  }

  public void setType(ReportTotalEnum type) {
    mType = type;
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
    private long amount;
    private ReportTotalEnum mType;

    public ReportTotal build(){
      return new ReportTotal(this);
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

    public Builder setAmount(long amount) {
      this.amount = amount;
      return this;
    }

    public Builder setType(ReportTotalEnum type) {
      mType = type;
      return this;
    }
  }
}
