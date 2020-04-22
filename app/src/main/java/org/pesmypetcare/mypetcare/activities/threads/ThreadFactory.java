package org.pesmypetcare.mypetcare.activities.threads;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author Albert Pinto
 */
public class ThreadFactory {
    private ThreadFactory() {

    }

    public static Thread createWriteImageThread(String storagePath, String imageName, byte[] imageBytes) {
        return new Thread(new WriteImagesRunnable(storagePath, imageName, imageBytes));
    }

    /**
     * Create the ask permission thread.
     * @return The ask permission thread
     */
    public static Thread createAskPermissionThread(Activity activity) {
        return new Thread(() -> {
            int permissionCheck = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                while (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    permissionCheck = ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });
    }
}
