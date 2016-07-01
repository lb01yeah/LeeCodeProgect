package com.lee.android.base;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lee.android.R;

public abstract class ToolBarActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_layout);
        if (layoutResID > 0) {
            LayoutInflater.from(this).inflate(layoutResID, (ViewGroup) findViewById(R.id.root_layout));
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar); //use toolbar instead of actionBar
        mToolbar.setTitle(R.string.app_name); //main title
        mToolbar.setSubtitle(R.string.title_activity_main); //sub title
//        mToolbar.setNavigationIcon(R.mipmap.tb_navigation_icon);
        mToolbar.setLogo(R.mipmap.tb_logo_icon);

        mToolbar.setOnMenuItemClickListener(onMenuItemClick);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//在toolsbar增加侧边栏返回键控制开关，收回按钮
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar, R.string.drawer_open,
                R.string.drawer_close); //控制侧滑点击可以展现出来
        mDrawerToggle.syncState();//侧滑控制开关打开状态按钮
        mDrawerLayout.setDrawerListener(mDrawerToggle);//监听点击打开、收回按钮效果

        initView();
    }



    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(ToolBarActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void initView(){



    }


}
