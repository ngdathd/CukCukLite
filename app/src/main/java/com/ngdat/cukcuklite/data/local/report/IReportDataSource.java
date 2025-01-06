package com.ngdat.cukcuklite.data.local.report;

import java.util.List;

import com.ngdat.cukcuklite.data.models.ReportCurrent;
import com.ngdat.cukcuklite.data.models.ReportDetail;
import com.ngdat.cukcuklite.data.models.ReportTotal;

/**
 * Danh sách các phương thức cung cấp cho việc lấy báo cáo
 * Created at 18/04/2019
 */
public interface  IReportDataSource {
    // Hôm nay
    List<ReportDetail> getReportToday();
    // Hôm qua
    List<ReportDetail> getReportYeaterday();
    // Tuần này
    List<ReportTotal> getReportThisWeek();
    // Tuần trước
    List<ReportTotal> getReportLastWeek();
    // Tháng này
    List<ReportTotal> getReportThisMonth();
    // Tháng trước
    List<ReportTotal> getReportLastMonth();
    // Năm nay
    List<ReportTotal> getReportThisYear();
    // Năm trước
    List<ReportTotal> getReportLastYear();
    // Gần đây filter
    List<ReportCurrent> getOverviewReport();
    // Lấy thông tin báo cáo theo khoảng thời gian
    List<ReportTotal> getReportByDate(String startDate, String endDate);
    List<ReportDetail> getReportDetailByDate(String startDate, String endDate);
    // Lấy thông tin báo cáo theo ngày xác định
    List<ReportDetail> getReportDetailDate(String date);

}
