package android.podonin.com.timemanager.widgets.measuredrecyclerviewwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Objects;

public class MeasuredRecyclerView extends RecyclerView {
    public MeasuredRecyclerView(Context context) {
        super(context);
    }

    public MeasuredRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasuredRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        @SuppressLint("DrawAllocation") DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(windowManager).getDefaultDisplay().getMetrics(displayMetrics);

        heightSpec = MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels / 4, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
