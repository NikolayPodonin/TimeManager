package android.podonin.com.timemanager.navigation;

import android.content.Context;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.view.fragment.DiagramFragment;
import android.podonin.com.timemanager.view.fragment.TaskEditFragment;
import android.podonin.com.timemanager.view.fragment.TasksFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class FragmentNavigator {
    private WeakReference<AppCompatActivity> mActivityWeakReference;

    public FragmentNavigator(@NonNull AppCompatActivity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    private FragmentNavigator(){}

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
                .addToBackStack(tag)
                .commit();
    }

    private void showFragmentAsRoot(@NonNull Fragment fragment) {
        popEveryFragmentAfter(0);
        showFragment(fragment);
    }

    private void showFragmentAsSecond(Fragment fragment) {
        popEveryFragmentAfter(1);
        showFragment(fragment);
    }

    private void popEveryFragmentAfter(int value) {
        FragmentManager manager = getFragmentManager();
        if (manager == null){
            return;
        }
        int stackCount = manager.getBackStackEntryCount();
        for (int i = value; i < stackCount; i++) {
            int fragId = manager.getBackStackEntryAt(i).getId();
            manager.popBackStack(fragId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void backToPreviousFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        if (fragmentManager.getBackStackEntryCount() == 1) {
            mActivityWeakReference.get().finish();
        } else {
            fragmentManager.popBackStack();
        }
    }

    public void showTasksFragment(){
        showFragmentAsSecond(TasksFragment.newInstance());
    }

    public void showDiagramFragment(){
        showFragmentAsRoot(DiagramFragment.newInstance());
    }

    public void showTaskEditFragment(String taskId){
        showFragment(TaskEditFragment.newInstance(taskId));
    }
}
