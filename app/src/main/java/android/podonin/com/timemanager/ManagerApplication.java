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

        initRealm();
    }

    private void initRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("timemanager.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
