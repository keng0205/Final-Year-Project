package fyp.keng.mytuition.registertutorextra;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;
import fyp.keng.mytuition.data.entities.Subject;



public interface RegisterExtraContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail(ArrayList<APIError> errors);

        void setSubjects(ArrayList<Subject> subjects);
    }

    interface Presenter extends BasePresenter {
        void registerTutorExtra(String description, ArrayList<Subject> subjects, int price);
        void getSubjects();
    }
}
