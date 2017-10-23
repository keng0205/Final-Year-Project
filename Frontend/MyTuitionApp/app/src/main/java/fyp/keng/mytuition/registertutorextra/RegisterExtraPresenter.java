package fyp.keng.mytuition.registertutorextra;

import java.util.ArrayList;

import fyp.keng.mytuition.data.entities.APIResponse;
import fyp.keng.mytuition.data.entities.RegisterTutorExtraRequest;
import fyp.keng.mytuition.data.entities.Subject;
import fyp.keng.mytuition.data.source.MyTuitionRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterExtraPresenter implements RegisterExtraContract.Presenter {

    private final MyTuitionRepository repository;

    private final RegisterExtraContract.View view;

    public RegisterExtraPresenter(MyTuitionRepository repository,
                                  RegisterExtraContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void registerTutorExtra(String description, ArrayList<Subject> subjects, int price) {
        RegisterTutorExtraRequest req = new RegisterTutorExtraRequest(description, subjects, price);

        repository.registerTutorExtra(req, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.onRegisterSuccess();
                } else {
                    view.onRegisterFail(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                view.onRegisterFail(null);
            }
        });
    }

    @Override
    public void getSubjects() {
        this.repository.getSubjects(new Callback<APIResponse<ArrayList<Subject>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<Subject>>> call, Response<APIResponse<ArrayList<Subject>>> response) {
                APIResponse<ArrayList<Subject>> resp = response.body();

                if (resp.isSuccessful()) {
                    ArrayList<Subject> subjects = resp.getResponse();
                    view.setSubjects(subjects);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<Subject>>> call, Throwable t) {
                // TODO
            }
        });

    }
}
