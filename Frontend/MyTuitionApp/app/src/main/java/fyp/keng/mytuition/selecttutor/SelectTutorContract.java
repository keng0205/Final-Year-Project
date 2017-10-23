package fyp.keng.mytuition.selecttutor;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;
import fyp.keng.mytuition.data.entities.User;

public interface SelectTutorContract {
    interface Presenter extends BasePresenter{

        void getTutorsBySubjectID(int subjectID);
    }

    interface View extends BaseView<Presenter> {
        void setTutors(ArrayList<User> tutors);

        void getTutorsFailed(ArrayList<APIError> errors);
    }
}
