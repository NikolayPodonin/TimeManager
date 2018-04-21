package android.podonin.com.timemanager.view;

import android.content.Context;
import android.view.View;

public interface ItemTaskView {

    void setOnItemClickListener(final View.OnClickListener listener);

    void callAdapterClickListener(View v, String taskId);

    void setBody(String body);

    void setDate(String date);

    void setEfficiency(String efficiency);

    Context getContext();
}
