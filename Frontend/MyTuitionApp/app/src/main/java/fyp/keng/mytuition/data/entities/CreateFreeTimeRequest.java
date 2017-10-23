package fyp.keng.mytuition.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;



public class CreateFreeTimeRequest {
    @SerializedName("start_time")
    @Expose
    private Date startTime;

    public CreateFreeTimeRequest(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
