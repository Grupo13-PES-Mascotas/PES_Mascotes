package org.pesmypetcare.mypetcare.utilities;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageManager {
    public static final String PROFILE_IMAGES_PATH = "petProfileImages";
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
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readImage(String relativePath, String imageName) throws IOException {
        FileInputStream input = new FileInputStream(PATH + File.separator + relativePath + File.separator
            + imageName + EXTENSION);

        ArrayList<Byte> bytesList = new ArrayList<>();
        int actualByte = input.read();

        while (actualByte != -1) {
            bytesList.add((byte) actualByte);
            actualByte = input.read();
        }

        byte[] bytes = new byte[bytesList.size()];
        for (int actual = 0; actual < bytes.length; ++actual) {
            bytes[actual] = bytesList.get(actual);
        }

        return bytes;
    }
}
