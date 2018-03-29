package android.podonin.com.timemanager;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ybr on 14.03.2018.
 */

public class ManagerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
        Realm.init(this);
    }
}
