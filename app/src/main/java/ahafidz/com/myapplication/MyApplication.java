package ahafidz.com.myapplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(BuildConfig.DB_NAME)
                .schemaVersion(BuildConfig.SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
