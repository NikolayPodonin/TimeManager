package android.podonin.com.timemanager.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.adapter.RvTasksAdapter;
import android.podonin.com.timemanager.utilites.CalendarUtils;
import android.podonin.com.timemanager.widgets.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.TasksFragmentPresenter;
import android.podonin.com.timemanager.view.TasksFragmentView;
import android.podonin.com.timemanager.view.activity.ContainerActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksFragment extends Fragment
        implements TasksFragmentView {


    private RecyclerView mRecyclerView;
    private RvTasksAdapter mTasksAdapter;
    private EventCalendarView mCalendarView;
    private CheckedTextView mToolbarToggle;
    private FrameLayout mToolbarToggleFrame;
    private FloatingActionButton mTasksActionButton;

    private TasksFragmentPresenter mFragmentPresenter;
    private FragmentNavigator mFragmentNavigator;

    public static TasksFragment newInstance(){
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        mRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        mCalendarView = view.findViewById(R.id.calendar_view);
        mToolbarToggle = view.findViewById(R.id.toolbar_toggle);
        mToolbarToggleFrame = view.findViewById(R.id.toolbar_toggle_frame);
        mTasksActionButton = view.findViewById(R.id.tasks_action_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentNavigator = getFragmentNavigator();;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTasksAdapter = new RvTasksAdapter();
        mRecyclerView.setAdapter(mTasksAdapter);

        mFragmentPresenter = new TasksFragmentPresenter(this);
        mFragmentPresenter.dispatchCreate(mCalendarView.getSelectedDay());

        mCalendarView.setOnChangeListener(dayMillis -> mFragmentPresenter.onSelectedDayChanged(dayMillis));
        mCalendarView.setOnTopFlingListener(((distance) -> {
            float dist = mCalendarView.getHeight() / 5;
            if (distance > dist){
                mFragmentPresenter.onTopFlingCalendar();
            }
        }));

        mToolbarToggleFrame.setOnClickListener(v -> mFragmentPresenter.onToggleClicked());

        mTasksActionButton.setOnClickListener(v -> mFragmentPresenter.onButtonClicked());

        mTasksAdapter.setOnItemClickListener((v, taskId) -> mFragmentPresenter.onItemClicked(taskId));

        mTasksAdapter.setOnLongItemClickListener((v, taskId) -> mFragmentPresenter.onLongItemClicked(taskId));
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentPresenter.dispatchCreate(mCalendarView.getSelectedDay());
    }

    private FragmentNavigator getFragmentNavigator() {
        ContainerActivity activity = (ContainerActivity) getActivity();
        if (activity == null){
            return null;
        }
        return activity.getFragmentNavigator();
    }

    @Override
    public void onDestroyView() {
        mFragmentPresenter.dispatchDestroy();
        super.onDestroyView();
    }
    public void toggleCalendar() {
        mToolbarToggle.toggle();
        if(mToolbarToggle.isChecked()){
            mCalendarView.setVisibility(View.VISIBLE);
        } else {
            mCalendarView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showDayOfMonthInToolbar(long dayMillis){
        mToolbarToggle.setText(CalendarUtils.toDateString(getContext(), dayMillis));
    }

    @Override
    public void showTimeTasks(@NonNull List<TimeTask> timeTasks){
        mTasksAdapter.setData(timeTasks);
    }

    @Override
    public void showDeleteTaskDialog(String taskId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setTitle(R.string.title_delete_task_dialog)
        .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            mFragmentPresenter.deleteTask(taskId);
            dialogInterface.cancel();
        })
        .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
        .create();

        dialog.show();
    }

    @Override
    public void goToTaskEditPage(String taskId){
        mFragmentNavigator.showTaskEditFragment(taskId);
    }
}
