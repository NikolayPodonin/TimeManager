package android.podonin.com.timemanager.view.fragment;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.adapter.TimeTaskAdapter;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.presenter.SomePresenter;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.SomeView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * @author Nic Podonin
 */

public class SomeFragment extends Fragment implements SomeView {

    private RecyclerView mRecyclerView;

    private SomePresenter mSomePresenter;
    private TimeTaskAdapter mTimeTaskAdapter;

    public static final String SOME_STRING_EXTRA = "someString";
    public static final String SOME_INT_EXTRA = "someInt";


    public static SomeFragment newInstance(int someIntArg, String someStringArg) {
        Bundle args = new Bundle();
        args.putInt(SOME_INT_EXTRA, someIntArg);
        args.putString(SOME_STRING_EXTRA, someStringArg);
        SomeFragment fragment = new SomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_some, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            int someInt = getArguments().getInt(SOME_INT_EXTRA);
            String someString = getArguments().getString(SOME_STRING_EXTRA);

            Toast.makeText(getActivity(), someInt + " " + someString, Toast.LENGTH_SHORT).show();
        }

        mTimeTaskAdapter = new TimeTaskAdapter();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mTimeTaskAdapter);

        mSomePresenter = new SomePresenter(new RealmHelper());
        mSomePresenter.setSomeView(this);
        mSomePresenter.dispatchCreate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSomePresenter.dispatchDestroy();
    }

    @Override
    public void showTimeTasks(@NonNull List<TimeTask> timeTasks) {
        mTimeTaskAdapter.setData(timeTasks);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Хуя лысого", Toast.LENGTH_SHORT).show();
    }
}
