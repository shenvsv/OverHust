package com.unique.overhust.fragment;


import java.util.ArrayList;
import java.util.Map;

import com.tencent.street.StreetThumbListener;
import com.tencent.street.StreetViewListener;
import com.tencent.street.StreetViewShow;
import com.tencent.street.a;
import com.tencent.street.map.basemap.GeoPoint;
import com.tencent.street.overlay.ItemizedOverlay;
import com.unique.overhust.MainActivity.MainActivity;
import com.unique.overhust.MapUtils.OverHustLocation;
import com.unique.overhust.MapUtils.StreetNavitationOverlay;
import com.unique.overhust.MapUtils.StreetOverlay;
import com.unique.overhust.MapUtils.StreetPoiData;
import com.unique.overhust.R;


import android.R.bool;
import android.R.integer;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony.Mms.Addr;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.EventLogTags.Description;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
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
import android.widget.Toast;


public class NavitationFragment extends Fragment implements TextWatcher {
    private View streetView;
    private ViewGroup mView;
    private ProgressDialog mDialog;

    private ImageView mImage;

    private Handler mHandler;

    private View mStreetView = null;

    private Button mButton;

    private GeoPoint center;

    private String key = "50ed4b26a236ee30947caf2b52a2a8f9";

    private Context mContext;

    private StreetViewListener mListener;

    private StreetNavitationOverlay overlay;

    private EditText endEditText;
    private EditText startEditText;
    private ListView mListView;
    private TextView mTextView;
    private ArrayAdapter<String> mAdapter;
    private final String[] mStrings = MyMap.name;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;
    private InputMethodManager imm;

    private MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        streetView = inflater
                .inflate(R.layout.fragment_navitation, container, false);


        mView = (LinearLayout) streetView.findViewById(R.id.streetlayout);

        mMainActivity = (MainActivity) getActivity();
        mImage = (ImageView) streetView.findViewById(R.id.image);
        // mButton=(Button) streetView.findViewById(R.id.button1);
        mListView = (ListView) streetView.findViewById(R.id.listView1);
        endEditText = (EditText) streetView.findViewById(R.id.editText1);
        startEditText = (EditText) streetView.findViewById(R.id.editText2);
        mRelativeLayout = (RelativeLayout) streetView
                .findViewById(R.id.searchlayout);
        mImageView = (ImageView) streetView.findViewById(R.id.imageView1);
        mTextView = (TextView) streetView.findViewById(R.id.textView1);
        mContext = getActivity();
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                if (startEditText.isFocused()) {
                    startEditText.setText(aString);


                }
                if (endEditText.isFocused()) {
                    endEditText.setText(mAdapter.getItem(position));

                }

                dismissList();


            }

        });
        mImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                double sx = 0;
                double sy = 0;
                double ex = 0;
                double ey = 0;
                Boolean isChecked = false;
                if (startEditText.getText().toString().equals("")) {
                    OverHustLocation mLocation = new OverHustLocation(mContext);
                    mLocation.getLocation();
                    sx = mLocation.getiLatitu();
                    sy = mLocation.getiLongti();
                    isChecked = true;

                } else if (MyMap.getLongitudeAndLatitude(startEditText.getText().toString()) != -1) {
                    int a = MyMap.getLongitudeAndLatitude(startEditText.getText().toString());
                    sx = MyMap.namex[a];
                    sy = MyMap.namey[a];
                    isChecked = true;

                } else {
                    Toast.makeText(mContext, "输入不详细", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isChecked) {
                    return;
                } else if (endEditText.getText().toString().equals("")) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(mContext, "请输入目的地", Toast.LENGTH_SHORT).show();

                            return;
                        }
                    });
                } else {
                    int a = MyMap.getLongitudeAndLatitude(endEditText.getText().toString());
                    ex = MyMap.namex[a];
                    ey = MyMap.namey[a];
                    initStreatView(sx, sy, ex, ey);
                    showDialog();
                }

            }


        });
        startEditText.addTextChangedListener(this);

        endEditText.addTextChangedListener(this);


        return streetView;
    }

    private void initStreatView(double sx, double sy, double ex, double ey) {
        // TODO Auto-generated method stub

        MyMap.findpath(sx, sy, ex, ey);
        addPoins(MyMap.path,ex,ey);
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

            }

            @Override
            public void onLoaded() {
                // TODO Auto-generated method stub
                mMainActivity.footImageView.setVisibility(View.VISIBLE);
                dismissDialog();
                mRelativeLayout.setVisibility(View.GONE);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStreetView.setVisibility(View.VISIBLE);
                        System.out.println("load ok!");
                        //mRelativeLayout.setVisibility(View.GONE);
                        final a status = StreetViewShow.getInstance().getStreetStatus();
                        System.out.println("a" + status.a + "b" + status.b + "c" + status.c + "d" + status.d + "e" + status.e);


                    }
                });

            }

            @Override
            public void onDataError() {
                // TODO Auto-generated method stub

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
        center = new GeoPoint((int) (MyMap.x[MyMap.path[0]] * 1E6), (int) (MyMap.y[MyMap.path[0]] * 1E6));
        StreetViewShow.getInstance().showStreetView(mContext, center, 100,
                mListener, 0, 0, key);

    }

    private void addPoins(int[] path,double ex,double ey) {
        // TODO Auto-generated method stub
        overlay = null;
        ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();
        System.out.println("anglexxx" + MyMap.angle.toString() + "/" + path.length);
        int i = 0;
        while (path[i] != 0) {
            //System.out.println("lala"+MyMap.angle[i]);
            if (i == 0) {
                pois.add(new StreetPoiData((int) (MyMap.x[path[i]] * 1E6),
                        (int) (MyMap.y[path[i]] * 1E6),
                        getBmhaha(R.drawable.navi_start),
                        getBmhaha(R.drawable.navi_start), 0));
            } else {

                if (MyMap.angle[i+1] == -1) {
                    pois.add(new StreetPoiData((int) (MyMap.x[path[i]] * 1E6),
                            (int) (MyMap.y[path[i]] * 1E6),
                            getBm(R.drawable.left),
                            getBm(R.drawable.left), 0));
                } else if (MyMap.angle[i+1] == 1) {
                    pois.add(new StreetPoiData((int) (MyMap.x[path[i]] * 1E6),
                            (int) (MyMap.y[path[i]] * 1E6),
                            getBm(R.drawable.right),
                            getBm(R.drawable.right), 0));
                } else {
                    pois.add(new StreetPoiData((int) (MyMap.x[path[i]] * 1E6),
                            (int) (MyMap.y[path[i]] * 1E6),
                            getBm(R.drawable.up),
                            getBm(R.drawable.up), 0));
                }

            }
            i++;
        }
        pois.add(new StreetPoiData((int) (ex * 1E6),
                (int) (ey * 1E6),
                getBmhaha(R.drawable.nava_end),
                getBmhaha(R.drawable.nava_end), 0));

        overlay = new StreetNavitationOverlay(pois);
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
//		if (mStreetView!=null) {
//			StreetViewShow.getInstance().destory();
//		}
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
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mListView.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
    }

    private void showList() {
        // TODO Auto-generated method stub
        mListView.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.GONE);
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
    public Dialog showDialog() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle("OverHust");
        mDialog.setMessage("正在加载导航...");
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(true);
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
