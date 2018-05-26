package android.podonin.com.timemanager.widgets.piediagramwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PieDiagramView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRectF = new RectF(10, 10, 200, 200);
    private float mValues[] = {90, 90, 90, 90};
    private int mColors[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

    public PieDiagramView(Context context) {
        super(context);
    }

    public PieDiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int temp = 0;

        for (int i = 0; i < mValues.length; i++){
           temp += i == 0 ? 0 : (int) mValues[i];
           mPaint.setColor(mColors[i]);

            canvas.drawArc(mRectF, temp, mValues[i], true, mPaint);
        }
    }
}
