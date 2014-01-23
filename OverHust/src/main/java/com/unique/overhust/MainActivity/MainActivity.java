package com.unique.overhust.MainActivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.devspark.appmsg.AppMsg;
import com.unique.overhust.FirstInto.GuideAdapter;
import com.unique.overhust.FirstInto.Guides;
import com.unique.overhust.MapUtils.OverHustLocation;
import com.unique.overhust.R;
import com.unique.overhust.fragment.DrawerFragment;
import com.unique.overhust.fragment.InitFragment;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private FrameLayout drawerFrameLyout, contentFrameLyout;
    private DrawerFragment mDrawerFragment;
    private OverHustLocation mOverHustLocation;
    private InitFragment mInitFragment;
    public static ImageView footImageView;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        findViews();
        initDrawer();
        initFirstInto();
        mDrawerLayout.openDrawer(GravityCompat.START);
        mOverHustLocation = new OverHustLocation(this);
        mOverHustLocation.getLocation();


    }

    public void initFirstInto() {
        mInitFragment = new InitFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, mInitFragment);
        transaction.commit();
    }

    public void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFrameLyout = (FrameLayout) findViewById(R.id.drawer_frame);
        contentFrameLyout = (FrameLayout) findViewById(R.id.content_frame);
        footImageView = (ImageView) findViewById(R.id.foot);
    }

    //添加左侧的fragment
    public void initDrawer() {
        mDrawerFragment = new DrawerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   //打开fragment的事物
        transaction.add(R.id.drawer_frame, mDrawerFragment);
        transaction.commit();
    }

    // 关掉左侧抽屉
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    //打开左侧抽屉
    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            openDrawer();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();      //连按两次退出应用
        }
        return false;
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
            AppMsg appMsg = AppMsg.makeText(this, "再次按返回键退出应用", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.overhust),R.layout.appmsg_green);
            appMsg.setLayoutGravity(Gravity.BOTTOM);
            appMsg.show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
