package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Utils.PrefUtils;
import com.example.myapplication.weight.LedView;

public class MainActivity extends AppCompatActivity {
    private LedView ledBig;
    private boolean canTouch;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ledBig = findViewById(R.id.led_view);
        findViewById(R.id.btn_reverser).setOnClickListener(view -> ledBig.reverse());
        findViewById(R.id.btn_clear).setOnClickListener(view -> ledBig.clear());
        findViewById(R.id.btn_canTouch).setOnClickListener(view->{
                //Todo 选择一张图片
        });
        findViewById(R.id.btn_save).setOnClickListener(view -> {
                //Todo 保存一张图片
        });
        this.ledBig.setIsCanTouch(true);
        int matrix = 11;
        byte b = 0;
        int i;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            i = matrix;
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
