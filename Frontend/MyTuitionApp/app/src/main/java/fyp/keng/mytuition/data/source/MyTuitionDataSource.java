package fyp.keng.mytuition.data.source;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fyp.keng.mytuition.data.entities.APIResponse;
import fyp.keng.mytuition.data.entities.AuthResponse;
import fyp.keng.mytuition.data.entities.CreateFreeTimeRequest;
import fyp.keng.mytuition.data.entities.CreateMessageRequest;
import fyp.keng.mytuition.data.entities.CreateTutorshipRequest;
import fyp.keng.mytuition.data.entities.DeviceRegisterRequest;
import fyp.keng.mytuition.data.entities.LoginRequest;
import fyp.keng.mytuition.data.entities.RegisterRequest;
import fyp.keng.mytuition.data.entities.RegisterTutorExtraRequest;
import fyp.keng.mytuition.data.entities.Subject;
import fyp.keng.mytuition.data.entities.TimesResponse;
import fyp.keng.mytuition.data.entities.TutorshipsResponse;
import fyp.keng.mytuition.data.entities.UpdateUserRequest;
import fyp.keng.mytuition.data.entities.User;
import fyp.keng.mytuition.data.entities.events.GeneralMessage;
import okhttp3.MultipartBody;
import retrofit2.Callback;


public interface MyTuitionDataSource {
    // Auth
    void basicLogin(LoginRequest req, Callback<APIResponse<AuthResponse>> cb);

    void googleLogin(String token);

    void changeAvatar(MultipartBody.Part body, Callback<APIResponse<User>> cb);

    void logOut();

    void register(RegisterRequest req, Callback<APIResponse<AuthResponse>> cb);

    void registerTutorExtra(RegisterTutorExtraRequest req, Callback<APIResponse> cb);

    // User
    void registerUserDevice(DeviceRegisterRequest req);

    void removeUserDevice(DeviceRegisterRequest req);

    void getUser(int userID, Callback<APIResponse<User>> cb);

    void updateUser(UpdateUserRequest req, Callback<APIResponse<User>> cb);

    // Subjects
    void getSubjects(Callback<APIResponse<ArrayList<Subject>>> cb);

    void getTutorsBySubject(int subjectID, Callback<APIResponse<ArrayList<User>>> cb);

    // Tutorships
    void createTutorship(CreateTutorshipRequest req, Callback<APIResponse> cb);

    void getTutorships(Callback<APIResponse<TutorshipsResponse>> cb);

    // Messages
    void getMessagesFrom(int userId, int fromOffset, int toOffset, Callback<APIResponse<ArrayList<GeneralMessage>>> cb);

    void createMessage(CreateMessageRequest req, Callback<APIResponse<GeneralMessage>> cb);

    void getLatestMessages(Callback<APIResponse<ArrayList<GeneralMessage>>> cb);

    // Events
    void createFreeTime(CreateFreeTimeRequest req, Callback<APIResponse<WeekViewEvent>> cb);

    void removeTime(WeekViewEvent event, Callback<APIResponse> cb);

    void getFreeTimes(int tutorID, Callback<APIResponse<ArrayList<WeekViewEvent>>> cb);

    void reserveTime(WeekViewEvent event, Callback<APIResponse> cb);

    void getTimes(Callback<APIResponse<TimesResponse>> cb);

    // Other misc. helpers
    boolean isUserTutor(User user);
}
