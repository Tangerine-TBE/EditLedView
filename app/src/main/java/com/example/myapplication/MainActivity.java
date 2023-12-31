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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.Utils.BitmapUtils;
import com.example.myapplication.Utils.GlideEngine;
import com.example.myapplication.Utils.ImageCropEngine;
import com.example.myapplication.weight.LedView;
import com.jakewharton.rxbinding4.view.RxView;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LedView ledBig;
    private ImageView mOriginView;
    private ImageView mCustomView;
    private AppCompatSeekBar mThresholdController;
    private Bitmap mOriginBitmap;
    private Bitmap mEffectBitmap;

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
                    mEffectBitmap = BitmapUtils.convertToBlackAndWhite(mOriginBitmap, i);
                    mCustomView.setImageBitmap(mEffectBitmap);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(mEffectBitmap,11, 11, true);
                    ledBig.setLEDData( BitmapUtils.getLedData(scaledBitmap));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        findViewById(R.id.btn_canTouch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(MainActivity.this).openGallery(SelectMimeType.ofImage()).setSandboxFileEngine(new UriToFileTransformEngine() {
                    @Override
                    public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
                        if (call != null) {
                            String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType);
                            call.onCallback(srcPath, sandboxPath);
                        }
                    }
                }).setCropEngine(new ImageCropEngine(1,1)).setImageEngine(GlideEngine.createGlideEngine()).setMaxSelectNum(1).forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        LocalMedia localMedia = result.get(0);
                        if (localMedia != null) {
                            String filePath = localMedia.getSandboxPath();
                            Bitmap originBitmap = BitmapFactory.decodeFile(filePath);
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap, (int) (originBitmap.getWidth() * 0.25), (int) (originBitmap.getHeight() * 0.25), true);
                            mOriginView.setImageBitmap(scaledBitmap);
                            mOriginBitmap = scaledBitmap;
                            mCustomView.setImageBitmap(BitmapUtils.convertToBlackAndWhite(scaledBitmap, 0));
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
    }
}
