package fyp.keng.mytuition.messaging;

import java.util.ArrayList;

import fyp.keng.mytuition.BasePresenter;
import fyp.keng.mytuition.BaseView;
import fyp.keng.mytuition.data.entities.APIError;
import fyp.keng.mytuition.data.entities.CreateMessageRequest;
import fyp.keng.mytuition.data.entities.User;
import fyp.keng.mytuition.data.entities.events.GeneralMessage;



public interface MessagingContract {

    interface View extends BaseView<MessagingContract.Presenter> {
        void addMessages(ArrayList<GeneralMessage> messages);

        void getMessagesFailed(ArrayList<APIError> errors);

        void setUser(User user);

        void setOtherUser(User user);

        void createMessageSucceeded(CreateMessageRequest req);

        void createMessageFailed(CreateMessageRequest req ,ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void getMessagesFrom(int userId, int fromOffset, int toOffset);

        void getUserByID(int userId);

        void createMessage(int receiverID, String content);
    }
}
