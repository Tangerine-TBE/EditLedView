package com.example.myapplication.Utils;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

public class BitmapUtils {

    public static Bitmap convertToBMW(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        int[] arrayOfInt = new int[i * j];
        paramBitmap.getPixels(arrayOfInt, 0, i, 0, 0, i, j);
        for (byte b = 0; b < j; b++) {
            for (byte b1 = 0; b1 < i; b1++) {
                byte b2;
                boolean bool;
                int k = i * b + b1;
                int m = arrayOfInt[k];
                char c = 'y';
                if ((0xFF0000 & m) >> 16 > paramInt3) {
                    b2 = (byte) 255;
                } else {
                    b2 = 0;
                }
                bool = (m & 0xFF) > paramInt3;
                if ((0xFF00 & m) >> 8 <= paramInt3)
                    c = Character.MIN_VALUE;
                arrayOfInt[k] = (bool ? 0xFF000000 : 0) | (b2 << 16) | (c << 8) | (m & 0xFF);
                if (arrayOfInt[k] == -1) {
                    arrayOfInt[k] = -1;
                } else {
                    arrayOfInt[k] = -16777216;
                }
            }
        }
        paramBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        paramBitmap.setPixels(arrayOfInt, 0, i, 0, 0, i, j);
        return ThumbnailUtils.extractThumbnail(paramBitmap, paramInt1, paramInt2);
    }
}
