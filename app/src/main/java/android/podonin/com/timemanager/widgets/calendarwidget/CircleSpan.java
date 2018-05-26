package android.podonin.com.timemanager.widgets.calendarwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.podonin.com.timemanager.R;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;

class CircleSpan extends ReplacementSpan {
    private final int mCircleColor;
    private final int mTextColor;
    private final float mPadding;

    public CircleSpan(Context context) {
        super();
        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{
                R.attr.colorAccent,
                android.R.attr.textColorPrimaryInverse
        });
        mCircleColor = ta.getColor(0, ContextCompat.getColor(context, R.color.greenA700));
        mTextColor = ta.getColor(1, 0);
        ta.recycle();
        mPadding = context.getResources().getDimension((R.dimen.padding_circle));
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end) + mPadding * 2); // left + right
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        float textSize = paint.measureText(text, start, end);
        paint.setColor(mCircleColor);
        canvas.drawCircle(x + textSize / 2 + mPadding,
                (top + bottom) / 2,
                (text.length() == 1 ? textSize : textSize / 2) + mPadding,
                paint);
        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, mPadding + x, y, paint);
    }
}
