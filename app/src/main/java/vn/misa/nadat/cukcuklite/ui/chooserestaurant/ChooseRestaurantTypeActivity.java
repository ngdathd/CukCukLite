package vn.misa.nadat.cukcuklite.ui.chooserestaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.cukcuklite.R;
import vn.misa.nadat.cukcuklite.adapters.ChooseRestaurantTypeAdapter;

/**
 * @created_by nadat on 24/04/2019
 */
public class ChooseRestaurantTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private TextView tvContinue;
    private Button llContinue;
    private RecyclerView rvRestaurantType;
    private List<String> mTypeRes;
    private ChooseRestaurantTypeAdapter mChooseRestaurantTypeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_restaurant);
        mTypeRes = new ArrayList<>();
        mTypeRes.add("Trà đá");
        mTypeRes.add("Trà đá1");
        mTypeRes.add("Trà đá2");
        initViews();
        initEvents();
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvContinue = findViewById(R.id.tvContinue);
        llContinue = findViewById(R.id.llContinue);
        rvRestaurantType = findViewById(R.id.rvRestaurantType);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRestaurantType.setLayoutManager(layoutManager);
        mChooseRestaurantTypeAdapter = new ChooseRestaurantTypeAdapter(mTypeRes);
        rvRestaurantType.setAdapter(mChooseRestaurantTypeAdapter);
    }

    /**
     * Phương thức gắn sự kiện cho view
     *
     * @created_by nadat on 24/04/2019
     */
    private void initEvents() {
        btnBack.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        llContinue.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnBack: {
                    finish();
                    break;
                }
                case R.id.tvContinue:
                case R.id.llContinue: {
                    Log.i("onClick", "onClick: " + mChooseRestaurantTypeAdapter.getPositionClick());
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
