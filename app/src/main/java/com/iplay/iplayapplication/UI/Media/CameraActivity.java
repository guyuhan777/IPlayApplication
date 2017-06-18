package com.iplay.iplayapplication.UI.Media;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.assistance.ImgUtils;
import com.iplay.iplayapplication.customComponent.doubleWayProgressBar.BothWayProgressBar;
import com.iplay.iplayapplication.mActivity.MyActivity;
import com.iplay.iplayapplication.util.Msg;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2017/6/7.
 */

public class CameraActivity extends MyActivity implements View.OnClickListener,View.OnTouchListener,BothWayProgressBar.OnProgressEndListener{
    private static final int LISTENER_START = 30;

    private SurfaceView sv_main_surface;
    private Camera camera;
    private TextView camera_back;
    private CircleImageView camera_button;

    private TextView camera_photo_nav;
    private TextView camera_video_nav;
    private TextView camera_type_label;

    private static final int TAKE_PHOTO_TYPE = 0;
    private static final int TAKE_VIDEO_TYPE = 1;

    private ImageView camera_face_change_button;

    private int currentType = TAKE_PHOTO_TYPE;

    private int videoWidth, videoHeight;

    private PhotoCallBack photoCallBack = new PhotoCallBack();

    private BothWayProgressBar mProgressBar;

    private Thread mProgressThread;

    private MediaRecorder mMediaRecorder;

    private boolean isRecording;

    private int mProgress;

    private boolean isCancel;

    private MyHandler mHandler;
    private TextView camera_move_hint;
    private boolean isRunning;

    private File mTargetFile;

    private static final String TAG = "CameraActivity";

    private int cameraPosition = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        camera_button = (CircleImageView) findViewById(R.id.take_photo_button);
        Glide.with(this).load(R.drawable.play90_1).into(camera_button);
        camera_button.setOnClickListener(this);
        camera_back = (TextView) findViewById(R.id.camera_back);
        camera_back.setOnClickListener(this);
        sv_main_surface = (SurfaceView) findViewById(R.id.sv_main_surface);
        sv_main_surface.getHolder().addCallback(photoCallBack);
        camera_photo_nav = (TextView) findViewById(R.id.camera_photo_nav);
        camera_photo_nav.setOnClickListener(this);
        camera_video_nav = (TextView) findViewById(R.id.camera_video_nav);
        camera_video_nav.setOnClickListener(this);
        camera_type_label = (TextView) findViewById(R.id.camera_type_label);

        camera_face_change_button = (ImageView) findViewById(R.id.camera_facce_change_button);
        camera_face_change_button.setOnClickListener(this);

        mProgressBar = (BothWayProgressBar) findViewById(R.id.camera_progress);
        mProgressBar.setOnProgressEndListener(this);
        mHandler = new MyHandler(this);
        mMediaRecorder = new MediaRecorder();

        camera_move_hint = (TextView) findViewById(R.id.camera_move_hint);

        videoWidth = 640;
        videoHeight = 480;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_back:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
                break;
            case R.id.take_photo_button:
                if(currentType == TAKE_PHOTO_TYPE) {
                    camera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            camera.takePicture(null, null, mPicture);
                        }
                    });
                }else if(currentType == TAKE_VIDEO_TYPE){

                }
                break;
            case R.id.camera_facce_change_button:
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();
                for(int i = 0;i<cameraCount;i++){
                    Camera.getCameraInfo(i,cameraInfo);
                    if(cameraPosition == 1){
                        if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                            camera.stopPreview();
                            camera.release();
                            camera = null;
                            camera = Camera.open(i);
                            cameraParaInit();
                            try{
                                camera.setPreviewDisplay(sv_main_surface.getHolder());
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            camera.startPreview();
                            cameraPosition = 0;
                            break;
                        }
                    }else{
                        if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                            camera.stopPreview();
                            camera.release();
                            camera = null;
                            camera = Camera.open(i);
                            cameraParaInit();
                            try{
                                camera.setPreviewDisplay(sv_main_surface.getHolder());
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            camera.startPreview();
                            cameraPosition = 1;
                            break;
                        }
                    }
                }
            case R.id.camera_photo_nav:
                currentType = TAKE_PHOTO_TYPE;
                camera_type_label.setText("照片");
                camera_photo_nav.setTextColor(getResources().getColor(R.color.blue_hint));
                camera_video_nav.setTextColor(getResources().getColor(R.color.grey));
                camera_button.setOnTouchListener(null);
                camera_button.setOnClickListener(this);
                break;
            case R.id.camera_video_nav:
                currentType = TAKE_VIDEO_TYPE;
                camera_type_label.setText("视频");
                camera_video_nav.setTextColor(getResources().getColor(R.color.blue_hint));
                camera_photo_nav.setTextColor(getResources().getColor(R.color.grey));
                camera_button.setOnClickListener(null);
                camera_button.setOnTouchListener(this);
                break;

        }
    }



    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //Bitmap bm = BitmapFactory.decodeByteArray(data,0,data.length);
            //PhotoEditActivity.start(CameraActivity.this);
            Msg<String> msg = ImgUtils.saveImageToGallery(getApplicationContext(),data);
            if(msg == null){
                Toast.makeText(CameraActivity.this,"Unknown Error",Toast.LENGTH_SHORT).show();
            }else{
                if(msg.MSG_TYPE == Msg.MSG_TYPE_FAILURE){
                    Toast.makeText(CameraActivity.this,"Photo Save Failure",Toast.LENGTH_SHORT).show();
                }else if(msg.MSG_TYPE == Msg.MSG_TYPE_SUCCESS){
                    Toast.makeText(CameraActivity.this,"Photo Save Success",Toast.LENGTH_SHORT).show();
                    PhotoEditActivity.start(CameraActivity.this,msg.getMsg());
                }
            }
            /*if(ImgUtils.saveImageToGallery(getApplicationContext(),data)){
                Log.d("photoTake","SUCCESS");
            }else{
                Log.d("photoTake","FAILURE");
            }*/
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        int action = event.getAction();
        float ey = event.getY();
        float ex = event.getX();
        //只监听中间的按钮处
        int vW = v.getWidth();
        int left = LISTENER_START;
        int right = vW - LISTENER_START;

        float downY = 0;

        switch (v.getId()){
            case R.id.take_photo_button:
                Log.d("video","ex: "+ex+" left: "+left+" right: "+ right);
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        if(ex > left && ex <right){
                            Log.d(TAG,"ACTION_DOWN");
                            mProgressBar.setCancel(false);
                            //显示上滑取消
                            camera_move_hint.setVisibility(View.VISIBLE);
                            camera_move_hint.setText("↑ 上滑取消");
                            //记录按下的Y坐标
                            downY = ey;

                            mProgressBar.setVisibility(View.VISIBLE);
                            //开始录制
                            Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
                            startRecord();

                            mProgressThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        mProgress = 0;
                                        isRunning = true;
                                        while (isRunning) {
                                            mProgress++;
                                            mHandler.obtainMessage(0).sendToTarget();
                                            Thread.sleep(20);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            mProgressThread.start();
                            ret = true;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (ex > left && ex < right) {
                            Log.d(TAG,"ACTION_UP");
                            camera_move_hint.setVisibility(View.INVISIBLE);
                            mProgressBar.setVisibility(View.INVISIBLE);
                            //判断是否为录制结束, 或者为成功录制(时间过短)
                            if (!isCancel) {
                                if (mProgress < 50) {
                                    //时间太短不保存
                                    stopRecordUnSave();
                                    Toast.makeText(this, "时间太短", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                //停止录制
                                stopRecordSave();
                            } else {
                                //现在是取消状态,不保存
                                stopRecordUnSave();
                                isCancel = false;
                                Toast.makeText(this, "取消录制", Toast.LENGTH_SHORT).show();
                                mProgressBar.setCancel(false);
                            }

                            ret = false;
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (ex > left && ex < right) {
                            float currentY = event.getY();
                            if (downY - currentY > 5) {
                                isCancel = true;
                                mProgressBar.setCancel(true);
                            }
                        }
                        break;
                }
                break;
        }

        return ret;
    }

    @Override
    public void onProgressEndListener() {
        stopRecordSave();
    }


    private void cameraParaInit(){
        if(camera!=null){
            Camera.Parameters parameters=camera.getParameters();
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes != null) {
                for (String mode : focusModes) {
                    mode.contains("continuous-video");
                    parameters.setFocusMode("continuous-video");
                }
            }
            camera.setParameters(parameters);
            camera.setDisplayOrientation(90);
        }
    }

    private class PhotoCallBack implements SurfaceHolder.Callback{
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if(camera == null){
                camera = Camera.open();
            }
            cameraParaInit();
            try{
                camera.setPreviewDisplay(sv_main_surface.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //开启预览效果
            camera.startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                Log.d("surface", "surfaceDestroyed: ");
                //停止预览并释放摄像头资源
                camera.stopPreview();
                camera.release();
                camera = null;
            }
            if (mMediaRecorder != null) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }
    }

    private void startRecord(){
        if(mMediaRecorder!=null){
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.d(TAG,"Storage_not_exist");
                return;
            }
            try {
                camera.unlock();
                mMediaRecorder.setCamera(camera);
                //从相机采集视频
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 从麦克采集音频信息
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                // TODO: 2016/10/20  设置视频格式
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                //每秒的帧数
                mMediaRecorder.setVideoFrameRate(24);
                mMediaRecorder.setVideoSize(videoWidth, videoHeight);
                //编码格式
                mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                // 设置帧频率，然后就清晰了
                mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024 * 100);
                // TODO: 2016/10/20 临时写个文件地址, 稍候该!!!
                File targetDir = Environment.
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                mTargetFile = new File(targetDir,
                        SystemClock.currentThreadTimeMillis() + ".mp4");
                mMediaRecorder.setOutputFile(mTargetFile.getAbsolutePath());
                mMediaRecorder.setPreviewDisplay(sv_main_surface.getHolder().getSurface());
                //解决录制视频, 播放器横向问题
                mMediaRecorder.setOrientationHint(90);
                Log.d(TAG,"here");
                mMediaRecorder.prepare();
                //正式录制
                mMediaRecorder.start();
                isRecording = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void stopRecordSave() {
        Log.d(TAG,"stopRecordSave");
        if (isRecording) {
            Log.d(TAG, "isRunning");
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            Toast.makeText(this, "视频已经放至" + mTargetFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }
        isRunning = false;
    }

    private void stopRecordUnSave() {
        Log.d(TAG,"stopRecordUnSave");
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            if (mTargetFile.exists()) {
                //不保存直接删掉
                mTargetFile.delete();
            }
        }
        isRunning = false;
    }

    private static class MyHandler extends Handler {
        private WeakReference<CameraActivity> mReference;
        private CameraActivity mActivity;

        public MyHandler(CameraActivity activity) {
            mReference = new WeakReference<>(activity);
            mActivity = mReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mActivity.mProgressBar.setProgress(mActivity.mProgress);
                    Log.d(TAG,"progress : "+mActivity.mProgress+"");
                    break;
            }
        }
    }
}
