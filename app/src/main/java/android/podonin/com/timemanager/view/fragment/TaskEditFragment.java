package android.podonin.com.timemanager.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.adapter.subcategoriesadapter.RvSubcategoryAdapter;
import android.podonin.com.timemanager.adapter.categoriesadapter.RvCategoriesAdapter;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.measuredrecyclerviewvidget.MeasuredRecyclerView;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.TaskEditFragmentPresenter;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TaskEditFragmentView;
import android.podonin.com.timemanager.view.activity.ContainerActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class TaskEditFragment extends Fragment
        implements TaskEditFragmentView {

    private static final String ARG_TASK_ID = "task_id";

    private EditText mTaskBody;
    private TextView mDate;
    private CheckBox mDone;
    private View mDividerView;
    private MeasuredRecyclerView mSubcategoriesRecyclerView;
    private RecyclerView mCategoriesRecyclerView;
    private RvSubcategoryAdapter mRvSubcategoryAdapter;

    private AppCompatButton mOkButton;
    private AppCompatButton mBackButton;

    private FragmentNavigator mNavigator;
    private TaskEditFragmentPresenter mPresenter;

    public static TaskEditFragment newInstance(@NonNull String taskId){
        Bundle args = new Bundle();
        args.putString(ARG_TASK_ID, taskId);

        TaskEditFragment fragment = new TaskEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_edit, container, false);

        mOkButton = view.findViewById(R.id.ok_button);
        mBackButton = view.findViewById(R.id.back_button);

        mSubcategoriesRecyclerView = view.findViewById(R.id.subcategories_efficiency_recycler_view);
        mCategoriesRecyclerView = view.findViewById(R.id.category_recycler_view);

        mTaskBody = view.findViewById(R.id.task_body_edit);
        mDate = view.findViewById(R.id.task_date_edit);
        mDone = view.findViewById(R.id.done_check_box);
        mDividerView = view.findViewById(R.id.horizontal_divider_2);
        mDividerView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String taskId = Objects.requireNonNull(getArguments()).getString(ARG_TASK_ID);
        mPresenter = new TaskEditFragmentPresenter(this, taskId);
        mPresenter.dispatchCreate();

        mSubcategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSubcategoryAdapter = new RvSubcategoryAdapter();
        mRvSubcategoryAdapter.setOnSaveChangesListener(new RvSubcategoryAdapter.OnSaveChangesListener() {
            @Override
            public void onSaveChanges(@NonNull List<TaskSubcategoryEfficiency> changed, @NonNull List<TaskSubcategoryEfficiency> deleted, @NonNull List<TaskSubcategoryEfficiency> added) {
                mPresenter.onSaveTaskSubcategoryEfficiencies(changed, deleted, added);
            }

            @Override
            public void onCheckChanges(boolean isSomethingChanged) {
                mPresenter.onCheckChanges(isSomethingChanged, mTaskBody.getText().toString(), CalendarUtils.toDateLong(mDate.getText().toString()), mDone.isChecked());
            }
        });
        mSubcategoriesRecyclerView.setAdapter(mRvSubcategoryAdapter);

        mCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        RvCategoriesAdapter categoriesAdapter = new RvCategoriesAdapter();
        categoriesAdapter.setOnCategoryClickListener(category -> {
            boolean isCashEmpty = mRvSubcategoryAdapter.isCategoryCashEmpty(category);
            mPresenter.onCategoryChoose(category, isCashEmpty);
        });
        mCategoriesRecyclerView.setAdapter(categoriesAdapter);

        mOkButton.setOnClickListener(v -> mPresenter.onOkExit());

        mNavigator = getFragmentNavigator();

        mBackButton.setOnClickListener(v -> mPresenter.onBackExit());
    }

    private FragmentNavigator getFragmentNavigator() {
        ContainerActivity activity = (ContainerActivity) getActivity();
        if (activity == null){
            return null;
        }
        return activity.getFragmentNavigator();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onExit();

        mPresenter.dispatchDestroy();
        super.onDestroyView();
    }

    @Override
    public void exit() {
        mNavigator.backToPreviousFragment();
    }

    @SuppressLint("ShowToast")
    @Override
    public void showMessage(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
    }

    @Override
    public void checkSomeChanges() {
        mRvSubcategoryAdapter.checkChanges();
    }

    @Override
    public void saveChanges() {
        mPresenter.onSaveTask(mTaskBody.getText().toString(), CalendarUtils.toDateLong(mDate.getText().toString()), mDone.isChecked());
        mRvSubcategoryAdapter.saveChanges();
    }

    @Override
    public void setSubcategoriesVisibility(boolean visibility) {
        if (visibility) {
            mSubcategoriesRecyclerView.setVisibility(View.VISIBLE);
            mDividerView.setVisibility(View.VISIBLE);
        } else {
            mSubcategoriesRecyclerView.setVisibility(View.GONE);
            mDividerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSubcategories(@NonNull List<Subcategory> subcategories, @NonNull List<TaskSubcategoryEfficiency> efficiencies){
        mRvSubcategoryAdapter.setData(subcategories, efficiencies);
    }

    @Override
    public void showSubcategoriesFromCash(Category category) {
        mRvSubcategoryAdapter.setDataFromCash(category);
    }

    @Override
    public void showTaskBody(String taskBody) {
        mTaskBody.setText(taskBody);
    }

    @Override
    public void showTaskDate(long taskDate) {
        mDate.setText(CalendarUtils.toDateString(getActivity(), taskDate));
    }

    @Override
    public void setDone(boolean isDone) {
        mDone.setChecked(isDone);
    }
}
