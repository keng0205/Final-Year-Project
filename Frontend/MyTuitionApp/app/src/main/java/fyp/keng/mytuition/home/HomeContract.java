package fyp.keng.mytuition.home;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.Subject;
import fyp.keng.mytuition.data.entities.TimesResponse;
import fyp.keng.mytuition.data.entities.User;
import fyp.keng.mytuition.data.entities.events.GeneralMessage;

public interface HomeContract {
    interface Presenter extends BasePresenter{
        void logOut();

        void getTutorships();

        void getSubjects();
        void getLatestMessages();

        void createFreeTime(WeekViewEvent event);
        void removeTime(WeekViewEvent event);

        User getUser();
        void getTimes();
    }

    interface View extends BaseView<Presenter> {
        void setMessageUsers(ArrayList<User> users);
        void setTutorNames(ArrayList<User> tutors);
        void setTuteeNames(ArrayList<User> tutees);
        void setSubjects(ArrayList<Subject> subjects);
        void setLatestMessages(ArrayList<GeneralMessage> latestMessages);

        void setTimes(TimesResponse events);
    }
}
