package fyp.keng.mytuition.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import fyp.keng.mytuition.MyTuitionApplication;

public class TuteeFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        if (token != null) {
            MyTuitionApplication app = (MyTuitionApplication)  getApplication();
            app.setDeviceToken(token);
        }
    }
}
