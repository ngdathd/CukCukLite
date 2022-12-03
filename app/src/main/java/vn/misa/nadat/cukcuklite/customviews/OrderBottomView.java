package vn.misa.nadat.cukcuklite.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import vn.misa.nadat.cukcuklite.R;

public class OrderBottomView extends View {
    private Paint customPaint;
    private Path path;

    public OrderBottomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        customPaint = new CustomPaint(this);
        path = new Path();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        width = (width / height) + 1;
        path.moveTo(0.0f, 0.0f);
        float f = (float) height;
        path.lineTo(0.0f, f);
        path.lineTo(f, 0.0f);
        int i = 2;
        while (i < width) {
            path.lineTo((float) (i * height), f);
            i++;
            path.lineTo((float) (i * height), 0.0f);
            i++;
        }
        canvas.drawPath(path, customPaint);
    }

    private class CustomPaint extends Paint {
        private final OrderBottomView mOrderBottomView;

        private CustomPaint(OrderBottomView orderBottomView) {
            this.mOrderBottomView = orderBottomView;
            setStyle(Style.FILL);
            setColor(this.mOrderBottomView.getContext().getResources().getColor(R.color.color_white));
        }
    }
}
