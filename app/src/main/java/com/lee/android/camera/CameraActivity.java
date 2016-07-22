package com.lee.android.camera;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
//import com.mediatek.factorymode.Utils;

import com.lee.android.R;

public class CameraActivity extends Activity implements OnClickListener {
    private Button mBtFailed;
    private Button mBtFinish;
    private Button mBtOk;
    private Button mBtZoomDown;
    private Button mBtZoomUp;
    private Camera mCamera;
    private CameraPreview mPreview;
    SharedPreferences mSp;

    private boolean CheckCameraHardware(Context paramContext) {
        if (paramContext.getPackageManager().hasSystemFeature("android.hardware.camera")) {}
        for (boolean bool = true;; bool = false) {
            Log.d("bobo.li L35——MA——：", "bool: "+bool);
            return bool;
        }

    }

    public Camera getCameraInstance() {
        Object localObject = null;
        try {
            Camera localCamera = Camera.open();
            localObject = localCamera;
        }
        catch (Exception localException) {
        }
        return (Camera)localObject;
    }

    public void onClick(View paramView) {
        SharedPreferences localSharedPreferences = this.mSp;
        if (paramView.getId() == R.id.newcamera_btok) {
//            Utils.SetPreferences(this, localSharedPreferences, R.string.camera_name, "success");
            finish();
        }
        if (paramView.getId() == R.id.newcamera_btfailed) {
//            Utils.SetPreferences(this, localSharedPreferences, R.string.camera_name, "failed");
            finish();
        }
        else if (paramView.getId() == R.id.newcamera_zoomup) {
            setZoomUp();
        }
        else if (paramView.getId() == R.id.newcamera_zoomdown) {
            setZoomDown();
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        getWindow().setFlags(128, 128);
        setContentView(R.layout.activity_camera);
//        paramBundle = getSharedPreferences("FactoryMode", 0);
//        this.mSp = paramBundle;
        this.mSp = getSharedPreferences("FactoryMode", 0);
        Log.d("C300_DBG", "2222222newcamera");
        if (!CheckCameraHardware(this)) {
//            Utils.SetPreferences(this, this.mSp, R.string.camera_name, "failed");
            Toast.makeText(this, "no Camera!", Toast.LENGTH_SHORT).show();
        }
            this.mCamera = getCameraInstance();
            this.mPreview = ((CameraPreview)findViewById(R.id.cp));
            this.mPreview.setCamera(this.mCamera);
            this.mBtFinish = ((Button)findViewById(R.id.newcamera_take));
            this.mBtFinish.setOnClickListener(this);
            this.mBtFinish.setEnabled(false);
            this.mBtFinish.setVisibility(View.GONE);
            this.mBtOk = ((Button)findViewById(R.id.newcamera_btok));
            this.mBtOk.setOnClickListener(this);
            this.mBtFailed = ((Button)findViewById(R.id.newcamera_btfailed));
            this.mBtFailed.setOnClickListener(this);
            this.mBtZoomUp = ((Button)findViewById(R.id.newcamera_zoomup));
            this.mBtZoomUp.setOnClickListener(this);
            this.mBtZoomDown = ((Button)findViewById(R.id.newcamera_zoomdown));
            this.mBtZoomDown.setOnClickListener(this);
            if (!this.mCamera.getParameters().isZoomSupported())
            {
                this.mBtZoomUp.setEnabled(false);
                this.mBtZoomDown.setEnabled(false);
            }
//            return;
//        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    public void setZoomDown() {
        try {
            Parameters localParameters = this.mCamera.getParameters();
            int j = localParameters.getZoom() - 1;
            int i = j;
            if (j < 0) {
                i = 0;
            }
            localParameters.setZoom(i);
            this.mCamera.setParameters(localParameters);
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    public void setZoomUp() {
        try {
            Parameters localParameters = this.mCamera.getParameters();
            int j = localParameters.getZoom() + 1;
            int i = j;
            if (j > localParameters.getMaxZoom()) {
                i = localParameters.getMaxZoom();
            }
            localParameters.setZoom(i);
            this.mCamera.setParameters(localParameters);
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }
}
