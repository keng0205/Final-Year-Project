package fyp.keng.mytuition.reservecalendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fyp.keng.mytuition.data.entities.APIResponse;
import fyp.keng.mytuition.data.entities.CreateTutorshipRequest;
import fyp.keng.mytuition.data.source.MyTuitionRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReserveCalendarPresenter implements ReserveCalendarContract.Presenter {

    private final MyTuitionRepository repository;

    private final ReserveCalendarContract.View view;

    public ReserveCalendarPresenter(MyTuitionRepository repository,
                                  ReserveCalendarContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getFreeTimes(int tutorID)  {
        this.repository.getFreeTimes(tutorID, new Callback<APIResponse<ArrayList<WeekViewEvent>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<WeekViewEvent>>> call, Response<APIResponse<ArrayList<WeekViewEvent>>> response) {
                APIResponse<ArrayList<WeekViewEvent>> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    ArrayList<WeekViewEvent> events = resp.getResponse();
                    view.setFreeTimes(events);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<WeekViewEvent>>> call, Throwable t) {
                // TODO
            }
        });

    }

    @Override
    public void reserveTime(WeekViewEvent event) {
        this.repository.reserveTime(event, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.onReserveTimeSuccess();
                } else {
                    view.onReserveTimeFail(resp.getErrors());
                }

            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                view.onReserveTimeFail(null);
            }
        });
    }
    @Override
    public void pairWithTutor(int tutorID) {
        CreateTutorshipRequest req = new CreateTutorshipRequest(tutorID);

        repository.createTutorship(req, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                   // view.pairTutorSucceeded();
                } else {
                   // view.pairTutorFailed(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
               // view.pairTutorFailed(null);
            }
        });
    }
}
