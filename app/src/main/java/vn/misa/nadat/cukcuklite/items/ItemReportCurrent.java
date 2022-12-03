package vn.misa.nadat.cukcuklite.items;

/**
 * Đối tượng tại màn hình báo cáo gần đây
 *
 * @created_by nadat on 24/04/2019
 */
public class ItemReportCurrent {
    private int DateIcon;
    private String DateColor;
    private String DateName;
    private String Revenue;

    public ItemReportCurrent(int dateIcon, String dateColor, String dateName, String revenue) {
        DateIcon = dateIcon;
        DateColor = dateColor;
        DateName = dateName;
        Revenue = revenue;
    }

    public String getDateName() {
        return DateName;
    }

    public void setDateName(String dateName) {
        DateName = dateName;
    }

    public int getDateIcon() {
        return DateIcon;
    }

    public void setDateIcon(int dateIcon) {
        DateIcon = dateIcon;
    }

    public String getDateColor() {
        return DateColor;
    }

    public void setDateColor(String dateColor) {
        DateColor = dateColor;
    }

    public String getRevenue() {
        return Revenue;
    }

    public void setRevenue(String revenue) {
        Revenue = revenue;
    }
}
