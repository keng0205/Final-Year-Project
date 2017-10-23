package fyp.keng.mytuition.registertutorextra;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.utils.ActivityUtils;

public class RegisterExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_extra);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Fill your profile");

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        RegisterExtraFragment registerExtraFragment = (RegisterExtraFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (registerExtraFragment == null) {
            registerExtraFragment = RegisterExtraFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerExtraFragment, R.id.contentFrame);
        }

        new RegisterExtraPresenter(
                app.repository,
                registerExtraFragment
        );
    }
}
