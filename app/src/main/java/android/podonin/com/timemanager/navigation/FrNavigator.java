package android.podonin.com.timemanager.navigation;

import android.content.Context;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.view.fragment.TasksFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class FrNavigator {
    public final WeakReference<AppCompatActivity> mActivityWeakReference;

    public FrNavigator(@NonNull AppCompatActivity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    protected Context getContext(){
        return mActivityWeakReference.get();
    }

    protected void showFragment(@NonNull Fragment fragment) {
        String tag = fragment.getClass().getName();
        showFragment(fragment, tag);
    }

    @Nullable
    protected FragmentManager getFragmentManager() {
        AppCompatActivity activity = mActivityWeakReference.get();
        if (activity != null) {
            return activity.getSupportFragmentManager();
        }
        return null;
    }

    protected void showFragment(@NonNull Fragment fragment, @NonNull String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag).commit();
    }

    public void showTasksFragment(){
        showFragment(TasksFragment.newInstance());
    }
}
