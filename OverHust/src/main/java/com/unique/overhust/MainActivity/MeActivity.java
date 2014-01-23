package com.unique.overhust.MainActivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;


import com.devspark.appmsg.AppMsg;
import com.unique.overhust.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class MeActivity extends SwipeBackActivity {

    private static final int VIBRATE_DURATION = 20;
    //控制滑动返回的边缘大小
    private static final int EDGE_SIZE = 100;
    private ImageView meBackView;
    private SwipeBackLayout mSwipeBackLayout;

    private ImageView navRecView,seaRecView,loveRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        findViews();
        setOnClick();


        //左侧滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(EDGE_SIZE);
    }

    public void findViews() {
        meBackView = (ImageView) findViewById(R.id.meback);
        navRecView=(ImageView)findViewById(R.id.navitationRecord);
        seaRecView=(ImageView)findViewById(R.id.searchRecord);
        loveRecView=(ImageView)findViewById(R.id.loveRecord);
    }

    public void setOnClick(){
        meBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        navRecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppMsg();
            }
        });
        seaRecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppMsg();
            }
        });
        loveRecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppMsg();
            }
        });
    }

    public void showAppMsg(){
        AppMsg appMsg = AppMsg.makeText(this, "敬请期待", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.overhust),R.layout.appmsg_green);
        appMsg.setLayoutGravity(Gravity.TOP);
        appMsg.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.me, menu);
        return true;
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
            View rootView = inflater.inflate(R.layout.fragment_me, container, false);
            return rootView;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AppMsg appMsg = AppMsg.makeText(this, "左滑边缘滑动返回", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.overhust),R.layout.appmsg_green);
//        appMsg.setLayoutGravity(Gravity.BOTTOM);
//        appMsg.show();
//        return false;
//    }

//    @Override
//    public boolean onKeyDown(int keyCode,KeyEvent event){
//        if(keyCode==KeyEvent.KEYCODE_BACK){
////            Intent meIntent=new Intent(this,MainActivity.class);
////            startActivity(meIntent);
//            finish();
//        }
//        return false;
//    }

}
