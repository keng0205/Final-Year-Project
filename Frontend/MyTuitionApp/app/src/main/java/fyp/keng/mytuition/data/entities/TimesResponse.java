package fyp.keng.mytuition.data.entities;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class TimesResponse {

    @SerializedName("own_events")
    @Expose
    ArrayList<WeekViewEvent> ownEvents = new ArrayList<>();

    @SerializedName("reserved_events")
    @Expose
    ArrayList<WeekViewEvent> reservedEvents = new ArrayList<>();

    public ArrayList<WeekViewEvent> getOwnEvents() {
        return ownEvents;
    }

    public ArrayList<WeekViewEvent> getReservedEvents() {
        return reservedEvents;
    }

    public void setOwnEvents(ArrayList<WeekViewEvent> ownEvents) {
        this.ownEvents = ownEvents;
    }

    public void setReservedEvents(ArrayList<WeekViewEvent> reservedEvents) {
        this.reservedEvents = reservedEvents;
    }
}
