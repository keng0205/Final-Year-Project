package fyp.keng.mytuition.register;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;


public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail(ArrayList<APIError> errors);

        void onAvatarSuccess();
        void onAvatarFailed(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void register(String firstname, String lastname, String email, String password, String usertype);
        void setAvatar(String avatarUri);
    }
}
