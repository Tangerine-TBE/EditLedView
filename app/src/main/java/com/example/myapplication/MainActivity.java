package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Utils.PrefUtils;
import com.example.myapplication.weight.LedView;

public class MainActivity extends AppCompatActivity {
    private int matrix = 11;
    private LedView ledBig;
    private HorizontalScrollView hscrollView;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hscrollView = findViewById(R.id.hscrollView);
        ledBig = findViewById(R.id.led_view);
        this.ledBig.setIsCanTouch(true);
        Integer integer = Integer.valueOf(280);
        this.matrix = PrefUtils.getIntFromPrefs(this, "_pix", 11);
        byte b = 0;
        int i = (int)TypedValue.applyDimension(1, integer.intValue(), getResources().getDisplayMetrics());
        this.ledBig.setOnTouchListener(new View.OnTouchListener() {
            final  MainActivity mainActivity = MainActivity.this;
            public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
                mainActivity.hscrollView.requestDisallowInterceptTouchEvent(param1MotionEvent.getAction() != 1);
                return false;
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            i = this.matrix;
            if (b < i * i) {
                stringBuilder.append("0");
                b++;
                continue;
            }
            this.ledBig.setLEDData(stringBuilder.toString());
            (new Handler()).postDelayed(new Runnable() {
                final MainActivity mainActivity = MainActivity.this ;

                public void run() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("width = ");
                    stringBuilder.append(mainActivity.ledBig.getWidth());
                    stringBuilder.append(", height = ");
                    stringBuilder.append(mainActivity.ledBig.getHeight());
                    stringBuilder.append(" ]");
                    Log.d("PY", stringBuilder.toString());
                }
            },2000L);
            return;
        }
    }
}
