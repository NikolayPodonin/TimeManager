package android.podonin.com.timemanager.navigation;

import android.content.Context;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.view.fragment.TaskEditFragment;
import android.podonin.com.timemanager.view.fragment.TasksFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class FragmentNavigator {
    private static FragmentNavigator sNavigator;
    private WeakReference<AppCompatActivity> mActivityWeakReference;

    private FragmentNavigator(@NonNull AppCompatActivity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    private FragmentNavigator(){}

    public static FragmentNavigator getInstance(@NonNull AppCompatActivity activity){
        if (sNavigator == null) {
            sNavigator = new FragmentNavigator(activity);
        }
        return sNavigator;
    }

    protected Context getContext(){
        return mActivityWeakReference.get();
    }

    private void showFragment(@NonNull Fragment fragment) {
        String tag = fragment.getClass().getName();
        showFragment(fragment, tag);
    }

    @Nullable
    private FragmentManager getFragmentManager() {
        AppCompatActivity activity = mActivityWeakReference.get();
        if (activity != null) {
            return activity.getSupportFragmentManager();
        }
        return null;
    }

    private void showFragment(@NonNull Fragment fragment, @NonNull String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public void backToPreviousFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    public void showTasksFragment(){
        showFragment(TasksFragment.newInstance());
    }

    public void showTaskEditFragment(String taskId){
        showFragment(TaskEditFragment.newInstance(taskId));
    }
}
