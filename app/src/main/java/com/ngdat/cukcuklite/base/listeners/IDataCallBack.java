package com.ngdat.cukcuklite.base.listeners;

/**
 * Là interface lắng nghe xứ lý khi lấy dữ liệu
 * Created at 25/03/2019
 *
 * @param <T> là kiểu dữ liệu kết quả trả về
 */
public interface IDataCallBack<T> {

    /**
     * Được gọi khi lấy dữ liệu về thành công
     * Created at 25/03/2019
     *
     * @param data là dữ liệu được trả về
     */
    void onDataSuccess(T data);

    /**
     * Created at 25/03/2019
     * Mô tả:  được gọi khi việc lấy dữ liệu bị thất bại
     *
     * @param msg là thông điệp muốn trả về để xử lý
     */
    void onDataFailed(int msg);
}
