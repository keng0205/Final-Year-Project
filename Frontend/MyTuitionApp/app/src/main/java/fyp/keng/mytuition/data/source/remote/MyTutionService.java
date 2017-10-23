package fyp.keng.mytuition.data.source.remote;

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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyTutionService {
    //return arraylist of subjects
    //completed
    @GET("?r=subjects")
    Call<APIResponse<ArrayList<Subject>>> getSubjects();
    //return arraylist of user for particular subject
    //https://tutee.herokuapp.com/subject/1/tutors
    //completed
    @GET("?r=subjects/subject-tutors")
    Call<APIResponse<ArrayList<User>>> getTutorsBySubject(@Query("id") int subjectID);
    //return user
    //completed
    @POST("?r=auth/login")
    Call<APIResponse<AuthResponse>> basicLogin(@Body LoginRequest req);
    //set user's device_token
    //completed
    @POST("?r=user/register-device")
    Call<APIResponse> registerUserDevice(@Body DeviceRegisterRequest req);
    //remove user's device_token
    //completed
    @PUT("?r=user/remove-device")
    Call<APIResponse> removeUserDevice(@Body DeviceRegisterRequest req);
    //done
    @POST("?r=auth/register")
    Call<APIResponse<AuthResponse>> register(@Body RegisterRequest req);
    //done
    @POST("?r=user/register-tutor-extra")
    Call<APIResponse> registerTutorExtra(@Body RegisterTutorExtraRequest req);
    //done
    @PUT("?r=user/update-profile")
    Call<APIResponse<User>> updateUser(@Body UpdateUserRequest req);

    @Multipart
    @POST("?r=user/change-avatar")
    Call<APIResponse<User>> changeAvatar(@Part MultipartBody.Part file);
    //done
    @POST("?r=tutorship")
    Call<APIResponse> createTutorship(@Body CreateTutorshipRequest req);
    //done
    @GET("?r=tutorship/get-tutorship")
    Call<APIResponse<TutorshipsResponse>> getTutorships();
    //https://tutee.herokuapp.com/message/69?from=0&to=10
    //done
    @GET("?r=message/get-messages")
    Call<APIResponse<ArrayList<GeneralMessage>>> getMessagesFrom(@Query("id") int userId,
                                                                 @Query("from") int fromOffset,
                                                                 @Query("to") int toOffset);
    //done
    @POST("?r=message")
    Call<APIResponse<GeneralMessage>> createMessage(@Body CreateMessageRequest req);

    @GET("?r=message/latest")
    Call<APIResponse<ArrayList<GeneralMessage>>> getLatestMessages();
    //done
    @POST("?r=event/")
    Call<APIResponse<WeekViewEvent>> createFreeTime(@Body CreateFreeTimeRequest req);
    //should be ok?
    @PUT("?r=event/remove")
    Call<APIResponse> removeTime(@Body WeekViewEvent event);
    //done
    @GET("?r=event/get-available-event")
    Call<APIResponse<ArrayList<WeekViewEvent>>> getFreeTimes(@Query("id") int tutorID);
    //done
    @GET("?r=event/get-event")
    Call<APIResponse<TimesResponse>> getTimes();
    //done
    @PUT("?r=event/reserve")
    Call<APIResponse> reserveTime(@Body WeekViewEvent event);
}
