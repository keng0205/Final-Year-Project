package fyp.keng.mytuition.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.usertypeselection.UserTypeSelectionFragment;
import fyp.keng.mytuition.utils.ActivityUtils;



public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MyTuitionApplication app = (MyTuitionApplication) getApplication();

        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance(getIntent().getBooleanExtra(UserTypeSelectionFragment.IS_TUTOR, false));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    profileFragment, R.id.contentFrame);
        }

        new ProfilePresenter(
                app.repository,
                profileFragment,
                app.repository.getLoggedInUser(),
                getApplicationContext()
        ).start();
    }

}
