package android.podonin.com.timemanager.presenter;


import android.podonin.com.timemanager.view.ExampleView;
import android.support.annotation.NonNull;

/**
 * @author Nic Podonin
 */

public class ExamplePresenter {

    @NonNull
    private ExampleView mExampleView;

    public ExamplePresenter(@NonNull ExampleView view){
        mExampleView = view;

    }
    public void dispatchCreate(){
        mExampleView.showSomeFragment();
    }
}
