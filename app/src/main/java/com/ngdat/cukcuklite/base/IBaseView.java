package com.ngdat.cukcuklite.base;

/**
 * Class cơ sở cho các View
 * Created at 25/03/2019
 */
public interface IBaseView {
    /**
     * Phương thức nhận 1 thông điệp
     * Created at 01/04/2019
     *
     * @param message
     */
    void receiveMessage(int message);

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 04/04/2019
     */
    void showLoading();

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 04/04/2019
     */
    void hideLoading();
}
