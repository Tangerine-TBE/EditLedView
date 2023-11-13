package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.example.myapplication.Utils.BitmapUtils;
import com.example.myapplication.Utils.GlideEngine;
import com.example.myapplication.weight.LedView;
import com.jakewharton.rxbinding4.view.RxView;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LedView ledBig;
    private ImageView mOriginView;
    private ImageView mCustomView;
    private AppCompatSeekBar mThresholdController;
    private Bitmap mOriginBitmap;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility", "CheckResult"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ledBig = findViewById(R.id.led_view);
        mOriginView = findViewById(R.id.iv_origin);
        mCustomView = findViewById(R.id.iv_effect);
        mThresholdController = findViewById(R.id.sb_controller);
        mThresholdController.setMax(255);
        mThresholdController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mOriginBitmap != null) {
                    Bitmap mEffectBitmap = BitmapUtils.convertToBlackAndWhite(mOriginBitmap, i);
                    mCustomView.setImageBitmap(mEffectBitmap);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        RxPermissions rxPermissions = new RxPermissions(this);
        RxView.clicks(findViewById(R.id.btn_canTouch)).compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE))
                        .subscribe(granted ->{
                            if(granted){
                                PictureSelector.create(this).openGallery(SelectMimeType.ofImage()).setSandboxFileEngine(new UriToFileTransformEngine() {
                                    @Override
                                    public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
                                        if (call != null) {
                                            String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType);
                                            call.onCallback(srcPath,sandboxPath);
                                        }
                                    }
                                }).setImageEngine(GlideEngine.createGlideEngine()).setMaxSelectNum(1).forResult(new OnResultCallbackListener<LocalMedia>() {
                                    @Override
                                    public void onResult(ArrayList<LocalMedia> result) {
                                        LocalMedia localMedia = result.get(0);
                                        if (localMedia != null) {
                                            String filePath = localMedia.getSandboxPath();
                                            Bitmap originBitmap = BitmapFactory.decodeFile(filePath);
                                            mOriginView.setImageBitmap(originBitmap);
                                            mOriginBitmap = originBitmap;
                                            mCustomView.setImageBitmap(BitmapUtils.convertToBlackAndWhite(originBitmap, 0));
                                        }
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                            }
                        });
        findViewById(R.id.btn_reverser).setOnClickListener(view -> ledBig.reverse());
        findViewById(R.id.btn_clear).setOnClickListener(view -> ledBig.clear());
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
