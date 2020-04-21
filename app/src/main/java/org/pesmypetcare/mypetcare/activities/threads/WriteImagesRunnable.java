package org.pesmypetcare.mypetcare.activities.threads;

import org.pesmypetcare.mypetcare.utilities.ImageManager;

/**
 * @author Albert Pinto
 */
public class WriteImagesRunnable implements Runnable {
    private String storageLocationPath;
    private String imageName;
    private byte[] imageBytes;

    public WriteImagesRunnable(String storageLocationPath, String imageName, byte[] imageBytes) {
        this.storageLocationPath = storageLocationPath;
        this.imageName = imageName;
        this.imageBytes = imageBytes;
    }

    @Override
    public void run() {
        ImageManager.writeImage(storageLocationPath, imageName, imageBytes);
    }
}
