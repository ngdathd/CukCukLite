package com.ngdat.cukcuklite.base.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class base cho các adapter dành cho recyclerView
 * Created at 25/03/2019
 *
 * @param <T>
 */
public abstract class ListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List<T> mListData;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created at 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public ListAdapter(Context context) {
        mContext = context;
        mListData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    /**
     * Phương thức lấy danh sách dữ liệu hiện tại
     * Created at 27/03/2019
     *
     * @return danh sách dữ liệu
     */
    public List<T> getListData() {
        return mListData;
    }

    /**
     * Phương thức truyền dữ liệu cho danh sách
     * Created at 27/03/2019
     *
     * @param listData danh sách dữ liệu được truyền vào
     */
    public void setListData(List<T> listData) {
        mListData.clear();
        mListData.addAll(listData);
        notifyDataSetChanged();
    }

    /**
     * Phương thức thêm dữ liệu cho danh sách dữ liệu đã có
     * Created at 27/03/2019
     *
     * @param listData danh sách dữ liệu được thêm vào
     */
    public void addData(List<T> listData) {
        mListData.addAll(listData);
        notifyDataSetChanged();
    }

    /**
     * Phương thức xóa dữ liệu của danh sách
     * Created at 27/03/2019
     */
    public void clearData() {
        if (mListData != null) {
            mListData.clear();
            mListData = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    /**
     * Phương thức lấy item tại vị trí position
     * Created at 27/03/2019
     *
     * @param position là vị trí muốn lấy
     * @return item của list
     */
    public T getItem(int position) {
        return mListData.get(position);
    }

}
