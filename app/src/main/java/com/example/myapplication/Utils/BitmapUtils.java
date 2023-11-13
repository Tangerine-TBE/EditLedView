package com.example.myapplication.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;

public class BitmapUtils {

    public static Bitmap convertToBlackAndWhite(Bitmap bitmap, int threshold) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap blackAndWhiteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);

            int average = (red + green + blue) / 3;

            if (average > threshold) {
                pixels[i] = Color.WHITE;
            } else {
                pixels[i] = Color.BLACK;
            }
        }

        blackAndWhiteBitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return blackAndWhiteBitmap;
    }
}
