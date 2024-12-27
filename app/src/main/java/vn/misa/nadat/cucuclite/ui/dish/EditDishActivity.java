package vn.misa.nadat.cucuclite.ui.dish;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.misa.nadat.cucuclite.R;
import vn.misa.nadat.cucuclite.databases.DBSQLiteManager;
import vn.misa.nadat.cucuclite.dialogs.CalculatorDialog;
import vn.misa.nadat.cucuclite.dialogs.ColorDialog;
import vn.misa.nadat.cucuclite.dialogs.IconDialog;
import vn.misa.nadat.cucuclite.ui.unit.SelectUnitActivity;

public class EditDishActivity extends AppCompatActivity implements View.OnClickListener,
        ColorDialog.IColorPicked, IconDialog.IDialogResult, CalculatorDialog.IDialogCalculator {
    private ImageButton imgBtnBack;
    private TextView btnDone;
    private TextView btnDelete;
    private TextView btnSave;
    private RelativeLayout realSelectColor;
    private RelativeLayout realSelectIcon;
    private CircleImageView img_select_icon;
    private CircleImageView img_select_color;
    private LinearLayout lnUnitPrice;
    private LinearLayout lnUnitName;
    private TextView tvPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);

        findViewByIds();
        setActions();
    }

    private void findViewByIds() {
        imgBtnBack = findViewById(R.id.imgBtnBack);
        btnDone = findViewById(R.id.btnDone);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        realSelectColor = findViewById(R.id.realSelectColor);
        realSelectIcon = findViewById(R.id.realSelectIcon);
        img_select_icon = findViewById(R.id.img_select_icon);
        img_select_color = findViewById(R.id.img_select_color);
        lnUnitPrice = findViewById(R.id.lnUnitPrice);
        lnUnitName = findViewById(R.id.lnUnitName);

        tvPrice = findViewById(R.id.tvPrice);
    }

    private void setActions() {
        imgBtnBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        realSelectColor.setOnClickListener(this);
        realSelectIcon.setOnClickListener(this);
        lnUnitPrice.setOnClickListener(this);
        lnUnitName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnBack: {
                onBackPressed();
                break;
            }
            case R.id.btnDone: {
                break;
            }
            case R.id.btnDelete: {
                break;
            }
            case R.id.btnSave: {
                break;
            }
            case R.id.lnUnitPrice: {
                new CalculatorDialog(this, this).show();
                break;
            }
            case R.id.lnUnitName: {
                startActivity(new Intent(EditDishActivity.this, SelectUnitActivity.class));
                break;
            }
            case R.id.realSelectColor: {
                new ColorDialog(this, "#fff987").show();
                break;
            }
            case R.id.realSelectIcon: {
                new IconDialog(this).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void colorPicked(String color) {
        img_select_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
        img_select_color.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }

    @Override
    public void iconPicked(String fileName) {
        setIcon(fileName);
    }

    private void setIcon(String fileName) {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(getResources().getString(R.string.asset_folder) + "/" + fileName);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            CircleImageView img_select_icon = findViewById(R.id.img_select_icon);
            img_select_icon.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void priceDish(String price) {
        tvPrice.setText(price);
    }
}
