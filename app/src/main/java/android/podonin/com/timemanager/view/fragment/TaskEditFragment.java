package android.podonin.com.timemanager.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.adapter.subcategoriesadapter.RvSubcategoryAdapter;
import android.podonin.com.timemanager.adapter.categoriesadapter.RvCategoriesAdapter;
import android.podonin.com.timemanager.measuredrecyclerviewvidget.MeasuredRecyclerView;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.TaskEditFragmentPresenter;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TaskEditFragmentView;
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

import java.util.List;

public class TaskEditFragment extends Fragment implements TaskEditFragmentView {

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

        mSubcategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSubcategoryAdapter = new RvSubcategoryAdapter();
        mRvSubcategoryAdapter.setOnSaveChangesListener(new RvSubcategoryAdapter.OnSaveChangesListener() {
            @Override
            public void onSaveChanges(List<TaskSubcategoryEfficiency> changed, List<TaskSubcategoryEfficiency> deleted, List<TaskSubcategoryEfficiency> added) {
                mPresenter.onSaveTaskSubcategoryEfficiencies(changed, deleted, added);
            }
        });
        mSubcategoriesRecyclerView.setAdapter(mRvSubcategoryAdapter);

        mCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        RvCategoriesAdapter categoriesAdapter = new RvCategoriesAdapter();
        categoriesAdapter.setOnCategoryClickListener(new RvCategoriesAdapter.OnCategoryClickListener() {
            @Override
            public void onClick(Category category) {
                boolean isCashEmpty = mRvSubcategoryAdapter.isCategoryCashEmpty(category);
                mPresenter.onCategoryChoose(category, isCashEmpty);

            }
        });
        mCategoriesRecyclerView.setAdapter(categoriesAdapter);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onSaveExit();
            }
        });

        mNavigator = FragmentNavigator.getInstance();

        String taskId = getArguments().getString(ARG_TASK_ID);
        mPresenter = new TaskEditFragmentPresenter(taskId, new RealmHelper());
        mPresenter.setFragmentView(this);
        mPresenter.dispatchCreate();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mPresenter.dispatchDestroy();
        super.onDestroyView();
    }

    @Override
    public void saveChanges() {
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
    public void showSubcategories(List<Subcategory> subcategories, List<TaskSubcategoryEfficiency> efficiencies){
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
    public void showTaskDate(String taskDate) {
        mDate.setText(taskDate);
    }

    @Override
    public void setDone(boolean isDone) {
        mDone.setChecked(isDone);
    }

    @Override
    public Context getFragmentContext() {
        return getActivity();
    }
}
