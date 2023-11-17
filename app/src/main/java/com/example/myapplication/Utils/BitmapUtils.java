package com.example.myapplication.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.util.Log;

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

        return  ThumbnailUtils.extractThumbnail(blackAndWhiteBitmap,blackAndWhiteBitmap.getWidth(),blackAndWhiteBitmap.getHeight());
    }
    public static String getLedData(Bitmap paramBitmap) {
        String str2;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("bitmapToByteArray  bitmap ");
        if (paramBitmap == null) {
            str2 = "为空";
        } else {
            str2 = "不为空";
        }
        stringBuilder2.append(str2);
        Log.e("LedDataUtil", stringBuilder2.toString());
        StringBuilder stringBuilder1 = new StringBuilder();
        int j = paramBitmap.getWidth() * paramBitmap.getHeight();
        int[] arrayOfInt = new int[j];
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("bitmapToByteArray  width = ");
        stringBuilder2.append(paramBitmap.getWidth());
        stringBuilder2.append(" height = ");
        stringBuilder2.append(paramBitmap.getHeight());
        Log.e("LedDataUtil", stringBuilder2.toString());
        paramBitmap.getPixels(arrayOfInt, 0, paramBitmap.getWidth(), 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
        int i;
        for (i = 0; i < j; i++) {
            if (arrayOfInt[i] == -1) {
                stringBuilder1.append("0");
            } else {
                stringBuilder1.append("1");
            }
        }
        String str1 = stringBuilder1.toString();
        i = str1.length();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("imgStrLength = ");
        stringBuilder2.append(i);
        stringBuilder2.append("\n imgStr = ");
        stringBuilder2.append(str1);
        Log.e("LedDataUtil", stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("sbLength = ");
        stringBuilder2.append(stringBuilder1.length());
        stringBuilder2.append("\n sb = ");
        stringBuilder2.append(stringBuilder1.toString());
        Log.e("LedDataUtil", stringBuilder2.toString());
        return str1;
    }
}
