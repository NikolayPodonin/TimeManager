package android.podonin.com.timemanager.navigation;

import android.content.Context;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.view.fragment.SomeFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * @author Nic Podonin
 */

public class ExampleNavigator {

    public final WeakReference<AppCompatActivity> mActivityWeakReference;


    public ExampleNavigator(@NonNull AppCompatActivity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    protected Context getContext(){
        return mActivityWeakReference.get();
    }

    public void showExampleFragment(int someIntArg, String someStringArg){
        showFragment(SomeFragment.newInstance(someIntArg, someStringArg));
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
        AppCompatActivity activity = mActivityWeakReference.get();
        if (activity == null) {
            return;
        }
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag).commit();
    }
}
