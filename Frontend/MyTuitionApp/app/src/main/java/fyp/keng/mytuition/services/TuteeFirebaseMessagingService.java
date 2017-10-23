package fyp.keng.mytuition.services;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import fyp.keng.mytuition.data.entities.events.GeneralMessage;

import static com.google.android.gms.internal.zzt.TAG;

public class TuteeFirebaseMessagingService extends FirebaseMessagingService {

    // Maps event types to classes to deserialize into Java objects
    private final static Map<String, Class> eventsMap = new HashMap<String, Class>(){{
        this.put("general", GeneralMessage.class);
    }};

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
/*
        if (remoteMessage.getData().size() > 0) {
            Gson gson = new Gson();

            Map<String, String> dataMap = remoteMessage.getData();
            String type = dataMap.get("type");
            String payload = dataMap.get("payload");

            if (eventsMap.containsKey(type) && !TextUtils.isEmpty(payload)) {
                Class messageClass = eventsMap.get(type);
                Object event = gson.fromJson(payload, messageClass);
                EventBus.getDefault().post(event);

            }
        }*/
        Log.d(TAG, "From: " + remoteMessage.getNotification().getBody());


        Gson gson = new Gson();




        Log.d(TAG, "Check: " + remoteMessage.getNotification().getBody());
        Object event = gson.fromJson(remoteMessage.getNotification().getBody(), GeneralMessage.class);
        EventBus.getDefault().post(event);
    }
}
