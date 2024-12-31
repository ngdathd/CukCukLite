package com.ngdat.cukcuklite.base;

/**
 * Class cơ sở cho các presenter
 * Created at 27/03/2019
 *
 * @param <T> thể hiện của view
 */
public interface IBasePresenter<T extends IBaseView> {
    /**
     * Phương thức truyền view cho presenter
     * Created at 27/03/
     *
     * @param view
     */
    void setView(T view);

    /**
     * Phương thức khởi chạy đầu tiên khi presenter bắt đầu hoạt động
     * Created at 27/03/2019
     */
    void onStart();

    /**
     * Phương thức kết thúc tương ứng khi presenter hoàn thành công việc của mình và ngừng hoạt động
     * Created at 01/04/2019
     */
    void onStop();
}
