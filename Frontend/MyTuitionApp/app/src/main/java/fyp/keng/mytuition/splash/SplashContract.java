package fyp.keng.mytuition.splash;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.AuthResponse;

public interface SplashContract {

    interface Presenter extends BasePresenter {
        AuthResponse getAutoLoginInfo();
    }

    interface View extends BaseView<Presenter> {
        void goToNextActivity();
    }
}
