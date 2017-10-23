package fyp.keng.mytuition.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.utils.ActivityUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = (SplashFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    splashFragment, R.id.contentFrame);
        }

        new SplashPresenter(app.repository, splashFragment).start();
    }
}
