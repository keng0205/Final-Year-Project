package fyp.keng.mytuition.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class UpdateUserRequest {
    @SerializedName("user")
    @Expose
    private User user;

    public UpdateUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
