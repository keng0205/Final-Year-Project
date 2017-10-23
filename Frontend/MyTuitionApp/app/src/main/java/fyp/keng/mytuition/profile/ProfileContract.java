package fyp.keng.mytuition.profile;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;
import fyp.keng.mytuition.data.entities.User;



public class ProfileContract {

    interface Presenter extends BasePresenter {
        void updateUser(String firstName, String lastName, String description, int price);
        void changeAvatar(String avatarUri);
    }

    interface View extends BaseView<Presenter> {
        void setUser(User user);

        void onUpdateSuccess();
        void onUpdateFailure();

        void onAvatarFailed(ArrayList<APIError> errors );
        void onAvatarSuccess();
    }
}
