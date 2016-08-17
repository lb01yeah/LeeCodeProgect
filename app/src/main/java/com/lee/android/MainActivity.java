package com.lee.android;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AtomicFile;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.android.base.ToolBarActivity;
import com.lee.android.camera.CameraActivity;
import com.lee.android.camera2.Camera2Activity;
import com.lee.android.listView.ListViewActivity;
import com.lee.android.nohttps.activity.StartActivity;
import com.lee.android.readxml.ReadXmlFile;
import com.lee.android.readxml.ReadXmlResources;
import com.lee.android.recycler.RecyclerActivity;
import com.lee.android.services.MusicService;


public class MainActivity extends ToolBarActivity implements View.OnClickListener {

    private static Context mContext;

    public static final String FRAG_TAG_TIME_PICKER = "time_dialog";

    private Button mLoginButton;
    private Button mReadXMLButton01;
    private Button mReadXMLButton02;
    private TextView mXmlValue;
    private Button mNoHttpButton;
    private Button mCameraButton;
    private Button mCameraButton2;
    private Button mServiceStart;
    private Button mServiceStop;
    private Button mRecyclerView;
    private Button mListView;

    //for pull xml
    // Persistent storage for notification policy
    static final String TAG = "NotificationService";
    static final boolean DBG = true;
    private AtomicFile mPolicyFile;
    private static final int DB_VERSION = 1;

    private static final String TAG_NOTIFICATION_POLICY = "notification-permision-default";
    private static final String ATTR_VERSION = "version";

    // Obsolete:  converted if present, but not resaved to disk.
    private static final String TAG_BLOCKED_PKGS = "blocked-packages";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_RANKING = "ranking";
    private static final String ATT_NAME = "name";
    private static final String ATT_PRIORITY = "priority";
    private static final String ATT_PEEKABLE = "peekable";

    private static final String ATT_KEYGUARD = "keyguard";
    private static final String ATT_STATUSBAR = "statusbar";
    private static final String ATT_SUBSCRIPT = "subscript";
    private static final String ATT_BANNER = "banner";

    private static final String ATTR_NAME = "name";


    private static final String ATT_VERSION = "version";
    private static final int XML_VERSION = 1;

    private static final String ATT_UID = "uid";
    private static final String ATT_VISIBILITY = "visibility";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        initView();
    }
    public static Context getContext(){
        return mContext;
    }
    //Begin:initView()
    public void initView(){

        mLoginButton = (Button) findViewById(R.id.main_login_btn);
        mReadXMLButton01 = (Button) findViewById(R.id.main_readxml_btn01);//第一种读取方式
        mReadXMLButton02 = (Button) findViewById(R.id.main_readxml_btn02);//第二种读取方式
        mNoHttpButton = (Button) findViewById(R.id.main_nohttp_btn); //nohttp 使用方式
        mCameraButton = (Button) findViewById(R.id.main_camera_btn); //camera Test
        mCameraButton2 = (Button) findViewById(R.id.main_camera2_btn); //camera2 Test
        mServiceStart = (Button) findViewById(R.id.main_service_start_btn); //start service
        mServiceStop = (Button) findViewById(R.id.main_service_stop_btn); //stop service
        mRecyclerView = (Button) findViewById(R.id.main_recyclerView_btn); //RecyclerView
        mListView = (Button) findViewById(R.id.main_listview_btn);

        mRecyclerView.setOnClickListener(this);
        mListView.setOnClickListener(this);
        mServiceStop.setOnClickListener(this);
        mServiceStart.setOnClickListener(this);

        mXmlValue = (TextView) findViewById(R.id.xml_read_value);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mReadXMLButton01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
/*                Intent intent = new Intent(this,AnimationActivity.class);
                startActivity(intent);*/

//                final File systemDir = new File(Environment.getDataDirectory(), "system");
//                final File systemDir = new File(Environment.getExternalStorageDirectory()+ "//Download"+"notification_policy_default.xml");
//                Log.d("bobo.li1","systemDir:"+systemDir.toString());
//                mPolicyFile = new AtomicFile(new File(systemDir, "notification_policy_default.xml"));
                ReadXmlResources rxResources = new ReadXmlResources(getContext());
/*                try {
//                   rxResources..loadPolicyFile();//读数据
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/


//                handleSavePolicyFile();//写数据
            }
        });

        mReadXMLButton02.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ReadXmlFile rxFile = new ReadXmlFile(getContext());
            }
        });

        mNoHttpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StartActivity.class);
                startActivity(intent);
            }
        });

        mCameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CameraActivity.class);
                startActivity(intent);
            }
        });

        mCameraButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Camera2Activity.class);
                startActivity(intent);
            }
        });
//        mRecyclerView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, RecyclerActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onClick(View v) {
//      此点击事件使用之前应当先注册
        switch(v.getId()){
            case R.id.main_service_start_btn:
                Intent intent1 = new Intent(MainActivity.this,MusicService.class);
                startService(intent1);
                break;
            case R.id.main_service_stop_btn:
                Intent intent2 = new Intent(MainActivity.this,MusicService.class);
                stopService(intent2);
                break;
            case R.id.main_recyclerView_btn:
                Intent intent3 = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(intent3);
                break;
            case R.id.main_listview_btn:
                Intent intent4 = new Intent(mContext, ListViewActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }

    //End:initView


}
