package vn.misa.nadat.cucuclite.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

import vn.misa.nadat.cucuclite.R;

public class TextViewRequire extends AppCompatTextView {
    public TextViewRequire(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setText(Html.fromHtml(String.format(
                getResources().getString(R.string.text_required_format),
                getText().toString().trim())));
    }
}