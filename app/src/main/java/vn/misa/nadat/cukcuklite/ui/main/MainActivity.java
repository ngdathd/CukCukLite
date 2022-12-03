package vn.misa.nadat.cukcuklite.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.DishAdapter;
import vn.misa.nadat.cukcuklite.adapters.SaleAdapter;
import vn.misa.nadat.cukcuklite.dialogs.DeleteItemDialog;
import vn.misa.nadat.cukcuklite.items.ItemDish;
import vn.misa.nadat.cukcuklite.items.ItemSale;
import vn.misa.nadat.cukcuklite.ui.addeditdish.AddEditDishActivity;
import vn.misa.nadat.cukcuklite.ui.choosedish.ChooseDishActivity;
import vn.misa.nadat.cukcuklite.ui.payment.PaymentActivity;
import vn.misa.nadat.cukcuklite.ui.report.ReportFragment;
import vn.misa.nadat.cukcuklite.utils.Constant;

/**
 * Màn hình chính của ứng dụng.
 *
 * @created_by nadat on 10/04/2019
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IMainContract.IView,
        DishAdapter.OnItemDishClickListener,
        SaleAdapter.OnItemSaleClickListener, View.OnClickListener {

    private DrawerLayout drawerLayout;
    private TextView tvToolbarTitle;
    private RecyclerView rcvMain;
    private FrameLayout fraReport;
//    private RelativeLayout rlGuideAddSale;
//    private RelativeLayout rlGuideAddDish;

    private SaleAdapter mSaleAdapter;
    private DishAdapter mDishAdapter;

    private IMainContract.IPresenter mMainPresenter;

    /**
     * Khởi tạo MainActivity.
     *
     * @param savedInstanceState: trạng thái của Activity
     * @created_by nadat on 10/04/2019
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);
            initToolbarAndNavigationMenu();

            tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
            fraReport = findViewById(R.id.fraReport);
//            rlGuideAddSale = findViewById(R.id.rlGuideAddSale);
//            rlGuideAddDish = findViewById(R.id.rlGuideAddDish);

            rcvMain = findViewById(R.id.rcvMain);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rcvMain.setLayoutManager(linearLayoutManager);
            findViewById(R.id.tvAddSale).setOnClickListener(this);
            findViewById(R.id.tvAddDish).setOnClickListener(this);

            mSaleAdapter = new SaleAdapter(this);
            mDishAdapter = new DishAdapter(this);

            mMainPresenter = new MainPresenter(this);
            mMainPresenter.getItemSales();
            mMainPresenter.getItemDishes();

            registerReceiverBackAfterAddEditSale();
            registerReceiverBackAfterAddEditDish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sự kiện nhấn nút Back của thiết bị.
     *
     * @created_by nadat on 10/04/2019
     */
    @Override
    public void onBackPressed() {
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo menu.
     *
     * @param menu: Menu được inflate
     * @return giá trị boolean
     * @created_by nadat on 10/04/2019
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu == null) {
            return false;
        }
        try {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Bắt sự kiện click vào các item có trong menu.
     *
     * @param item: item được click
     * @return giá trị boolean
     * @created_by nadat on 10/04/2019
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == null) {
            return false;
        }
        try {
            int id = item.getItemId();
            if (id == R.id.action_add) {
                if (tvToolbarTitle.getText().toString().equalsIgnoreCase(getString(R.string.nav_sale_title))) {
                    Intent intent = new Intent(MainActivity.this, ChooseDishActivity.class);
                    intent.putExtra(Constant.ADD_SALE, 1);
                    startActivity(intent);
                } else if (tvToolbarTitle.getText().toString().equalsIgnoreCase(getString(R.string.nav_dish_title))) {
                    startActivity(new Intent(MainActivity.this, AddEditDishActivity.class));
                }
            }
            return super.onOptionsItemSelected(item);
        } catch (Exception e) {
            e.printStackTrace();
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Bắt sự kiện click vào item trong Navigation.
     *
     * @param item: item được click
     * @return giá trị boolean
     * @created_by nadat on 10/04/2019
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_sale: { // Click vào "Bán hàng"
                    fraReport.setVisibility(View.GONE);
                    rcvMain.setVisibility(View.VISIBLE);
//                    rlGuideAddDish.setVisibility(View.GONE);
                    tvToolbarTitle.setText(R.string.nav_sale_title);
                    rcvMain.setAdapter(mSaleAdapter);
                    break;
                }
                case R.id.nav_menu: { // Click vào "Thực đơn"
                    fraReport.setVisibility(View.GONE);
                    rcvMain.setVisibility(View.VISIBLE);
//                    rlGuideAddSale.setVisibility(View.GONE);
                    tvToolbarTitle.setText(R.string.nav_dish_title);
                    rcvMain.setAdapter(mDishAdapter);
                    break;
                }
                case R.id.nav_report: { // Click vào "Báo cáo"
                    rcvMain.setVisibility(View.GONE);
                    fraReport.setVisibility(View.VISIBLE);
//                    rlGuideAddSale.setVisibility(View.GONE);
//                    rlGuideAddDish.setVisibility(View.GONE);
                    tvToolbarTitle.setText(R.string.nav_report_title);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    ReportFragment reportFragment = new ReportFragment();
                    transaction.replace(R.id.fraReport, reportFragment);
                    transaction.commit();
                    break;
                }
                case R.id.nav_sync: {
                    break;
                }
                case R.id.nav_settings: {
                    break;
                }
                case R.id.nav_notification: {
                    break;
                }
                case R.id.nav_share: {
                    break;
                }
                case R.id.nav_rate: {
                    break;
                }
                case R.id.nav_info: {
                    break;
                }
                default:
                    break;
            }
            DrawerLayout drawer = findViewById(R.id.drawerLayout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Khởi tạo Toolbar và NavigationMenu.
     *
     * @created_by nadat on 10/04/2019
     */
    private void initToolbarAndNavigationMenu() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawerLayout = findViewById(R.id.drawerLayout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.navView);
            navigationView.setNavigationItemSelectedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận sự kiện sau khi hoàn thành thêm hoặc sửa bán hàng.
     *
     * @created_by nadat on 16/04/2019
     */
    private void registerReceiverBackAfterAddEditSale() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.BACK_ACTION_ADD_EDIT_SALE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mMainPresenter.getItemSales();
                rcvMain.setAdapter(mSaleAdapter);
            }
        }, intentFilter);
    }

    /**
     * Nhận sự kiện sau khi hoàn thành thêm hoặc sửa món ăn.
     *
     * @created_by nadat on 16/04/2019
     */
    private void registerReceiverBackAfterAddEditDish() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.BACK_ACTION_ADD_EDIT_DISH);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mMainPresenter.getItemDishes();
                rcvMain.setAdapter(mDishAdapter);
            }
        }, intentFilter);
    }

    /**
     * Setup dữ liệu cho mSaleAdapter và hiển thị tất cả các ItemSale.
     *
     * @param itemSales: danh sách ItemSale có trong DB
     * @created_by nadat on 10/04/2019
     */
    @Override
    public void showItemSales(List<ItemSale> itemSales) {
        if (itemSales == null) {
            return;
        }
        try {
            fraReport.setVisibility(View.GONE);
//            rlGuideAddDish.setVisibility(View.GONE);
            if (itemSales.size() == 0) {
//                rcvMain.setVisibility(View.GONE);
//                rlGuideAddSale.setVisibility(View.VISIBLE);
            } else {
                rcvMain.setVisibility(View.VISIBLE);
//                rlGuideAddSale.setVisibility(View.GONE);
            }
            mSaleAdapter.setItemSales(itemSales);
            rcvMain.setAdapter(mSaleAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup dữ liệu cho mDishAdapter.
     *
     * @param itemDishes: danh sách ItemDish có trong DB
     * @created_by nadat on 10/04/2019
     */
    @Override
    public void showItemDishes(List<ItemDish> itemDishes) {
        if (itemDishes == null) {
            return;
        }
        try {
//            fraReport.setVisibility(View.GONE);
            if (itemDishes.size() == 0) {
//                rcvMain.setVisibility(View.GONE);
//                rlGuideAddDish.setVisibility(View.VISIBLE);
            } else {
//                rcvMain.setVisibility(View.VISIBLE);
//                rlGuideAddDish.setVisibility(View.GONE);
            }
            mDishAdapter.setItemDishes(itemDishes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện thông báo không có dữ liệu.
     *
     * @created_by nadat on 10/04/2019
     */
    @Override
    public void showNotificationItemsEmpty() {
        try {
//            rcvMain.setVisibility(View.GONE);
//            fraReport.setVisibility(View.GONE);
            if (tvToolbarTitle.getText().toString().equalsIgnoreCase(getString(R.string.nav_sale_title))) {
//                rlGuideAddSale.setVisibility(View.VISIBLE);
//                rlGuideAddDish.setVisibility(View.GONE);
            } else if (tvToolbarTitle.getText().toString().equalsIgnoreCase(getString(R.string.nav_dish_title))) {
//                rlGuideAddDish.setVisibility(View.VISIBLE);
//                rlGuideAddSale.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị đã có lỗi
     *
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void showNotificationError() {
        try {
            Toast.makeText(this, "Đã có lỗi mời bạn thử lại!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ẩn ItemSale bị xóa
     *
     * @param itemSale: ItemSale được xóa
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void hideItemSaleDeleted(ItemSale itemSale) {
        if (itemSale == null) {
            return;
        }
        try {
            mSaleAdapter.deleteItemSale(itemSale);
            if (tvToolbarTitle.getText().toString().equalsIgnoreCase(getString(R.string.nav_sale_title))) {
                if (mSaleAdapter.getItemCount() == 0) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bắt sự kiện click vào ItemDish.
     *
     * @param id: Id của ItemDish được click
     * @created_by nadat on 10/04/2019
     */
    @Override
    public void onClickItemDish(int id) {
        try {
            Intent intent = new Intent(MainActivity.this, AddEditDishActivity.class);
            intent.putExtra(Constant.ID_DISH, id);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click vào 1 ItemSale
     *
     * @param id: id của ItemSale
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClickItemSale(int id) {
        try {
            Intent intent = new Intent(MainActivity.this, ChooseDishActivity.class);
            intent.putExtra(Constant.ID_SALE, id);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click vào nút Hủy trên ItemSale
     *
     * @param itemSale:ItemSale
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClickCancelItemSale(final ItemSale itemSale) {
        try {
            new DeleteItemDialog(this, Constant.TYPE_SALE, new DeleteItemDialog.OnClickAcceptDialogDeleteItemListener() {
                @Override
                public void onClickAcceptDialogDeleteItem() {
                    mMainPresenter.deleteItemSale(itemSale);
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click vào nút thu tiền
     *
     * @param id: id của ItemSale
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClickTakeMoneyItemSale(int id) {
        try {
            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
            intent.putExtra(Constant.ID_SALE, id);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bắt sự kiện click vào view
     *
     * @param v: view
     * @created_by nadat on 24/04/2019
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvAddSale: {
                    Intent intent = new Intent(MainActivity.this, ChooseDishActivity.class);
                    intent.putExtra(Constant.ADD_SALE, 1);
                    startActivity(intent);
                    break;
                }
                case R.id.tvAddDish: {
                    startActivity(new Intent(MainActivity.this, AddEditDishActivity.class));
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
