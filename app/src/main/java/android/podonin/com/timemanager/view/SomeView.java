package android.podonin.com.timemanager.view;

import android.podonin.com.timemanager.model.TimeTask;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Nic Podonin
 */

public interface SomeView {

    void showTimeTasks(@NonNull List<TimeTask> timeTasks);

    void showError();
}
