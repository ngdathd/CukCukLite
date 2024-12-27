package vn.misa.nadat.cucuclite.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import vn.misa.nadat.cucuclite.R;

public class ColorDialog extends AlertDialog {
    private AlertDialog mAlertDialog;
    private IColorPicked mIColorPicked;
    private String mColor;

    public ColorDialog(@NonNull Context context, String color) {
        super(context);
        mIColorPicked = (IColorPicked) context;
        mColor = color;
    }

    @SuppressLint("NewApi")
    @Override
    public void show() {
        try {
            Context context = getContext();
            final String[] listColor = context.getResources().getStringArray(R.array.colordefault);

            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_color, null);
            ViewGroup glIcon = view.findViewById(R.id.gl_icon);
            Button btnCancel = view.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            for (int i = 0; i < listColor.length; i++) {
                ImageView imageView = (ImageView) inflater.inflate(R.layout.color_item, glIcon, false);
                if (mColor.equals(listColor[i]))
                    imageView.setImageResource(R.drawable.ic_check_normal);
                imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(listColor[i])));
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIColorPicked.colorPicked(listColor[finalI]);
                        mAlertDialog.dismiss();
                    }
                });
                glIcon.addView(imageView);
            }
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IColorPicked {
        void colorPicked(String color);
    }
}
