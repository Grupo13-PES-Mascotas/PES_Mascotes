package org.pesmypetcare.mypetcare.utilities;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Albert Pinto
 */
public class MessagingService extends FirebaseMessagingService {
    private static MessagingServiceCommunication communication;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        if (MainActivity.isEnableLoginActivity()) {
            communication.sendMessageToken(s);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("group") + " - " + data.get("forum");
        String text = data.get("creator") + " " + getString(R.string.new_post_received);
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();

        communication.schedulePostNotification(title, text, time);
    }

    /**
     * Set the communication instance.
     * @param communication The communication instance to set
     */
    public static void setCommunication(MessagingServiceCommunication communication) {
        MessagingService.communication = communication;
    }
}
