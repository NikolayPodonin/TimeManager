package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TasksFragmentView;
import android.podonin.com.timemanager.adapter.RvTasksAdapter;
import android.podonin.com.timemanager.view.fragment.TasksFragment;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TasksFragmentPresenter {

    @NonNull
    private RealmHelper mRealmHelper;
    private List<TimeTask> mTimeTasks = new ArrayList<>();

    public TasksFragmentPresenter(TasksFragmentView tasksFragmentView) {
        mTasksFragmentView = tasksFragmentView;
        mRealmHelper = RealmHelper.getInstance();
    }

    private TasksFragmentView mTasksFragmentView;

    public void dispatchCreate(){
        mTimeTasks.addAll(mRealmHelper.getAll(TimeTask.class));
        mTasksFragmentView.showTimeTasks(mTimeTasks);

        mTasksFragmentView.showMonthInToolbar(CalendarUtils.toMonthString(mTasksFragmentView.getFragmentContext(), CalendarUtils.today()));
    }

    public void dispatchDestroy() {
        mTasksFragmentView = null;
    }

    public void onSelectedDayChanged(long dayMillis){
        mTasksFragmentView.showMonthInToolbar(CalendarUtils.toMonthString(mTasksFragmentView.getFragmentContext(), dayMillis));
        mTimeTasks.clear();
        mTimeTasks.addAll(mRealmHelper.getAll(TimeTask.class, TimeTask.START_DATE_FIELD, dayMillis));
        mTasksFragmentView.showTimeTasks(mTimeTasks);
    }

    public void onToggleClicked(){
        mTasksFragmentView.toggleCalendar();
    }

    public void onButtonClicked(){
        TimeTask task = new TimeTask();
        task.setStartDate(CalendarUtils.today());
        mRealmHelper.insert(task);
        mTasksFragmentView.goToTaskEditPage(task.getTaskId());
    }

    public void onItemClicked(String taskId){
        mTasksFragmentView.goToTaskEditPage(taskId);
    }
}
