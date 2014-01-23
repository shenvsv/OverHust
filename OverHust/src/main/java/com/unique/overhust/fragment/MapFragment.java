package com.unique.overhust.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.devspark.appmsg.AppMsg;
import com.tencent.street.StreetThumbListener;
import com.tencent.street.StreetViewListener;
import com.tencent.street.StreetViewShow;
import com.tencent.street.map.basemap.GeoPoint;
import com.tencent.street.overlay.ItemizedOverlay;
import com.unique.overhust.CommonUtils.IsNetwork;
import com.unique.overhust.MainActivity.MainActivity;
import com.unique.overhust.CommonUtils.AddPois;
import com.unique.overhust.MapUtils.OverHustLocation;
import com.unique.overhust.MapUtils.StreetOverlay;
import com.unique.overhust.MapUtils.StreetPoiData;
import com.unique.overhust.R;
import com.unique.overhust.UI.LoadStreetDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fhw on 11/17/13.
 */
public class MapFragment extends Fragment implements StreetViewListener {
    private ViewGroup streetView;

    private ImageView streetImageview, mapPreView, footImageView;
    private LoadStreetDialog mDialog;

    private Handler mHandler, locationHandler;

    private Context mContext;

    private MainActivity mMainActivity;

    private View mStreetview;
    private View mapView;

    private OverHustLocation mLocation;

    private GeoPoint currentCenter;

    //连网检查
    private IsNetwork mIsNetWork;

    private boolean isHustFlag = true, isFromInit = false;

    private AppMsg mAppMsg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapView = inflater.inflate(R.layout.fragment_map, null);
        mMainActivity = (MainActivity) getActivity();
        mContext = getActivity();
        findViews();
        showDialog();


        mIsNetWork = new IsNetwork(mContext);
        mIsNetWork.isNetwork();
        if (mIsNetWork.getNetworkState() == false) {
            dismissDialog();
            mMainActivity.openDrawer();
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                streetImageview.setImageBitmap((Bitmap) msg.obj);
            }
        };
        mLocation = new OverHustLocation(mContext);
        String key = "4fb2821bde027e675565c75b32245ad5";
        currentCenter = new GeoPoint((int) (mLocation.getiLatitu() * 1E6), (int) (mLocation.getiLongti() * 1E6));
        StreetViewShow.getInstance().showStreetView(mContext, currentCenter, 100, this, -170, 0, key);

        return mapView;
    }


    public void findViews() {
        streetView = (FrameLayout) mapView.findViewById(R.id.maplayout);
        streetImageview = (ImageView) mapView.findViewById(R.id.streeview);
        mapPreView = (ImageView) mapView.findViewById(R.id.map_pre);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainActivity.footImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        StreetViewShow.getInstance().requestStreetThumb(
                "10041002111120153536407",// "10011505120412110900000",
                new StreetThumbListener() {

                    @Override
                    public void onGetThumbFail() {

                    }

                    @Override
                    public void onGetThumb(Bitmap bitmap) {
                        Message msg = new Message();
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);

                    }
                });
    }

    StreetOverlay overlay;

    @Override
    public ItemizedOverlay getOverlay() {
        if (overlay == null) {
            ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();
            long a = System.currentTimeMillis();
            AddPois mAddPois = new AddPois(mContext);
            pois = mAddPois.getPois();
            long b = System.currentTimeMillis() - a;
            System.out.println("tim111" + b);
            overlay = new StreetOverlay(pois);
            overlay.populate();
        }
        return overlay;
    }

    private Bitmap getBm(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;

        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    @Override
    public void onViewReturn(final View v) {
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStreetview = v;
                streetView.addView(mStreetview);
            }
        });
    }

    public void isHust() {
        if (mLocation.getiLatitu() < 30.4 || mLocation.getiLatitu() > 30.6) {
            if (mLocation.getiLongti() < 114.4 || mLocation.getiLongti() > 114.5) {
                mAppMsg = AppMsg.makeText(mMainActivity, "", new AppMsg.Style(10000, R.color.overhust), R.layout.is_not_in_hust);
                mAppMsg.setLayoutGravity(Gravity.TOP);
                mAppMsg.show();
                if (mAppMsg.isFloating() == true) {
                    ImageView goHust = (ImageView) mAppMsg.getView().findViewById(R.id.go_hust);
                    goHust.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAppMsg.cancel();
                            loadHustView();
                        }
                    });
                }
            }
        }
    }

    public void loadHustView() {
        String key = "4fb2821bde027e675565c75b32245ad5";
        currentCenter = new GeoPoint(30508874, 114413638);
        StreetViewShow.getInstance().destory();
        streetView.removeAllViews();
        StreetViewShow.getInstance().showStreetView(mContext, currentCenter, 100, this, -35, 0, key);
    }

    @Override
    public void onNetError() {
        Log.e("neterror", "onNetError");
        dismissDialog();
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mapPreView.setVisibility(View.VISIBLE);
                AppMsg appMsg = AppMsg.makeText(mMainActivity, "网络连接错误", new AppMsg.Style(2000, R.color.alert), R.layout.appmsg_red);
                appMsg.setLayoutGravity(Gravity.TOP);
                appMsg.show();
            }
        });

    }

    @Override
    public void onDataError() {
        if (mDialog.showTime() >= 13) {

            //街景加载失败，加载默认街景
            loadHustView();
            isFromInit=true;

            AppMsg appMsg = AppMsg.makeText(mMainActivity, "数据加载出错，为您加载默认街景", new AppMsg.Style(3000, R.color.alert), R.layout.appmsg_red);
            appMsg.setLayoutGravity(Gravity.TOP);
            appMsg.show();
            dismissDialog();


//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    mMainActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mMainActivity.openDrawer();
//                        }
//                    });
//                }
//            }, 1000);

            return;
        }

        Log.e("dataerror", "onDataError");
        String key = "4fb2821bde027e675565c75b32245ad5";
        currentCenter = new GeoPoint((int) (mLocation.getiLatitu() * 1E6), (int) (mLocation.getiLongti() * 1E6));
        StreetViewShow.getInstance().showStreetView(mContext, currentCenter, 100, this, -170, 0, key);
    }

    @Override
    public void onLoaded() {
        mMainActivity.footImageView.setVisibility(View.VISIBLE);
        mapPreView.setVisibility(View.INVISIBLE);
        dismissDialog();
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStreetview.setVisibility(View.VISIBLE);
            }
        });
        if (isFromInit == false) {
            if (isHustFlag == true) {
                isHust();
                isHustFlag = false;
            }
        }
    }

    @Override
    public void onAuthFail() {
        // LogUtil.logStreet("onAuthFail");
    }

    //加载progressDialog
    public LoadStreetDialog showDialog() {
        mDialog = new LoadStreetDialog(mContext, R.style.LoadStreetDialog);
        mDialog.show();


        return mDialog;
    }

    /**
     * add a keylistener for progress dialog
     */
    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissDialog();
            }
            return false;
        }
    };

    //销毁progressDialog
    public void dismissDialog() {
        mDialog.dismiss();
    }

}
