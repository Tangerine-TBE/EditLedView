package com.example.myapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.TypedValue;

public class ViewUtils {
    public static Bitmap blur(Bitmap paramBitmap, Context paramContext) {
        RenderScript renderScript = RenderScript.create(paramContext);
        Allocation allocation = Allocation.createFromBitmap(renderScript, paramBitmap);
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, allocation.getElement());
        scriptIntrinsicBlur.setInput(allocation);
        scriptIntrinsicBlur.setRadius(25.0F);
        scriptIntrinsicBlur.forEach(allocation);
        allocation.copyTo(paramBitmap);
        return paramBitmap;
    }

    public static float correctTextY(float paramFloat, Paint paramPaint) {
        return paramFloat - (paramPaint.ascent() + paramPaint.descent()) / 2.0F;
    }

    public static int dp2px(Context paramContext, int paramInt) {
        return (int) TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics());
    }

    public static int getColorBetweenAB(int paramInt1, int paramInt2, float paramFloat, int paramInt3) {
        int i = (0xFF0000 & paramInt1) >> 16;
        float f4 = (((paramInt2 & 0xFF0000) >> 16) - i) / paramFloat;
        float f2 = paramInt3;
        float f3 = i;
        paramInt3 = 0xFF00 & paramInt1;
        float f5 = ((paramInt2 & 0xFF00) - paramInt3 >> 8) / paramFloat;
        float f1 = (paramInt3 >> 8);
        paramInt1 &= 0xFF;
        paramFloat = ((paramInt2 & 0xFF) - paramInt1) / paramFloat;
        float f6 = paramInt1;
        return Color.rgb((int)(f4 * f2 + f3), (int)(f5 * f2 + f1), (int)(paramFloat * f2 + f6));
    }

    public static float getTextHeight(Paint paramPaint) {
        Paint.FontMetrics fontMetrics = paramPaint.getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    public static float getTextRectHeight(Paint paramPaint, String paramString) {
        Rect rect = new Rect();
        paramPaint.getTextBounds(paramString, 0, paramString.length(), rect);
        return rect.height();
    }

    public static float getTextRectWidth(Paint paramPaint, String paramString) {
        Rect rect = new Rect();
        paramPaint.getTextBounds(paramString, 0, paramString.length(), rect);
        return paramPaint.measureText(paramString);
    }

    public static float pixelsToDp(Context paramContext, float paramFloat) {
        return paramFloat / (paramContext.getResources().getDisplayMetrics()).densityDpi / 160.0F;
    }

    public static float px2Dp(int paramInt, Context paramContext) {
        return paramInt * (paramContext.getResources().getDisplayMetrics()).density;
    }
}
