package vn.misa.nadat.cukcuklite.dialogs;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.PickColorAdapter;
import vn.misa.nadat.cukcuklite.adapters.PickIconAdapter;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Dialog chọn màu hoặc icon.
 *
 * @created_by nadat on 15/04/2019
 */
public class PickIconColorDialog extends DialogFragment implements PickColorAdapter.OnClickItemColorListener, PickIconAdapter.OnClickItemIconListener {
    private OnChooseColorListener mOnChooseColorListener;
    private OnChooseIconListener mOnChooseIconListener;

    /**
     * Khởi tạo dialog.
     *
     * @param spanCount:    số cột
     * @param colorDefault: màu mặc định
     * @return dialog đã được tạo
     * @created_by nadat on 15/04/2019
     */
    public static PickIconColorDialog newInstance(int spanCount, String colorDefault) {
        PickIconColorDialog dialog = new PickIconColorDialog();
        Bundle args = new Bundle();
        args.putInt(Constant.SPAN_COUNT, spanCount);
        args.putString(Constant.COLOR_DEFAULT, colorDefault);
        dialog.setArguments(args);
        return dialog;
    }

    /**
     * Set up sự kiện chọn màu.
     *
     * @param onChooseColorListener: sự kiện chọn màu
     * @created_by nadat on 15/04/2019
     */
    public void setOnChooseColorListener(OnChooseColorListener onChooseColorListener) {
        mOnChooseColorListener = onChooseColorListener;
    }

    /**
     * Set up sự kiện chọn icon.
     *
     * @param onChooseIconListener: sự kiện chọn icon
     * @created_by nadat on 15/04/2019
     */
    public void setOnChooseIconListener(OnChooseIconListener onChooseIconListener) {
        mOnChooseIconListener = onChooseIconListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            if (dialog.getWindow() != null) {
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        } catch (Exception e) {
            super.onStart();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_icon_color, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcvColor = view.findViewById(R.id.rcvColor);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        int spanCount = 0;
        String colorDefault = "";
        if (getArguments() != null) {
            spanCount = getArguments().getInt(Constant.SPAN_COUNT);
            colorDefault = getArguments().getString(Constant.COLOR_DEFAULT);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), spanCount);
        rcvColor.setLayoutManager(gridLayoutManager);

        List<String> items = new ArrayList<>();
        if (spanCount == 4) {
            items.addAll(Arrays.asList(getResources().getStringArray(R.array.color_default)));
            PickColorAdapter pickColorAdapter = new PickColorAdapter(items, this);
            pickColorAdapter.setColorChecked(colorDefault);
            rcvColor.setAdapter(pickColorAdapter);
        } else {
            try {
                if (getContext() != null) {
                    AssetManager assetManager = getContext().getAssets();
                    if (assetManager != null) {
                        String[] lists = assetManager.list(Constant.THUMBNAIL_ASSETS);
                        if (lists != null) {
                            items.addAll(Arrays.asList(lists));
                        }
                    }
                    PickIconAdapter pickIconAdapter = new PickIconAdapter(items, this);
                    rcvColor.setAdapter(pickIconAdapter);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Bắt sự kiện click vào 1 màu.
     *
     * @param color: màu được click
     * @created_by nadat on 15/04/2019
     */
    @Override
    public void onClickItemColor(String color) {
        mOnChooseColorListener.onChooseColor(color);
        getDialog().dismiss();
    }

    /**
     * Bắt sự kiện click vào 1 icon.
     *
     * @param icon: icon được click
     * @created_by nadat on 15/04/2019
     */
    @Override
    public void onClickItemIcon(String icon) {
        mOnChooseIconListener.onChooseIcon(icon);
        getDialog().dismiss();
    }

    /**
     * Inteface lắng nghe sự kiện chọn màu.
     *
     * @created_by nadat on 15/04/2019
     */
    public interface OnChooseColorListener {
        void onChooseColor(String color);
    }

    /**
     * Interface lắng nghe sự kiện chọn icon.
     *
     * @created_by nadat on 15/04/2019
     */
    public interface OnChooseIconListener {
        void onChooseIcon(String icon);
    }
}
