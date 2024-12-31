package com.ngdat.cukcuklite.screen.sale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.ngdat.cukcuklite.R;
import com.ngdat.cukcuklite.data.local.database.IDBUtils;
import com.ngdat.cukcuklite.data.models.Order;
import com.ngdat.cukcuklite.data.local.prefs.SharedPrefersManager;
import com.ngdat.cukcuklite.screen.authentication.login.LoginActivity;
import com.ngdat.cukcuklite.screen.dialogs.delete.ConfirmDeleteDialog;
import com.ngdat.cukcuklite.screen.dishorder.DishOrderActivity;
import com.ngdat.cukcuklite.screen.main.MainActivity;
import com.ngdat.cukcuklite.screen.pay.PayActivity;
import com.ngdat.cukcuklite.utils.AppConstants;
import com.ngdat.cukcuklite.utils.Navigator;

/**
 * Màn hình bán hàng
 * Created at 09/04/2019s
 */
public class SaleFragment extends Fragment implements ISaleContract.IView, OrderAdapter.IOrderClickListener, ConfirmDeleteDialog.IConfirmDeleteCallBack {

    public static final int REQUEST_PAY = 111;
    private static final String DELETE_DIALOG = "DELETE_DIALOG";
    private RecyclerView rvOrder;
    private ConstraintLayout clWaterMark;
    private MainActivity mContext;
    private SalePresenter mPresenter;
    private OrderAdapter mAdapter;
    private ProgressDialog mDialog;
    private TextView tvAddOrder;
    private String mBillId;
    private Navigator mNavigator;
    private SharedPrefersManager mPrefersManager;

    public static SaleFragment newInstance() {
        return new SaleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        mPresenter = new SalePresenter();
        mPrefersManager = SharedPrefersManager.getInstance(mContext);
        mPresenter.setView(this);
        mNavigator = new Navigator(this);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created at 15/04/2019
     */
    private void initEvents() {
        tvAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mNavigator.startActivity(new Intent(mContext, DishOrderActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created at 15/04/2019
     */
    private void initViews(View view) {
        try {
            clWaterMark = view.findViewById(R.id.clWaterMark);
            rvOrder = view.findViewById(R.id.rvOrder);
            tvAddOrder = view.findViewById(R.id.tvAddOrder);
            rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new OrderAdapter(mContext);
            mAdapter.setIOrderClickListener(this);
            rvOrder.setAdapter(mAdapter);
            initProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo progressbar
     * Created at 09/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(mContext) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.init_dish_list));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //khi màn hình được tương tác thì sẽ load lại dữ liệu
        mPresenter.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (MainActivity) context;
    }

    @Override
    public void showListOrder(List<Order> orders) {
        try {
            if (orders != null && orders.size() > 0) {
                clWaterMark.setVisibility(View.GONE);
                mAdapter.setListData(orders);
            } else {
                clWaterMark.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created at 15/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        try {
            mNavigator.showToastOnTopScreen(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created at 15/04/2019
     */
    @Override
    public void showLoading() {
        try {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created at 15/04/2019
     */
    @Override
    public void hideLoading() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức sửa order
     * Created at 15/04/2019
     *
     * @param billId - id của hóa đơn
     */
    @Override
    public void orderClick(String billId) {
        try {
            Intent intent = new Intent();
            intent.setClass(mContext, DishOrderActivity.class);
            intent.putExtra(AppConstants.EXTRA_BILL_ID, billId);
            mNavigator.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hủy order
     * Created at 15/04/2019
     *
     * @param billId - id của hóa đơn
     */
    @Override
    public void cancelOrder(String billId) {
        try {
            mBillId = billId;
            ConfirmDeleteDialog deleteDialog = ConfirmDeleteDialog.newInstance(null,
                    IDBUtils.ITableBillUtils.COLUMN_BILL_ID);
            deleteDialog.setCallBack(this);
            mContext.getSupportFragmentManager().beginTransaction().add(deleteDialog, DELETE_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thanh toán/thu tiền order, nếu người dùng chưa đăng nhập thì
     * phải đăng nhập mới cho thanh toán
     * Created at 15/04/2019
     *
     * @param billId - id của hóa đơn
     */
    @Override
    public void payOrder(String billId) {
        try {
            if (mPrefersManager.getIsLoginSuccess()) {
                Intent intent = new Intent();
                intent.setClass(mContext, PayActivity.class);
                intent.putExtra(AppConstants.EXTRA_BILL_ID, billId);
                mNavigator.startActivityForResult(intent, REQUEST_PAY);
            } else {
                mNavigator.startActivity(LoginActivity.class, Navigator.ActivityTransition.NONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi xác nhận xóa order
     * Created at 15/04/2019
     */
    @Override
    public void acceptDelete() {
        try {
            mPresenter.cancelOrder(mBillId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
