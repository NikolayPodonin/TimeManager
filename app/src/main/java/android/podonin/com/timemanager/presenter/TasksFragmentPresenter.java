package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.ItemTaskView;
import android.podonin.com.timemanager.view.TasksFragmentView;
import android.podonin.com.timemanager.view.adapter.RvTasksAdapter;
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

        mTasksFragmentView.setCalendarOnChangeListener(new EventCalendarView.OnChangeListener() {
            @Override
            public void onSelectedDayChange(long dayMillis) {
                mTasksFragmentView.showMonthInToolbar(CalendarUtils.toMonthString(mTasksFragmentView.getFragmentContext(), dayMillis));
                mTimeTasks.clear();
                mTimeTasks.addAll(mRealmHelper.getTimeTasksPerDay(dayMillis));
                mTasksFragmentView.showTimeTasks(mTimeTasks);
            }
        });

        mTasksFragmentView.showMonthInToolbar(CalendarUtils.toMonthString(mTasksFragmentView.getFragmentContext(), CalendarUtils.today()));

        mTasksFragmentView.setOnToggleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTasksFragmentView.toggleCalendar();
            }
        });

        mTasksFragmentView.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTask timeTask = mRealmHelper.addTimeTask(new TimeTask());
                mTasksFragmentView.goToTaskEditPage(timeTask.getTaskId());
            }
        });

        mTasksFragmentView.setOnItemClickListener(new RvTasksAdapter.OnClickListener() {
            @Override
            public void onClick(View v, String taskId) {
                mTasksFragmentView.goToTaskEditPage(taskId);
            }
        });
    }

    public void dispatchDestroy() {
        mTasksFragmentView = null;
    }

    public static class ItemTaskPresenter  {
        @NonNull
        private TimeTask mTimeTask;
        private ItemTaskView mItemTaskView;

        public ItemTaskPresenter(@NonNull TimeTask timeTask) {
            mTimeTask = timeTask;
        }

        public void setItemTaskView(ItemTaskView itemTaskView) {
            mItemTaskView = itemTaskView;
        }

        public void dispatchCreate(){
            if(!setupDone()){
                return;
            }
            mItemTaskView.setOnItemClickListener(goToEditTaskPage());
            mItemTaskView.setBody(mTimeTask.getTaskBody());
            mItemTaskView.setDate(CalendarUtils.toDateString(mItemTaskView.getContext(), mTimeTask.getStartDate()));
            mItemTaskView.setEfficiency(mTimeTask.getSubcategoryEfficiencies().first().getEfficiency().toString());
        }

        public boolean setupDone(){
            return mTimeTask != null && mItemTaskView != null;
        }

        private View.OnClickListener goToEditTaskPage(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTaskView.callAdapterClickListener(v, mTimeTask.getTaskId());
                }
            };
        }
    }
}
