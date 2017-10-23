package fyp.keng.mytuition.tutorselectdetails;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;
import fyp.keng.mytuition.data.entities.User;

interface TutorSelectDetailsContract {

    interface View extends BaseView<Presenter> {
        void setTutor(User user);

        void pairTutorSucceeded();
        void pairTutorFailed(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void getTutorByID(int tutorID);
        void pairWithTutor(int tutorID);

        User getCurrentUser();

        boolean alreadyPairedWith(User user);
    }
}
