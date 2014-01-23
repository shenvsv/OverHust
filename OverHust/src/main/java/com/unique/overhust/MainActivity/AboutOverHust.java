package com.unique.overhust.MainActivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.aphidmobile.flip.FlipViewController;
import com.devspark.appmsg.AppMsg;
import com.unique.overhust.AboutOverHust.OverHustAdapter;
import com.unique.overhust.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AboutOverHust extends SwipeBackActivity {
    //控制滑动返回的边缘大小
    private static final int EDGE_SIZE = 100;
    private SwipeBackLayout mSwipeBackLayout;

    private FlipViewController flipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_over_hust);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        //左侧滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(EDGE_SIZE);

        flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
        flipView.setAdapter(new OverHustAdapter(this));
        setContentView(flipView);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AppMsg appMsg = AppMsg.makeText(this, "左侧边缘滑动返回", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.overhust),R.layout.appmsg_green);
//        appMsg.setLayoutGravity(Gravity.BOTTOM);
//        appMsg.show();
//        return false;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_over_hust, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_about_over_hust, container, false);
            return rootView;
        }
    }

}
