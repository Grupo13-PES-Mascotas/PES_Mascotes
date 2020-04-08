package org.pesmypetcare.mypetcare.features.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;


public class NotificationReceiver extends BroadcastReceiver {
    private static String CHANNEL_ID = "0";
    private static int REQUEST_CODE;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = getBuilder(context, intent);
        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, REQUEST_CODE, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        ++REQUEST_CODE;
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context. NOTIFICATION_SERVICE);
        /*if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel( CHANNEL_ID ,
                "NOTIFICATION_CHANNEL_NAME" , importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }*/
        int id = intent.getIntExtra(intent.getParcelableExtra(
                context.getString(R.string.notificationid)), 0);
        assert notificationManager != null;
        notificationManager.notify(id , builder.build());
        CHANNEL_ID = Integer.toString(Integer.parseInt(CHANNEL_ID) + 1);
    }

    /**
     * Initializes the notification.
     * @param context The context
     * @param intent The intent
     * @return The builder
     */
    private NotificationCompat.Builder getBuilder(Context context, Intent intent) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(intent.getStringExtra(context.getString(R.string.title)))
                    .setContentText(intent.getStringExtra(context.getString(R.string.text)))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

}
