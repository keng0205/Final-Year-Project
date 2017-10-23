package fyp.keng.mytuition.tutorselectdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.selecttutor.SelectTutorFragment;
import fyp.keng.mytuition.utils.ActivityUtils;

public class TutorSelectDetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutor_select_details);
        getSupportActionBar().hide();

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        TutorSelectDetailsFragment tutorSelectDetailsFragment = (TutorSelectDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (tutorSelectDetailsFragment == null) {
            int tutorID = getIntent().getIntExtra(SelectTutorFragment.SELECTED_TUTOR_ID, 0);

            tutorSelectDetailsFragment = TutorSelectDetailsFragment.newInstance(tutorID);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tutorSelectDetailsFragment, R.id.contentFrame);
        }

        new TutorSelectDetailsPresenter(
                app.repository,
                tutorSelectDetailsFragment
        );
    }
}
