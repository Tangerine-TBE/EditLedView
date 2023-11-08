package com.example.myapplication.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.Utils.PrefUtils;
import com.example.myapplication.Utils.ViewUtils;

public class LedView extends View {

    private int backgroundColor = Color.parseColor("#FFFFFF");

    private float bias = 0.0F;

    private int h;

    private boolean isCanTouch = true;

    private float ledBeadRadius;

    private int ledBrightColor = Color.parseColor("#F8171D");

    private int ledColSize = 11;

    private String ledData;

    private int ledSize = 11;

    private int lightOffColor = Color.parseColor("#ABADA8");

    private Paint mPaint;

    private long startTouchTime;

    private float startX = 0.0F;

    private float startY = 0.0F;

    private int w;

    public LedView(Context paramContext) {
        super(paramContext, null);
        init();
    }

    public LedView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet, 0);
        init();
    }

    public LedView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void drawAllLedBead(Canvas paramCanvas, int paramInt) {
        this.ledSize = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (paramInt == this.ledSize) {
            this.bias =0.0F;
        } else {
            this.bias = 0.0F;
        }
        this.mPaint.setColor(this.lightOffColor);
        for (byte b = 0; b < this.ledSize; b++) {
            for (byte b1 = 0; b1 < paramInt; b1++) {
                float f2 = this.ledBeadRadius;
                paramCanvas.drawCircle((float) b1 * f2 * 2.0F + f2 + this.bias, b * f2 * 2.0F + f2, f2, this.mPaint);
            }
        }
    }

    private void drawBrightLedBead(Canvas paramCanvas, String paramString) {
        this.mPaint.setColor(this.ledBrightColor);
        this.ledSize = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
        for (byte b = 0; b < this.ledSize; b++) {
            for (byte b1 = 0; b1 < this.ledColSize; b1++) {
                if (isLEDBeadBright(paramString, b, b1)) {
                    float f2 = b1;
                    float f1 = this.ledBeadRadius;
                    paramCanvas.drawCircle(f2 * f1 * 2.0F + f1 + this.bias, b * f1 * 2.0F + f1, f1, this.mPaint);
                }
            }
        }
    }

    private void drawMoveDot(boolean paramBoolean, float paramFloat1, float paramFloat2) {
        paramFloat1 = Math.max(0.0F, paramFloat1);
        float f = Math.max(0.0F, paramFloat2);
        paramFloat2 = this.ledBeadRadius;
        int i = (int) (f / paramFloat2 / 2.0F);
        int j = (int) ((paramFloat1 - this.bias) / paramFloat2 / 2.0F);
        if (i + 1 <= this.ledSize) {
            int k = this.ledColSize;
            if (j + 1 <= k) {
                String str1;
                k = k * i + j;
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("\n ");
                String str2 = "点击事件";
                if (paramBoolean) {
                    str1 = "点击事件";
                } else {
                    str1 = "触摸事件";
                }
                stringBuilder1.append(str1);
                stringBuilder1.append(" 格子Size = ");
                stringBuilder1.append(this.ledBeadRadius * 2.0F);
                stringBuilder1.append(", 正在滑动 [ x = ");
                stringBuilder1.append(paramFloat1);
                stringBuilder1.append(", y = ");
                stringBuilder1.append(f);
                stringBuilder1.append(" ] -- Index [ X = ");
                stringBuilder1.append(i);
                stringBuilder1.append(", Y = ");
                stringBuilder1.append(j);
                stringBuilder1.append(" ] index = ");
                stringBuilder1.append(k);
                Log.e("PY", stringBuilder1.toString());
                StringBuilder stringBuilder2 = new StringBuilder(this.ledData);
                if (k <= this.ledData.length() - 1) {
                    String str = "1";
                    str1 = str;
                    if (paramBoolean) {
                        str1 = str;
                        if (this.ledData.charAt(k) == '1') str1 = "0";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    if (!paramBoolean) str2 = "触摸事件";
                    stringBuilder.append(str2);
                    stringBuilder.append(" 坐标 = ");
                    stringBuilder.append(k);
                    stringBuilder.append(" 状态 = ");
                    stringBuilder.append(str1);
                    Log.e("PY", stringBuilder.toString());
                    stringBuilder2.replace(k, k + 1, str1);
                    this.ledData = stringBuilder2.toString();
                    invalidate();
                }
            }
        }
    }

    public static int getDefaultSize(int paramInt1, int paramInt2) {
        int i = View.MeasureSpec.getMode(paramInt2);
        paramInt2 = View.MeasureSpec.getSize(paramInt2);
        if (i == Integer.MIN_VALUE || i == 1073741824) paramInt1 = paramInt2;
        return paramInt1;
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.ledData = initLEDData();
    }

    private String initLEDData() {
        StringBuilder stringBuilder = new StringBuilder();
        this.ledSize = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
        byte b = 0;
        while (true) {
            int i = this.ledSize;
            if (b < i * i) {
                stringBuilder.append("0");
                b++;
                continue;
            }
            return stringBuilder.toString();
        }
    }

    private boolean isLEDBeadBright(String paramString, int paramInt1, int paramInt2) {
        boolean bool1 = TextUtils.isEmpty(paramString);
        boolean bool = false;
        if (bool1) return false;
        if (paramString.charAt(paramInt1 * this.ledColSize + paramInt2) == '1') bool = true;
        return bool;
    }

    public void clear() {
        this.ledData = initLEDData();
//        int i = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
//        this.ledSize = i;
//        this.ledBeadRadius = w / i * 2.0F;
        invalidate();
    }

    public String getLedData() {
        return this.ledData;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        this.ledSize = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ledColSize = ");
        stringBuilder.append(this.ledColSize);
        stringBuilder.append(" ledData = ");
        stringBuilder.append(this.ledData);
        Log.e("888888", stringBuilder.toString());
        drawAllLedBead(paramCanvas, this.ledColSize);
        if (this.ledData.length() == this.ledSize * this.ledColSize)
            drawBrightLedBead(paramCanvas, this.ledData);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("col size = ");
        stringBuilder.append(this.ledColSize);
        Log.e("LEDView OnMeasure", stringBuilder.toString());
        if (this.ledColSize * this.ledBeadRadius * 2.0F <= displayMetrics.widthPixels) {
            setMeasuredDimension(getDefaultSize(displayMetrics.widthPixels, paramInt1), getDefaultSize(ViewUtils.dp2px(getContext(), 11), paramInt2));
        } else {
            Log.d("LONG PIC", ">>>>>>>>>IS A LONG PIC");
            setMeasuredDimension(getDefaultSize((int) (this.ledColSize * this.ledBeadRadius * 2.0F + 1.0F), paramInt1), getDefaultSize(ViewUtils.dp2px(getContext(), 11), paramInt2));
        }
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.h = Math.min(paramInt1, paramInt2);
        paramInt2 = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
        this.ledSize = paramInt2;
        paramInt1 = this.h;
        this.ledBeadRadius = paramInt1 / paramInt2/2.0f;
        this.w = paramInt1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("初始化数据 宽高[ width = ");
        stringBuilder.append(this.w);
        stringBuilder.append(", height = ");
        stringBuilder.append(this.h);
        stringBuilder.append(" ]");
        Log.d("PY", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("初始化数据 直径 = ");
        stringBuilder.append(this.ledBeadRadius * 2.0F);
        stringBuilder.append(", 半径 = ");
        stringBuilder.append(this.ledBeadRadius);
        Log.d("PY", stringBuilder.toString());
        super.onSizeChanged(this.w, this.h, paramInt3, paramInt4);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        StringBuilder stringBuilder = null;
        int i = paramMotionEvent.getAction();
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    float f2 = paramMotionEvent.getX();
                    float f1 = paramMotionEvent.getY();
                    if (Math.abs(f2 - this.startX) > 10.0F || Math.abs(f1 - this.startY) > 10.0F) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Start  [ x = ");
                        stringBuilder.append(this.startX);
                        stringBuilder.append(", y = ");
                        stringBuilder.append(this.startY);
                        stringBuilder.append(" ] Move  [ x = ");
                        stringBuilder.append(f2);
                        stringBuilder.append(", y = ");
                        stringBuilder.append(f1);
                        stringBuilder.append(" ] 触摸差 [ x = ");
                        stringBuilder.append(Math.abs(f2 - this.startX));
                        stringBuilder.append(", y = ");
                        stringBuilder.append(Math.abs(f1 - this.startY));
                        stringBuilder.append(" ] ");
                        Log.d("PY", stringBuilder.toString());
                        drawMoveDot(false, f2, f1);
                    }
                }
            } else {
                float f1 = paramMotionEvent.getX();
                float f2 = paramMotionEvent.getY();
                long l = System.currentTimeMillis();
                stringBuilder = new StringBuilder();
                stringBuilder.append("松开--------startTouchTime = ");
                stringBuilder.append(this.startTouchTime);
                stringBuilder.append(" -- endTouchTime = ");
                stringBuilder.append(l);
                stringBuilder.append(" 时差 = ");
                stringBuilder.append(l - this.startTouchTime);
                Log.e("PY", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("松开 startX = ");
                stringBuilder.append(this.startX);
                stringBuilder.append(", startY = ");
                stringBuilder.append(this.startY);
                Log.d("PY", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("松开 endX = ");
                stringBuilder.append(f1);
                stringBuilder.append(", endY = ");
                stringBuilder.append(f2);
                Log.d("PY", stringBuilder.toString());
                if (l - this.startTouchTime <= 500L && Math.abs(f1 - this.startX) <= 10.0F && Math.abs(f2 - this.startY) <= 10.0F) {
                    Log.d("PY", "点击事件");
                    drawMoveDot(true, f1, f2);
                }
                Log.d("PY", "松开");
            }
        } else {
            this.startTouchTime = System.currentTimeMillis();
            Log.d("PY", "按下");
            this.startX = paramMotionEvent.getX();
            this.startY = paramMotionEvent.getY();
        }
        return this.isCanTouch;
    }

    public void reverse() {
        String str = this.ledData.replace('0', '2');
        this.ledData = str;
        str = str.replace('1', '3');
        this.ledData = str;
        str = str.replace('2', '1');
        this.ledData = str;
        this.ledData = str.replace('3', '0');
//        int i = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
//        this.ledSize = i;
//        this.ledBeadRadius = this.w / i * 2.0F;
        invalidate();
    }

    public void setIsCanTouch(boolean paramBoolean) {
        this.isCanTouch = paramBoolean;
    }

    public void setLEDData(String paramString) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setLEDData - ");
        stringBuilder.append(paramString);
        Log.e("888888", stringBuilder.toString());
        String str = paramString;
        if (TextUtils.isEmpty(paramString)) str = initLEDData();
        this.ledSize = PrefUtils.getIntFromPrefs(getContext(), "_pix", 11);
        int j = str.length();
        int i = this.ledSize;
        this.ledColSize = j / i;
        this.ledBeadRadius = this.w / i * 2.0F;
        this.ledData = str;
        invalidate();
    }
}
