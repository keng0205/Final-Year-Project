package fyp.keng.mytuition.selecttutor;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.home.HomeSearchFragment;
import fyp.keng.mytuition.utils.ActivityUtils;

public class SelectTutorActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tutor);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText(getIntent().getStringExtra(HomeSearchFragment.SUBJECT_TYPE) + " tutors");

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        SelectTutorFragment selectTutorFragment = (SelectTutorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (selectTutorFragment == null) {
            selectTutorFragment = SelectTutorFragment.newInstance(
                    getIntent().getStringExtra(HomeSearchFragment.SUBJECT_TYPE),
                    getIntent().getIntExtra(HomeSearchFragment.SUBJECT_ID, 0));

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    selectTutorFragment, R.id.contentFrame);
        }

        new SelectTutorPresenter(
                app.repository,
                selectTutorFragment
        );
    }
}
