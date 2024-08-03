package com.packtpub.renderscript;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class RenderScriptActivity extends Activity
implements OnClickListener {
    static {
        System.loadLibrary("RSSupport");
        System.loadLibrary("renderscript");
    }
    
    private Button mThresholdButton, mBlurButton, mCombineButton;
    private SeekBar mBlurRadiusBar, mThresholdBar;
    private ImageView mSrcImageView, mDstImageView;
    private Bitmap mSrcImage, mDstImage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renderscript);
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mSrcImage = BitmapFactory.decodeResource(getResources(),
                                        R.drawable.picture, options);
        mDstImage = Bitmap.createBitmap(mSrcImage.getWidth(),
                                        mSrcImage.getHeight(),
                                        Bitmap.Config.ARGB_8888);
        
        mBlurButton = (Button) findViewById(R.id.blurButton);
        mBlurButton.setOnClickListener(this);
        mThresholdButton = (Button) findViewById(R.id.thresholdButton);
        mThresholdButton.setOnClickListener(this);
        mCombineButton = (Button)findViewById(R.id.combineButton);
        mCombineButton.setOnClickListener(this);

        mBlurRadiusBar = (SeekBar) findViewById(R.id.radiusBar);
        mThresholdBar = (SeekBar) findViewById(R.id.thresholdBar);

        mSrcImageView = (ImageView) findViewById(R.id.srcImageView);
        mDstImageView = (ImageView) findViewById(R.id.dstImageView);
        mSrcImageView.setImageBitmap(mSrcImage);
        mDstImageView.setImageBitmap(mDstImage);
    }

    @Override
    public void onClick(View pView) {
        float progressRadius = (float) mBlurRadiusBar.getProgress();
        float radius = Math.max(progressRadius * 0.1f, 0.1f);
        float threshold = ((float) mThresholdBar.getProgress())
                        / 100.0f;

        switch(pView.getId()) {
        case R.id.blurButton:
            blur(getCacheDir().toString(), mSrcImage, mDstImage,
                 radius);
            break;

        case R.id.thresholdButton:
            threshold(getCacheDir().toString(), mSrcImage, mDstImage,
                      threshold);
            break;

        case R.id.combineButton:
            combine(getCacheDir().toString(), mSrcImage, mDstImage,
                    radius, threshold);
            break;
        }
        mDstImageView.invalidate();
    }

    private native void blur(String pCacheDir, Bitmap pSrcImage,
                             Bitmap pDstImage, float pRadius);

    private native void threshold(String pCacheDir, Bitmap pSrcImage,
                                  Bitmap pDstImage, float pThreshold);

    private native void combine(String pCacheDir,
                                Bitmap pSrcImage, Bitmap pDstImage,
                                float pRadius, float pThreshold);
}