package fyp.keng.mytuition.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.utils.ActivityUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class HomeActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.tutee_actionbar);
        getSupportActionBar().setElevation(0);

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    homeFragment, R.id.contentFrame);
        }

        new HomePresenter(
                app.repository,
                homeFragment
        );
    }
}
