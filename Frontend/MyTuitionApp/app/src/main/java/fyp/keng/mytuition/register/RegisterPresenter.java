package fyp.keng.mytuition.register;

import java.io.File;

import fyp.keng.mytuition.data.entities.APIResponse;
import fyp.keng.mytuition.data.entities.AuthResponse;
import fyp.keng.mytuition.data.entities.DeviceRegisterRequest;
import fyp.keng.mytuition.data.entities.RegisterRequest;
import fyp.keng.mytuition.data.entities.User;
import fyp.keng.mytuition.data.source.MyTuitionRepository;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterPresenter implements RegisterContract.Presenter {

    private final MyTuitionRepository repository;

    private final RegisterContract.View view;

    public RegisterPresenter(MyTuitionRepository repository,
                                      RegisterContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String firstName, String lastName, String email, String password, String userType) {
        RegisterRequest req = new RegisterRequest(firstName, lastName, email, password, userType);

        repository.register(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    repository.registerUserDevice(new DeviceRegisterRequest());
                    view.onRegisterSuccess();
                } else {
                    view.onRegisterFail(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                view.onRegisterFail(null);
            }
        });
    }

    @Override
    public void setAvatar(String avatarUri) {
        File avatarFile = new File(avatarUri);
        RequestBody req = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), req);

        this.repository.changeAvatar(body, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse resp = response.body();
                if (resp.isSuccessful()) {
                    view.onAvatarSuccess();
                } else {
                    view.onAvatarFailed(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                view.onAvatarFailed(null);
            }
        });
    }
}
