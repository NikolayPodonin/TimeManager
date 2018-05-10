package android.podonin.com.timemanager.measuredrecyclerviewvidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

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
        heightSpec = MeasureSpec.makeMeasureSpec(370, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
