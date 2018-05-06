package android.podonin.com.timemanager.view.fragment;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.TaskEditFragmentPresenter;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskEditFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";

    private EditText mTaskBody;
    private TextView mDate;
    private CheckBox mDone;
    private RecyclerView mSubcategoriesRecyclerView;
    private RecyclerView mCategoriesRecyclerView;

    private AppCompatButton mOkButton;
    private AppCompatButton mBackButton;

    private FragmentNavigator mNavigator;
    private TaskEditFragmentPresenter mPresenter;

    public static TaskEditFragment newInstance(String taskId){
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mSubcategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mNavigator = FragmentNavigator.getInstance();

        String taskId = getArguments().getString(ARG_TASK_ID);
        mPresenter = new TaskEditFragmentPresenter(taskId);


    }

    @Override
    public void onStop() {
        //mRealmHelper.addTimeTask();
        super.onStop();
    }
}
