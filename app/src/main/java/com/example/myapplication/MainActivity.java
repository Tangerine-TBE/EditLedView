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
        ledBig = findViewById(R.id.led_view);
        findViewById(R.id.btn_reverser).setOnClickListener(view -> ledBig.reverse());
        findViewById(R.id.btn_clear).setOnClickListener(view -> ledBig.clear());
        this.ledBig.setIsCanTouch(true);
        this.matrix = PrefUtils.getIntFromPrefs(this, "_pix", 11);
        byte b = 0;
        int i;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            i = this.matrix;
            if (b < i * i) {
                stringBuilder.append("0");
                b++;
                continue;
            }
            this.ledBig.setLEDData(stringBuilder.toString());
            return;
        }
    }
}
