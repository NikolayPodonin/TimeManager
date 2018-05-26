package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.widgets.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TasksFragmentView;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TasksFragmentPresenter {

    @NonNull
    private RealmHelper mRealmHelper;
    private List<TimeTask> mTimeTasks = new ArrayList<>();
    private long mSelectedDay;

    public TasksFragmentPresenter(TasksFragmentView tasksFragmentView) {
        mTasksFragmentView = tasksFragmentView;
        mRealmHelper = RealmHelper.getInstance();
    }

    private TasksFragmentView mTasksFragmentView;

    public void dispatchCreate(long dayMillis){
        showTasksPerDay(dayMillis);
    }

    public void dispatchDestroy() {
        mTasksFragmentView = null;
    }

    public void onSelectedDayChanged(long dayMillis){
        showTasksPerDay(dayMillis);
    }

    private void showTasksPerDay(long dayMillis) {
        mSelectedDay = dayMillis;
        mTasksFragmentView.showMonthInToolbar(mSelectedDay);
        mTimeTasks.clear();
        mTimeTasks.addAll(mRealmHelper.getAll(TimeTask.class, TimeTask.START_DATE_FIELD, mSelectedDay));
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

    public void onLongItemClicked(String taskId) {
        mTasksFragmentView.showDeleteTaskDialog(taskId);
    }

    public void deleteTask(String taskId) {
        TimeTask timeTask = null;
        for (TimeTask task : mTimeTasks) {
            if (task.getTaskId().equals(taskId)) {
                timeTask = task;
            }
        }
        if (timeTask == null) {
            return;
        }
        for (TaskSubcategoryEfficiency tse: timeTask.getSubcategoryEfficiencies()) {
            mRealmHelper.delete(tse.getClass(), TaskSubcategoryEfficiency.TASK_SUB_EFFICIENCY_ID, taskId);
        }
        mRealmHelper.delete(TimeTask.class, TimeTask.TASK_ID, taskId);
        mTimeTasks.clear();
        mTimeTasks.addAll(mRealmHelper.getAll(TimeTask.class, TimeTask.START_DATE_FIELD, mSelectedDay));
        mTasksFragmentView.showTimeTasks(mTimeTasks);
    }
}
