package fyp.keng.mytuition.usertypeselection;

import fyp.keng.mytuition.data.source.MyTuitionRepository;

public class UserTypeSelectionPresenter implements UserTypeSelectionContract.Presenter {

    private final MyTuitionRepository repository;

    private final UserTypeSelectionContract.View view;

    public UserTypeSelectionPresenter(MyTuitionRepository repository,
                                      UserTypeSelectionContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
