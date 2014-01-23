package com.unique.overhust.MainActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.devspark.appmsg.AppMsg;
import com.unique.overhust.CommonUtils.IsNetwork;
import com.unique.overhust.Feedback.SendFeedback;
import com.unique.overhust.R;
import com.unique.overhust.UI.FeedbackDialog;

import java.util.Timer;
import java.util.TimerTask;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SettingActivity extends SwipeBackActivity {

    //控制滑动返回的边缘大小
    private static final int EDGE_SIZE = 100;

    private ImageView settingBackView;
    private ImageView feedbackView;
    private ImageView aboutUsView;
    private ImageView aboutOverHust;

    private MainActivity mMainActivity;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
        viewOnClick();
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
        settingBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //左侧滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(EDGE_SIZE);
    }

    public void findViews() {
        settingBackView = (ImageView) findViewById(R.id.settingback);
        feedbackView = (ImageView) findViewById(R.id.feedback);
        aboutUsView = (ImageView) findViewById(R.id.aboutus);
        aboutOverHust = (ImageView) findViewById(R.id.about_overhust);
    }

    public void viewOnClick() {
        feedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedbackDialog();
                netWork();
            }
        });
        aboutUsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutusIntent = new Intent(SettingActivity.this, AboutUs.class);
                startActivity(aboutusIntent);
            }
        });
        aboutOverHust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutOverHustIntent = new Intent(SettingActivity.this, AboutOverHust.class);
                startActivity(aboutOverHustIntent);

            }
        });
    }

    public void netWork() {
        //网络检查
        IsNetwork mIsNetwork = new IsNetwork(this);
        mIsNetwork.isNetwork();
    }

    public void showFeedbackDialog() {
//        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
//        final View feedbackView = factory.inflate(R.layout.alert_dialog_feedback, null);
//        new AlertDialog.Builder(SettingActivity.this)
//                .setTitle(R.string.alert_dialog_feedback)
//                .setView(feedbackView)
//                .setCancelable(false)
//                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        commitFeedback(feedbackView);
//                    }
//                })
//                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .create().show();
        FeedbackDialog mFeedbackDialog = new FeedbackDialog(this, R.style.FeedbackDialog);
        mFeedbackDialog.show();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AppMsg appMsg = AppMsg.makeText(this, "左侧边缘滑动返回", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.overhust), R.layout.appmsg_green);
//        appMsg.setLayoutGravity(Gravity.BOTTOM);
//        appMsg.show();
//        return false;
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
            return rootView;
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode,KeyEvent event){
//        if(keyCode==KeyEvent.KEYCODE_BACK){
////            Intent meIntent=new Intent(this,MainActivity.class);
////            startActivity(meIntent);
//            finish();
//
//        }
//        return false;
//    }
}
