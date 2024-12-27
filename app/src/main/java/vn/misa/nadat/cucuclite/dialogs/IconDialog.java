package vn.misa.nadat.cucuclite.dialogs;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import vn.misa.nadat.cucuclite.R;


public class IconDialog extends AlertDialog {
    private AlertDialog mAlertDialog;
    private IDialogResult mIDialogResult;

    public IconDialog(@NonNull Context context) {
        super(context);
        mIDialogResult = (IDialogResult) context;
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            String assetFolder = context.getString(R.string.asset_folder);
            AssetManager assetManager = context.getAssets();
            final String[] listImage = assetManager.list(assetFolder);

            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_icon, null);
            ViewGroup glIcon = view.findViewById(R.id.gl_icon);
            Button btnCancel = view.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            for (int i = 0; i < listImage.length; i++) {
                InputStream ims = assetManager.open(assetFolder + "/" + listImage[i]);
                Drawable d = Drawable.createFromStream(ims, null);
                ImageView imageView = (ImageView) inflater.inflate(R.layout.icon_item, glIcon, false);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIDialogResult.iconPicked(listImage[finalI]);
                        mAlertDialog.dismiss();
                    }
                });
                imageView.setImageDrawable(d);
                glIcon.addView(imageView);
            }
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface IDialogResult {
        void iconPicked(String fileName);
    }
}
