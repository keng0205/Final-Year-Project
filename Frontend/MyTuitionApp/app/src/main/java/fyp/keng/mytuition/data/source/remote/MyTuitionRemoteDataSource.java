package fyp.keng.mytuition.data.source.remote;

import android.content.Context;

import com.alamkanak.weekview.WeekViewEvent;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
import fyp.keng.mytuition.data.source.MyTuitionDataSource;
import fyp.keng.mytuition.utils.EmptyCallback;
import io.gsonfire.DateSerializationPolicy;
import io.gsonfire.GsonFireBuilder;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTuitionRemoteDataSource implements MyTuitionDataSource {
    private static MyTuitionRemoteDataSource instance;

    private Retrofit retrofit;
    private MyTutionService service;
    private Context context;

    public MyTuitionRemoteDataSource(Context context) {
        this.context = context;
        retrofit = buildUnauthenticatedRetrofit();
        service = retrofit.create(MyTutionService.class);
    }

    public static MyTuitionRemoteDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new MyTuitionRemoteDataSource(context);
        }

        return instance;
    }

    public void setToken(String authToken) {
        retrofit = buildAuthenticatedRetrofit(authToken);
        this.service = retrofit.create(MyTutionService.class);
    }

    private Gson buildGson() {
        GsonFireBuilder builder = new GsonFireBuilder();
        builder.dateSerializationPolicy(DateSerializationPolicy.rfc3339);
        return builder.createGson();
    }

    private Retrofit buildAuthenticatedRetrofit(final String authToken) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new StethoInterceptor());

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", authToken)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        })
        .addInterceptor(logging);

        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.95:8216")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .build();

        return retrofit;
    }

    private Retrofit buildUnauthenticatedRetrofit () {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new StethoInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.95:8216")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .build();

        return retrofit;
    }

    @Override
    public void basicLogin(LoginRequest req, Callback<APIResponse<AuthResponse>> cb) {
        Call<APIResponse<AuthResponse>> call = service.basicLogin(req);
        call.enqueue(cb);
    }

    @Override
    public void googleLogin(String token) {

    }

    @Override
    public void changeAvatar(MultipartBody.Part body, Callback<APIResponse<User>> cb) {
        Call<APIResponse<User>> call = service.changeAvatar(body);
        call.enqueue(cb);
    }

    @Override
    public void logOut() {
        this.retrofit = buildUnauthenticatedRetrofit();
    }

    @Override
    public void register(RegisterRequest req, Callback<APIResponse<AuthResponse>> cb) {
        Call<APIResponse<AuthResponse>> call = service.register(req);
        call.enqueue(cb);
    }

    @Override
    public void registerTutorExtra(RegisterTutorExtraRequest req, Callback<APIResponse> cb) {
        Call<APIResponse> call = service.registerTutorExtra(req);
        call.enqueue(cb);
    }

    @Override
    public void registerUserDevice(DeviceRegisterRequest req) {
        Call call = service.registerUserDevice(req);
        call.enqueue(new EmptyCallback());
    }

    @Override
    public void removeUserDevice(DeviceRegisterRequest req) {
        Call call = service.removeUserDevice(req);
        call.enqueue(new EmptyCallback());
    }

    @Override
    public void getUser(int userID, Callback<APIResponse<User>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Not implemented!"));
    }

    @Override
    public void updateUser(UpdateUserRequest req, Callback<APIResponse<User>> cb) {
        Call<APIResponse<User>> call = service.updateUser(req);
        call.enqueue(cb);
    }

    @Override
    public void getSubjects(Callback<APIResponse<ArrayList<Subject>>> cb) {
        Call<APIResponse<ArrayList<Subject>>> call = service.getSubjects();
        call.enqueue(cb);
    }

    @Override
    public void getTutorsBySubject(int subjectID, Callback<APIResponse<ArrayList<User>>> cb) {
        Call<APIResponse<ArrayList<User>>> call = service.getTutorsBySubject(subjectID);
        call.enqueue(cb);
    }

    @Override
    public void getMessagesFrom(int userId, int fromOffset, int toOffset, Callback<APIResponse<ArrayList<GeneralMessage>>> cb) {
        Call<APIResponse<ArrayList<GeneralMessage>>> call = service.getMessagesFrom(userId, fromOffset, toOffset);
        call.enqueue(cb);
    }

    @Override
    public void createTutorship(CreateTutorshipRequest req, Callback<APIResponse> cb) {
        Call<APIResponse> call = service.createTutorship(req);
        call.enqueue(cb);
    }

    @Override
    public void getTutorships(Callback<APIResponse<TutorshipsResponse>> cb) {
        Call<APIResponse<TutorshipsResponse>> call = service.getTutorships();
        call.enqueue(cb);
    }

    @Override
    public void createMessage(CreateMessageRequest req, Callback<APIResponse<GeneralMessage>> cb) {
        Call<APIResponse<GeneralMessage>> call = service.createMessage(req);
        call.enqueue(cb);
    }

    @Override
    public void getLatestMessages(Callback<APIResponse<ArrayList<GeneralMessage>>> cb) {
        Call<APIResponse<ArrayList<GeneralMessage>>> call = service.getLatestMessages();
        call.enqueue(cb);
    }

    @Override
    public void createFreeTime(CreateFreeTimeRequest req, Callback<APIResponse<WeekViewEvent>> cb) {
        Call<APIResponse<WeekViewEvent>> call = service.createFreeTime(req);
        call.enqueue(cb);
    }

    @Override
    public void removeTime(WeekViewEvent event, Callback<APIResponse> cb) {
        Call<APIResponse> call = service.removeTime(event);
        call.enqueue(cb);
    }

    @Override
    public void getFreeTimes(int tutorID, Callback<APIResponse<ArrayList<WeekViewEvent>>> cb) {
        Call<APIResponse<ArrayList<WeekViewEvent>>> call = service.getFreeTimes(tutorID);
        call.enqueue(cb);
    }

    @Override
    public void reserveTime(WeekViewEvent event, Callback<APIResponse> cb) {
        Call<APIResponse> call = service.reserveTime(event);
        call.enqueue(cb);
    }

    @Override
    public void getTimes(Callback<APIResponse<TimesResponse>> cb) {
        Call<APIResponse<TimesResponse>> call = service.getTimes();
        call.enqueue(cb);
    }

    @Override
    public boolean isUserTutor(User user) {
        return false;
    }


}
