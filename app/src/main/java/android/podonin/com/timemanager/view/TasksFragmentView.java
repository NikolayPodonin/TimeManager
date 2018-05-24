package android.podonin.com.timemanager.view;

import android.content.Context;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.adapter.RvTasksAdapter;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

public interface TasksFragmentView {

    void goToTaskEditPage(String taskId);

    void showTimeTasks(@NonNull List<TimeTask> timeTasks);

    void showMonthInToolbar(long dayMillis);

    void toggleCalendar();

    void showDeleteTaskDialog(String taskId);
}
