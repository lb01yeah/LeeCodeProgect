package com.lee.android.splashscreen;
/**
 * 用于应用的启动动画
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.lee.android.R;
import com.lee.android.base.NetworkInfoUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static String URL = "http://c.hiphotos.baidu.com/baike/s%3D220/sign=86442af5a6c27d1ea1263cc62bd4adaf/42a98226cffc1e17d8f914604890f603738de919.jpg";
    private MyAsyncTask asyncTask;

    private static final int FAILURE = 0; // 失败
    private static final int SUCCESS = 1; // 成功
    private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;

        mImageView = (ImageView) findViewById(R.id.splash_image);
        mProgressBar = (ProgressBar) findViewById(R.id.splash_pb);

        asyncTask = new MyAsyncTask(mProgressBar, mImageView);
        asyncTask.execute(URL); //将图片URL作为参数传入到doInBackground()


/*        new AsyncTask<Void, Void, Integer>() {
            //            后台的操作全部放到doInBackground方法中去,最后返回三种状态,作为后台执行的结果
            @Override
            protected Integer doInBackground(Void... params) {
                int result;

                result = loadingCache();

                return result;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
            }
        }.execute(new Void[]{});
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/

    }

    //内部类在需要更新进度值时，AsyncTask的基本生命周期过程为：
    // onPreExecute() --> doInBackground() --> publishProgress() --> onProgressUpdate() --> onPostExecute()
    public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        private ProgressBar mProgressBar;//
        private ImageView mImageView;

        public MyAsyncTask(ProgressBar pb, ImageView iv) {
            mProgressBar = pb;
            mImageView = iv;
        }


        //在执行后台doInBackground操作之前执行，运行在主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        //核心方法，执行后台下载操作的方法，运行在子线程中；如果我们想向用户展示文件的下载进度情况，这时，我们可以在doInBackground()下载操作中，
        // 调用publishProgress()，将当前进度值传入该方法，而publishProgress()内部会去调用AsyncTask的另一个回调方法：
        @Override
        protected Bitmap doInBackground(String... params) {
            String urlParams = params[0];//拿到execute()传过来的图片url
            Bitmap bitmap = null;
            URLConnection conn = null;
            InputStream is = null;
            try {
                URL url = new URL(urlParams);
                conn = url.openConnection();
                is = conn.getInputStream();

                //这里只是为了演示更新进度的功能，实际的进度值需要在从输入流中读取时逐步获取
                for (int i = 0; i < 100; i++) {
                    publishProgress(i);
                    Thread.sleep(50);//为了看清效果，睡眠一段时间
                }
                //将获取到的输入流转成Bitmap
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);

                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bitmap;

/*            //实际项目中如何获取文件大小作为进度值及更新进度值,在读取输入流到本地的时候，同时读取了输入流的大小的进度值
            int totalSize = conn.getContentLength();//获取文件总大小
            int size = 0;//保存当前下载文件的大小，作为进度值
            int count = 0;
            byte[] buffer = new byte[1024];
            while((count = is.read(buffer)) != -1){
                size += count;//获取已下载的文件大小
                //调用publishProgress更新进度，它内部会回调onProgressUpdate()方法
                publishProgress(size,totalSize);
                Thread.sleep(100);//为了看清效果，睡眠一段时间  */
        }

        //在下载操作doInBackground()中调用publishProgress()时候的回调方法，更新下载进度，运行在主线程中
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }

        //后台操作完成后调用，运行在主线程中
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressBar.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
        }

    }

    private int loadingCache() {
        //判断网络链接是否正常
        if (!NetworkInfoUtil.isNetworkConnected(mContext)) {
            Toast.makeText(SplashActivity.this, "please checkout your internet!", Toast.LENGTH_SHORT).show();
            return OFFLINE;
        }
        Toast.makeText(SplashActivity.this, "Congratulation internet connection success", Toast.LENGTH_SHORT).show();
        return SUCCESS;
    }
}
