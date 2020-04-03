package org.pesmypetcare.mypetcare.utilities;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageManager {
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator + "MyPetCare";
    private static final String EXTENSION = ".jpg";

    private ImageManager() {

    }

    public static byte[] getImageBytes(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static void writeImage(String relativePath, String fileName, byte[] bytes) {
        File file = new File(PATH + File.separator + relativePath);
        file.mkdirs();
        try {
            FileOutputStream output = new FileOutputStream(PATH + File.separator + relativePath
                + File.separator + fileName + EXTENSION);
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
