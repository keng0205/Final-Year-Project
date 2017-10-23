package fyp.keng.mytuition.pickauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.utils.ActivityUtils;

public class AuthenticationActivity extends AppCompatActivity {
    //done
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        getSupportActionBar().hide();
        //getSupportActionBar().setCustomView(R.layout.actionbar_icon_only);

        // TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        // title.setText("Welcome");

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        AuthenticationFragment authenticationFragment = (AuthenticationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (authenticationFragment == null) {
            authenticationFragment = AuthenticationFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    authenticationFragment, R.id.contentFrame);
        }

        new AuthenticationPresenter(
                app.repository,
                authenticationFragment
        );
    }
}
