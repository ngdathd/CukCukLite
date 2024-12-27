package vn.misa.nadat.cucuclite.ui.unit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import vn.misa.nadat.cucuclite.R;
import vn.misa.nadat.cucuclite.databases.DBSQLiteManager;
import vn.misa.nadat.cucuclite.dialogs.EditUnitDialog;
import vn.misa.nadat.cucuclite.items.ItemUnit;

public class SelectUnitActivity extends AppCompatActivity implements View.OnClickListener, UnitAdapter.OnClickUnit, AddUnitDialog.IAddUnit {
    private ImageButton imgBtnBack;
    private ImageButton btnAdd;
    private RecyclerView rcvUnitName;
    private List<ItemUnit> units;
    private UnitAdapter unitAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_unit);
        findViewByIds();
        setActions();
    }

    private void findViewByIds() {
        imgBtnBack = findViewById(R.id.imgBtnBack);
        btnAdd = findViewById(R.id.btnAdd);
        rcvUnitName = findViewById(R.id.rcvUnitName);
    }

    private void setActions() {
        imgBtnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUnitName.setLayoutManager(linearLayoutManager);
        units = DBSQLiteManager.getInstance().getAllItemUnits();
        unitAdapter = new UnitAdapter(units, this);
        rcvUnitName.setAdapter(unitAdapter);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.imgBtnBack: {
                    onBackPressed();
                    break;
                }
                case R.id.btnAdd: {
                    new AddUnitDialog(this, this).show();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickEdit(ItemUnit unit) {
        new EditUnitDialog(this).show();
    }

    @Override
    public void onAddSucess(ItemUnit unit) {
        units.add(unit);
        unitAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddError() {

    }
}
