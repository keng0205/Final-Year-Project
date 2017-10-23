package fyp.keng.mytuition.pickauthentication;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;
import fyp.keng.mytuition.data.entities.User;



public interface AuthenticationContract {
    interface View extends BaseView<Presenter> {
        void loginSucceeded(User currentUser);

        void loginFailed(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }
}
