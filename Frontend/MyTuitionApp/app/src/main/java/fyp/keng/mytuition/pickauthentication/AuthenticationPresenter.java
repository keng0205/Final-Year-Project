package fyp.keng.mytuition.pickauthentication;

import fyp.keng.mytuition.data.entities.APIResponse;
import fyp.keng.mytuition.data.entities.AuthResponse;
import fyp.keng.mytuition.data.entities.DeviceRegisterRequest;
import fyp.keng.mytuition.data.entities.LoginRequest;
import fyp.keng.mytuition.data.source.MyTuitionRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationPresenter implements AuthenticationContract.Presenter {

    private final MyTuitionRepository repository;

    private final AuthenticationContract.View view;

    public AuthenticationPresenter(MyTuitionRepository repository,
                                      AuthenticationContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {


    }

    @Override
    public void login(String email, String password) {
        LoginRequest req = new LoginRequest(email, password);
        //try use assyn to login
        repository.basicLogin(req, new Callback<APIResponse<AuthResponse>>() {
            //onsucess
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse<AuthResponse> resp = response.body();

                if (resp.isSuccessful()) {
                    repository.registerUserDevice(new DeviceRegisterRequest());
                    AuthResponse authResponse = resp.getResponse();
                    view.loginSucceeded(authResponse.getUser());
                } else {
                    view.loginFailed(resp.getErrors());
                }
            }
            //onfailed

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                view.loginFailed(null);
            }
        });
    }
}
