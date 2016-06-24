package com.lee.android.splashscreen;
/**
 * 用于应用的启动动画
 */

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lee.android.R;

public class SplashActivity extends AppCompatActivity {

    private static final int FAILURE = 0; // 失败
    private static final int SUCCESS = 1; // 成功
    private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new AsyncTask<Void,Void,Integer>(){
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
    }

    private int loadingCache(){
        if(BaseApplication.mNetWorkState == NetworkUtils.NETWORN_NONE){
            return OFFLINE;
        }
        return SUCCESS;
    }
}
