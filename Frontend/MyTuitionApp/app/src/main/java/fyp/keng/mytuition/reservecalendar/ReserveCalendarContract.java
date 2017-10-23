package fyp.keng.mytuition.reservecalendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;


public interface ReserveCalendarContract {

    interface View extends BaseView<ReserveCalendarContract.Presenter> {
        void setFreeTimes(ArrayList<WeekViewEvent> events);
        void onReserveTimeSuccess();
        void onReserveTimeFail(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void getFreeTimes(int tutorID);
        void reserveTime(WeekViewEvent event);
        void pairWithTutor(int tutorID);
    }
}
