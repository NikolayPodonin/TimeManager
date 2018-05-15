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

    private TasksFragmentView mTasksFragmentView;

    public TasksFragmentPresenter(RealmHelper realmHelper) {
        mRealmHelper = realmHelper;
    }

    public void setTasksFragment(TasksFragment tasksFragment) {
        mTasksFragmentView = tasksFragment;
    }

    public void dispatchCreate(){
        mTimeTasks.addAll(mRealmHelper.getAllTimeTasks());
        mTasksFragmentView.showTimeTasks(mTimeTasks);

        mTasksFragmentView.showMonthInToolbar(CalendarUtils.toMonthString(mTasksFragmentView.getFragmentContext(), CalendarUtils.today()));
    }

    public void dispatchDestroy() {
        mTasksFragmentView = null;
    }

    public void onSelectedDayChanged(long dayMillis){
        mTasksFragmentView.showMonthInToolbar(CalendarUtils.toMonthString(mTasksFragmentView.getFragmentContext(), dayMillis));
        mTimeTasks.clear();
        mTimeTasks.addAll(mRealmHelper.getTimeTasksPerDay(dayMillis));
        mTasksFragmentView.showTimeTasks(mTimeTasks);
    }

    public void onToggleClicked(){
        mTasksFragmentView.toggleCalendar();
    }

    public void onButtonClicked(){
        TimeTask task = new TimeTask();
        task.setStartDate(CalendarUtils.today());
        TimeTask timeTask = mRealmHelper.addTimeTask(task);
        mTasksFragmentView.goToTaskEditPage(timeTask.getTaskId());
    }

    public void onItemClicked(String taskId){
        mTasksFragmentView.goToTaskEditPage(taskId);
    }
}
