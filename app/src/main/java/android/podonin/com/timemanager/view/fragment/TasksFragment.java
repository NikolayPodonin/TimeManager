package android.podonin.com.timemanager.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.TaskEditActivity;
import android.podonin.com.timemanager.presenter.TasksFragmentPresenter;
import android.podonin.com.timemanager.view.TasksFragmentView;
import android.podonin.com.timemanager.view.adapter.RvTasksAdapter;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksFragment extends Fragment implements TasksFragmentView {


    private RecyclerView mRecyclerView;
    private RvTasksAdapter mTasksAdapter;
    private EventCalendarView mCalendarView;
    private CheckedTextView mToolbarToggle;
    private FrameLayout mToolbarToggleFrame;
    private FloatingActionButton mTasksActionButton;

    private TasksFragmentPresenter mFragmentPresenter;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTasksAdapter = new RvTasksAdapter();
        mRecyclerView.setAdapter(mTasksAdapter);

        mFragmentPresenter = new TasksFragmentPresenter(new RealmHelper());
        mFragmentPresenter.setTasksFragment(this);
        mFragmentPresenter.dispatchCreate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mFragmentPresenter.dispatchDestroy();
    }

    public Context getFragmentContext(){
        return getActivity();
    }



    public void setOnButtonClickListener(View.OnClickListener listener){
        mTasksActionButton.setOnClickListener(listener);
    }

    @Override
    public void setOnItemClickListener(RvTasksAdapter.OnClickListener onItemClickListener) {
        mTasksAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void setOnToggleClickListener(View.OnClickListener listener){
        mToolbarToggleFrame.setOnClickListener(listener);
    }

    public void toggleCalendar() {
        mToolbarToggle.toggle();
        if(mToolbarToggle.isChecked()){
            mCalendarView.setVisibility(View.VISIBLE);
        } else {
            mCalendarView.setVisibility(View.GONE);
        }
    }

    public void showMonthInToolbar(String month){
        mToolbarToggle.setText(month);
    }

    public void showTimeTasks(@NonNull List<TimeTask> timeTasks){
        mTasksAdapter.setData(timeTasks);
    }

    public void setCalendarOnChangeListener(EventCalendarView.OnChangeListener listener){
        mCalendarView.setOnChangeListener(listener);
    }

    public void goToTaskEditPage(String taskId){
        Intent intent = TaskEditActivity.newIntent(getActivity(), taskId);
        startActivity(intent);
    }


}
