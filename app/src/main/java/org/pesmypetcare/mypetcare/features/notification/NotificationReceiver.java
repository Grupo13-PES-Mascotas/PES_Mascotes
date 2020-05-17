package org.pesmypetcare.mypetcare.features.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;

import java.util.Objects;


public class NotificationReceiver extends BroadcastReceiver {
    private static String channelId = "0";
    private static int requestCode = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = getBuilder(context, intent);
        PendingIntent notifyPendingIntent = getPendingIntent(context);
        NotificationManager notificationManager = getNotificationManager(context, builder, notifyPendingIntent);
        int id = Integer.parseInt(Objects.requireNonNull(
                intent.getStringExtra(context.getString(R.string.notificationid))));
        assert notificationManager != null;
        notificationManager.notify(id, builder.build());
        channelId = Integer.toString(Integer.parseInt(channelId) + 1);
    }

    /**
     * Get the Notification Manager.
     * @param context The context
     * @param builder The notification builder
     * @param notifyPendingIntent The pending intent
     * @return The Notification Manager
     */
    private NotificationManager getNotificationManager(Context context, NotificationCompat.Builder builder,
        PendingIntent notifyPendingIntent) {
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O) {
            int importance = NotificationManager. IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                context.getString(R.string.notificationChannelName), importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return notificationManager;
    }

    /**
     * Get the pending intent to redirect the notification to the app.
     * @param context The context
     * @return The pending intent
     */
    private PendingIntent getPendingIntent(Context context) {
        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        ++requestCode;
        return notifyPendingIntent;
    }

    /**
     * Initializes the notification.
     * @param context The context
     * @param intent The intent
     * @return The builder
     */
    private NotificationCompat.Builder getBuilder(Context context, Intent intent) {
        return new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(intent.getStringExtra(context.getString(R.string.title)))
                    .setContentText(intent.getStringExtra(context.getString(R.string.text)))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

}
