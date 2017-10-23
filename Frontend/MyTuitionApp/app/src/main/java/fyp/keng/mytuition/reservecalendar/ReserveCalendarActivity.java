package fyp.keng.mytuition.reservecalendar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fyp.keng.mytuition.R;
import fyp.keng.mytuition.MyTuitionApplication;
import fyp.keng.mytuition.tutorselectdetails.TutorSelectDetailsFragment;
import fyp.keng.mytuition.utils.ActivityUtils;



public class ReserveCalendarActivity extends AppCompatActivity {

    private ReserveCalendarFragment reserveCalendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_calendar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Book a session");

        MyTuitionApplication app = (MyTuitionApplication)  getApplication();

        reserveCalendarFragment = (ReserveCalendarFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (reserveCalendarFragment == null) {
            reserveCalendarFragment = ReserveCalendarFragment.newInstance(
                    getIntent().getIntExtra(TutorSelectDetailsFragment.TUTOR_ID, -1),
                    getIntent().getIntExtra(TutorSelectDetailsFragment.TUTOR_PRICE, -1),
                    getIntent().getBooleanExtra(TutorSelectDetailsFragment.ALREADY_PAIRED, false));

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    reserveCalendarFragment, R.id.contentFrame);
        }

        new ReserveCalendarPresenter(
                app.repository,
                reserveCalendarFragment
        );
    }

    @Override
    protected void onDestroy() {
        reserveCalendarFragment.onDestroy();
        super.onDestroy();
    }
}
