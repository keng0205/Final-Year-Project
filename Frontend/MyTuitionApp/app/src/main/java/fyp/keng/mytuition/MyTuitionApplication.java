package fyp.keng.mytuition;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

import fyp.keng.mytuition.data.source.MyTuitionRepository;
import fyp.keng.mytuition.data.source.local.MyTuitionLocalDataSource;
import fyp.keng.mytuition.data.source.remote.MyTuitionRemoteDataSource;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyTuitionApplication extends MultiDexApplication {

    public static MyTuitionApplication instance = null;

    public static MyTuitionRepository repository;

    public static Context getInstance() {
        if (instance == null) {
            instance = new MyTuitionApplication();
        }

        return instance;
    }

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        repository = MyTuitionRepository.getInstance(
                MyTuitionRemoteDataSource.getInstance(this),
                MyTuitionLocalDataSource.getInstance(this)
        );

        repository.fetchPersistedUserInfo();

        initStetho(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().build());
    }

    private void initStetho(final Context context) {
        Stetho.initialize(Stetho.newInitializerBuilder(context)
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
        .build());
    }

    public void setDeviceToken(String deviceToken) {
        if (repository != null) {
            repository.setDeviceToken(deviceToken);
        }
    }
}
