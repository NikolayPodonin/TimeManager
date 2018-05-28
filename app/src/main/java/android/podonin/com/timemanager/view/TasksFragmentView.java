package android.podonin.com.timemanager.view;

import android.podonin.com.timemanager.model.TimeTask;
import android.support.annotation.NonNull;

import java.util.List;

public interface TasksFragmentView {

    void goToTaskEditPage(String taskId);

    void showTimeTasks(@NonNull List<TimeTask> timeTasks);

    void showDayOfMonthInToolbar(long dayMillis);

    void toggleCalendar();

    void showDeleteTaskDialog(String taskId);
}
