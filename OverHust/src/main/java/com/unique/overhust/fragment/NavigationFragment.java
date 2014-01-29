package com.unique.overhust.fragment;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.tencent.street.StreetViewListener;
import com.tencent.street.StreetViewShow;
import com.tencent.street.a;
import com.tencent.street.map.basemap.GeoPoint;
import com.tencent.street.overlay.ItemizedOverlay;
import com.unique.overhust.CommonUtils.IsNetwork;
import com.unique.overhust.MainActivity.MainActivity;
import com.unique.overhust.MapUtils.OverHustLocation;
import com.unique.overhust.MapUtils.StreetPoiData;
import com.unique.overhust.NavigationUtils.NaviInfo;
import com.unique.overhust.NavigationUtils.NavigationPoint;
import com.unique.overhust.NavigationUtils.NavigationTools;
import com.unique.overhust.NavigationUtils.StreetNavigationOverlay;
import com.unique.overhust.R;
import com.unique.overhust.UI.LoadStreetDialog;

import java.util.ArrayList;


public class NavigationFragment extends Fragment implements TextWatcher{
    private View streetView;
    private ViewGroup mView;
    private LoadStreetDialog mDialog;

    private ImageView mImage;

    private Handler mHandler;

    private View mStreetView = null;

    private Button mButton;

    private GeoPoint center;

    private String key = "50ed4b26a236ee30947caf2b52a2a8f9";

    private Context mContext;

    private StreetViewListener mListener;

    private StreetNavigationOverlay overlay;

    //Views
    private TextView endEditText;
    private TextView startEditText;
    private EditText searchEdit;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private final String[] mStrings = NavigationPoint.name;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;
    private LinearLayout searchLayout;
    TextView mTextView = null;

    private InputMethodManager imm;

    private MainActivity mMainActivity;


    //连网检查
    private IsNetwork mIsNetWork;
    private NavigationTools navigationTools = null;
    private NaviInfo naviInfo = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init views
        streetView = inflater
                .inflate(R.layout.fragment_navigation, container, false);

        mView = (LinearLayout) streetView.findViewById(R.id.streetlayout);
        searchLayout = (LinearLayout)streetView.findViewById(R.id.search_layout);
        mMainActivity = (MainActivity) getActivity();
        mImage = (ImageView) streetView.findViewById(R.id.image);
        // mButton=(Button) streetView.findViewById(R.id.button1);
        mListView = (ListView) streetView.findViewById(R.id.listView1);
        endEditText = (TextView) streetView.findViewById(R.id.endText);
        startEditText = (TextView) streetView.findViewById(R.id.startText);
        searchEdit = (EditText) streetView.findViewById(R.id.search_edit);
        mRelativeLayout = (RelativeLayout) streetView
                .findViewById(R.id.searchlayout);
        mImageView = (ImageView) streetView.findViewById(R.id.imageView1);
        mContext = getActivity();


//        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_HIDDEN,
//                InputMethodManager.HIDE_NOT_ALWAYS);

        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mStrings);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mImage.setImageBitmap((Bitmap) msg.obj);
            }
        };
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                //mRelativeLayout.setVisibility(View.GONE);
                String aString = mAdapter.getItem(position);
                mTextView.setText(aString);
                dismissList();
                searchLayout.setVisibility(View.GONE);
            }
        });
        mImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                NavigationPoint startPoint;
                NavigationPoint endPoint;
                String startString = startEditText.getText().toString();
                String endString = endEditText.getText().toString();

                Boolean isChecked = false;
                if (startEditText.getText().toString().equals("")) {
                    OverHustLocation mLocation = new OverHustLocation(mContext);
                    mLocation.getLocation();
                    //System.out.println("location"+mLocation.getiLatitu()+mLocation.getiLongti());
                    startPoint = new NavigationPoint(mLocation.getiLatitu(), mLocation.getiLongti());
                    isChecked = true;

                } else {
                    startPoint = new NavigationPoint(startEditText.getText().toString());
                    isChecked = true;
                }
                if (!isChecked) {
                    return;
                } else if (endEditText.getText().toString().equals("")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //Toast.makeText(mContext, "请输入目的地", Toast.LENGTH_SHORT).show();
                            AppMsg appMsg = AppMsg.makeText(mMainActivity, "请输入目的地", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.alert),R.layout.appmsg_red);
                            appMsg.setLayoutGravity(Gravity.TOP);
                            appMsg.show();
                            return;
                        }
                    });
                } else {
                    endPoint = new NavigationPoint(endString);
                    initStreatView(startPoint, endPoint);
                }

            }

        });
        startEditText.setOnClickListener(searchListener);
        endEditText.setOnClickListener(searchListener);
        searchEdit.addTextChangedListener(this);
        mIsNetWork = new IsNetwork(mContext);
        mIsNetWork.isNetwork();

        return streetView;
    }
    private OnClickListener searchListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.startText:
                    mTextView = startEditText;
                    System.out.println("search1");
                    break;
                case R.id.endText:
                    mTextView = endEditText;
                    System.out.println("search2");
                    break;
                default:break;

            }
            searchLayout.setVisibility(View.VISIBLE);

        }
    };


    private void initStreatView(NavigationPoint startPoint, NavigationPoint endPoint) {
        // TODO Auto-generated method stub
        navigationTools = new NavigationTools(startPoint, endPoint);
        naviInfo = new NaviInfo(getActivity(), navigationTools);
        addPoins(navigationTools.getPoints());
        mListener = new StreetViewListener() {
            @Override
            public void onViewReturn(final View arg0) {
                // TODO Auto-generated method stub
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStreetView = arg0;
                        mView.addView(mStreetView);
                    }
                });
            }

            @Override
            public void onNetError() {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, "errorNet", Toast.LENGTH_SHORT).show();
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppMsg appMsg = AppMsg.makeText(mMainActivity, "网络连接错误", new AppMsg.Style(2000, R.color.alert), R.layout.appmsg_red);
                        appMsg.setLayoutGravity(Gravity.TOP);
                        appMsg.show();
                    }
                });
            }

            @Override
            public void onLoaded() {
                // TODO Auto-generated method stub
                mMainActivity.footImageView.setVisibility(View.VISIBLE);
                dismissDialog();
                mRelativeLayout.setVisibility(View.GONE);
                System.out.println("load ok");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStreetView.setVisibility(View.VISIBLE);
                        System.out.println("load ok!");
                        //mRelativeLayout.setVisibility(View.GONE);
                        final a status = StreetViewShow.getInstance().getStreetStatus();
                        System.out.println("a" + status.a + "b" + status.b + "c" + status.c + "d" + status.d + "e" + status.e);
                        NavigationPoint b = new NavigationPoint((double) status.c / 1E6, (double) status.d / 1E6);
                        naviInfo.show(b);
                    }
                });
            }

            @Override
            public void onDataError() {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, "errorData", Toast.LENGTH_SHORT).show();
                System.out.println("errorData");
            }

            @Override
            public void onAuthFail() {
                // TODO Auto-generated method stub

            }

            @Override
            public ItemizedOverlay getOverlay() {
                // TODO Auto-generated method stub
                return overlay;
            }
        };
        System.out.println("centre" + navigationTools.getPoints().get(0).getiLatitu() + navigationTools.getPoints().get(0).getiLatitu());
        center = new GeoPoint((int) (navigationTools.getPoints().get(0).getiLatitu() * 1E6), (int) (navigationTools.getPoints().get(0).getiLongti() * 1E6));
        //center=new GeoPoint((int)(30.508809 * 1E6),(int)(114.43087 * 1E6));
        StreetViewShow.getInstance().showStreetView(mContext, center, 100,
                mListener, 0, 0, key);
        showDialog();
    }

    private void addPoins(ArrayList<NavigationPoint> points) {
        // TODO Auto-generated method stub
        overlay = null;
        ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();

        pois.add(new StreetPoiData((int) (points.get(0).getiLatitu() * 1E6),
                (int) (points.get(0).getiLongti() * 1E6),
                getBmhaha(R.drawable.navi_start),
                getBmhaha(R.drawable.navi_start), 0));

        for (int i = 1; i < points.size() - 1; i++) {
            NavigationPoint point = points.get(i);
            switch (point.getAngle()) {
                case -1:
                    pois.add(new StreetPoiData((int) (point.getiLatitu() * 1E6),
                            (int) (point.getiLongti() * 1E6),
                            getBm(R.drawable.left),
                            getBm(R.drawable.left), 0));
                    break;
                case 1:
                    pois.add(new StreetPoiData((int) (point.getiLatitu() * 1E6),
                            (int) (point.getiLongti() * 1E6),
                            getBm(R.drawable.right),
                            getBm(R.drawable.right), 0));
                    break;
                default:
                    System.out.println("angle" + point.getAngle());
                    pois.add(new StreetPoiData((int) (point.getiLatitu() * 1E6),
                            (int) (point.getiLongti() * 1E6),
                            getBm(R.drawable.up),
                            getBm(R.drawable.up), 0));
                    break;
            }
        }
        int i = points.size() - 1;
        pois.add(new StreetPoiData((int) (points.get(i).getiLatitu() * 1E6),
                (int) (points.get(i).getiLongti() * 1E6),
                getBmhaha(R.drawable.nava_end),
                getBmhaha(R.drawable.nava_end), 0));
        overlay = new StreetNavigationOverlay(pois);
        overlay.populate();
    }

    private Bitmap getBm(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;
        options.inSampleSize = 4;

        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    private Bitmap getBmhaha(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;

        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
		if (mStreetView!=null) {
			StreetViewShow.getInstance().destory();
		}
    }

    @Override
    public void onResume() {
        super.onResume();
        // StreetViewShow.getInstance().requestStreetThumb("10041002111120153536407",//"10011505120412110900000",
        // new StreetThumbListener() {
        //
        // @Override
        // public void onGetThumbFail() {
        //
        // }
        //
        // @Override
        // public void onGetThumb(Bitmap bitmap) {
        // Message msg = new Message();
        // msg.obj = bitmap;
        // mHandler.sendMessage(msg);
        // }
        // });
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        // TODO Auto-generated method stub
        String newText = s.toString();

        if (TextUtils.isEmpty(newText)) {
            dismissList();
            mAdapter.getFilter().filter(s);
        } else {
            showList();
            mAdapter.getFilter().filter(s);
        }
    }

    private void dismissList() {
        // TODO Auto-generated method stub
        mListView.setVisibility(View.GONE);

    }

    private void showList() {
        // TODO Auto-generated method stub
        mListView.setVisibility(View.VISIBLE);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    //加载progressDialog
    public LoadStreetDialog showDialog() {
        mDialog=new LoadStreetDialog(mContext,R.style.LoadStreetDialog);
        mDialog.show();

        startEditText.setVisibility(View.INVISIBLE);
        endEditText.setVisibility(View.INVISIBLE);
        mImageView.setVisibility(View.INVISIBLE);

        return mDialog;
    }

    //销毁progressDialog
    public void dismissDialog() {
        mDialog.dismiss();
    }

}
