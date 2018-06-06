package android.podonin.com.timemanager.view;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public interface DiagramFragmentView {
    void setData(float[] values, int[] stringResIds, int[] colorResIds,
                 int stringResId, int descriptionResId, boolean anabelPercent);
}
