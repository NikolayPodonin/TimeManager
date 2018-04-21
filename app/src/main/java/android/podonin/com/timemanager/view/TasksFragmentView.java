package android.podonin.com.timemanager.view;

import android.content.Context;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.view.adapter.RvTasksAdapter;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

public interface TasksFragmentView {

    void goToTaskEditPage(String taskId);

    void setCalendarOnChangeListener(EventCalendarView.OnChangeListener listener);

    void showTimeTasks(@NonNull List<TimeTask> timeTasks);

    void showMonthInToolbar(String month);

    void toggleCalendar();

    void setOnToggleClickListener(View.OnClickListener listener);

    void setOnButtonClickListener(View.OnClickListener listener);

    void setOnItemClickListener(RvTasksAdapter.OnClickListener onItemClickListener);

    Context getFragmentContext();

}
