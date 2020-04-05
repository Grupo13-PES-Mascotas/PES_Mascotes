package org.pesmypetcare.mypetcare.utilities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {
    public static final String PET_PROFILE_IMAGES_PATH = "petProfileImages";
    public static final String USER_PROFILE_IMAGES_PATH = "userProfileImages";
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator + "MyPetCare";
    private static final String EXTENSION = ".jpg";
    private static final int QUALITY = 100;
    private static Bitmap defaultPetImage;
    private static byte[] defaultBytesPetImage;

    private ImageManager() {

    }

    /**
     * Get the bytes of the bitmap image.
     * @param image The bitmap that the bytes has to be obtained
     * @return The bytes of the bitmap
     */
    public static byte[] getImageBytes(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, QUALITY, stream);
        byte[] bytes = stream.toByteArray();

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    /**
     * Write an image to a file.
     * @param relativePath The path inside the folder with application data
     * @param fileName The name of the file that has to be written
     * @param bytes The bytes that has to be written
     */
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

    /**
     * Read an image form a file.
     * @param relativePath The path inside the folder with application data
     * @param imageName The name of the image that has to be read
     * @return The bytes of the image that has been read
     * @throws IOException The file does not exist
     */
    public static byte[] readImage(String relativePath, String imageName) throws IOException {
        /*FileInputStream input = new FileInputStream(PATH + File.separator + relativePath + File.separator
            + imageName + EXTENSION);

        ArrayList<Byte> bytesList = new ArrayList<>();
        int actualByte = input.read();

        while (actualByte != -1) {
            bytesList.add((byte) actualByte);
            actualByte = input.read();
        }

        byte[] bytes = convertToByteArray(bytesList);
        input.close();*/

        String imagePath = PATH + File.separator + relativePath + File.separator + imageName + EXTENSION;
        FileChannel channel = new FileInputStream(imagePath).getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        return bytes;
    }

    /**
     * Converts an ArrayList of bytes to an array of bytes.
     * @param bytesList The ArrayList of bytes
     * @return The array that contains the bytes from the ArrayList
     */
    private static byte[] convertToByteArray(List<Byte> bytesList) {
        byte[] bytes = new byte[bytesList.size()];
        for (int actual = 0; actual < bytes.length; ++actual) {
            bytes[actual] = bytesList.get(actual);
        }
        return bytes;
    }

    /**
     * Set the public default image of the pet.
     * @param drawable The default image of the pet
     */
    public static void setPetDefaultImage(Drawable drawable) {
        defaultPetImage = ((BitmapDrawable) drawable).getBitmap();
        defaultBytesPetImage = getImageBytes(defaultPetImage);
    }

    /**
     * Delete an image from local storage.
     * @param relativePath relativePath The path inside the folder with application data
     * @param imageName The name of the image that has to be deleted
     */
    public static void deleteImage(String relativePath, String imageName) {
        File image = new File(PATH + File.separator + relativePath + File.separator + imageName + EXTENSION);
        image.delete();
    }
}
