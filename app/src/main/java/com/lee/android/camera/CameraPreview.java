package com.lee.android.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.lee.android.R;

public class CameraPreview extends SurfaceView implements Callback {
    private Camera mCamera;
    private Parameters mCameraParam;
    protected Context mContext;
    private SurfaceHolder mHolder;
    private String mISO = "AUTO";

    public CameraPreview(Context paramContext) {
        super(paramContext);
        this.mContext = paramContext;
        setFocusable(true);
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public CameraPreview(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.mContext = paramContext;
        Log.d("C300_DBG", "1111111 CameraPreview constructed");
        setFocusable(true);
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    private Size getOptimalPreviewSize(List<Size> paramList, double paramDouble) {
        Log.v("bobo.li_50_cp", "11111getOptimalPreviewSize");
        Object localObject2;
        Log.v("bobo.li_52_cp", "11111getOptimalPreviewSize"+paramList);
        //[android.hardware.Camera$Size@57dac0, android.hardware.Camera$Size@9fbc30, android.hardware.Camera$Size@afb580,
        // android.hardware.Camera$Size@ef9a20, android.hardware.Camera$Size@ef9a50, android.hardware.Camera$Size@13f7860,
        // android.hardware.Camera$Size@1676730, android.hardware.Camera$Size@18f5600, android.hardware.Camera$Size@18f5678,
        // android.hardware.Camera$Size@1af4840, android.hardware.Camera$Size@1df33dc, android.hardware.Camera$Size@27eefd0,
        // android.hardware.Camera$Size@3be67b8, android.hardware.Camera$Size@3be67c0, android.hardware.Camera$Size@3469bf0]

        if (paramList == null) {
            Log.v("bobo.li_53_cp", "11111getOptimalPreviewSize"+paramList);
            localObject2 = null;
        }
        int j;
        Object localObject1;
//        do
//        {
            localObject2 = null;
            double d = Double.MAX_VALUE;
            int i = 0;
            if (0 <= 0) {
                i = Math.min(getHeight(), getWidth());
            }
            j = i;
            if (i <= 0) {
                j = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getHeight();
            }
            Iterator localIterator = paramList.iterator();
            while (localIterator.hasNext()) {
                localObject1 = (Size) localIterator.next();
                if ((j <= ((Size) localObject1).height) && (Math.abs(((Size) localObject1).width / ((Size) localObject1).height - paramDouble) <= 0.05D) && (Math.abs(((Size) localObject1).height - j) < d)) {
                    localObject2 = localObject1;
                    d = Math.abs(((Size) localObject1).height - j);
                }
            }
            localObject1 = localObject2;
            if (localObject2 == null) {
                localIterator = paramList.iterator();
                for (; ; ) {
                    localObject1 = localObject2;
                    if (!localIterator.hasNext()) {
                        break;
                    }
                    localObject1 = (Size) localIterator.next();
                    if ((Math.abs(((Size) localObject1).width / ((Size) localObject1).height - paramDouble) <= 0.05D) && (Math.abs(((Size) localObject1).height - j) < d)) {
                        localObject2 = localObject1;
                        d = Math.abs(((Size) localObject1).height - j);
                    }
                }
            }
            localObject2 = localObject1;

//        } while (localObject1 != null);
        paramDouble = Double.MAX_VALUE;
        Iterator localIterator2 = paramList.iterator();
        for (;;)
        {
            localObject2 = localObject1;
            if (!localIterator2.hasNext()) {
                break;
            }
//            paramList = (Camera.Size)localIterator.next();
            localObject2 = (Size)localIterator2.next();
//            if (Math.abs(paramList.height - j) < paramDouble)
            if (Math.abs(((Size)localObject2).height - j) < paramDouble)
            {
//                localObject1 = paramList;
                localObject1 = localObject2;
                paramDouble = Math.abs(((Size)localObject2).height - j);
            }
        }
        return (Size)localObject2;
    }

    private void startPreview() {
        Log.v("bobo.li_L123_cp", "11111 startPreview");
        Log.d("bobo.li_L124_cp:", "this.mCamera: "+this.mCamera);
        if (this.mCamera != null) {
            this.mCameraParam = this.mCamera.getParameters();
            Size localSize = this.mCameraParam.getPictureSize();
            Log.v("bobo.li_L129_cp", "11111 startPreview");
            List localList = this.mCameraParam.getSupportedPreviewSizes();

            // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
            if (localList.size() >= 1) {
                Iterator<Size> itor = localList.iterator();
                while (itor.hasNext()) {
                    Size cur = itor.next();
                    System.out.println("bobo.li-size==" + cur.width + " " + cur.height);
                    Log.v("bobo.li", "size:"+ cur.width+":h:"+cur.height);
                }
            }


            Log.v("bobo.li_L130_cp", "11111 startPreview_localList:"+localList);
            Object localObject2 = null;
            Object localObject1 = localObject2;
            if (localSize != null)
            {
                localObject1 = localObject2;
                if (localSize.height != 0) {
                    localObject1 = getOptimalPreviewSize(localList, localSize.width / localSize.height);
                }
            }
            if (localObject1 != null) {
                this.mCameraParam.setPreviewSize(((Size)localObject1).width, ((Size)localObject1).height);
            }
            this.mCameraParam.setFlashMode("torch");
            this.mCameraParam.set("fps-mode", 0);
            this.mCameraParam.set("cam-mode", 0);
            this.mCamera.setParameters(this.mCameraParam);
            Log.v("bobo.li_147_cp", "11111 setParameters");
        }
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("bobo.li_154_cp", "11111 setParameters");
        this.mCamera.startPreview();
        Log.v("bobo.li_156_cp", "11111 setParameters");
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if ((paramMotionEvent.getAction() == 0) && (this.mCamera != null)) {
            this.mCamera.autoFocus(null);
        }
        return true;
    }

    public void setCamera(Camera paramCamera) {
        Log.d("bobo.li_L162:", "surfaceCreate: ");
        this.mCamera = paramCamera;
        this.mCamera = paramCamera;
        this.mCameraParam = this.mCamera.getParameters();
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
        try {
            Log.d("bobo.li_L177:", "surfaceCreate: ");
            this.mCamera.setPreviewDisplay(paramSurfaceHolder);
            startPreview();
        }
        catch (IOException ex) {
            Log.d("C300_DBG", "Error is " + ex.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        if (this.mHolder.getSurface() == null) {}
            try {
                this.mCamera.stopPreview();
                try {
                    this.mCamera.setPreviewDisplay(this.mHolder);
                    this.mCamera.startPreview();
                }
                catch (Exception ex) {
                    Log.d("C300_DBG", "Error is " + ex.getMessage());
                }
            }
            catch (Exception ex) {
                Log.d("C301_DBG", "Error is " + ex.getMessage());
            }
    }
}
