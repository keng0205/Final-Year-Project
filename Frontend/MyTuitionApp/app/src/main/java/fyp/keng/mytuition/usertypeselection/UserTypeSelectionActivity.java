package fyp.keng.mytuition.usertypeselection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.utils.ActivityUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserTypeSelectionActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_user_type_selection);

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        UserTypeSelectionFragment userTypeSelectionFragment = (UserTypeSelectionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (userTypeSelectionFragment == null) {
            userTypeSelectionFragment = UserTypeSelectionFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    userTypeSelectionFragment, R.id.contentFrame);
        }

        new UserTypeSelectionPresenter(
                app.repository,
                userTypeSelectionFragment
        );
    }
}
