package com.unique.overhust.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.unique.overhust.CommonUtils.IsNetwork;
import com.unique.overhust.MainActivity.MainActivity;
import com.unique.overhust.CommonUtils.SearchCheeses;
import com.unique.overhust.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fhw on 11/17/13.
 */
public class SearchFragment extends Fragment implements TextWatcher {
    private View searchView;

    private EditText mEditText;
    private ListView mListView;
    private ImageView mImageView;
    private RelativeLayout mRelativeLayout;

    private Context mContext;
    private MainActivity mMainActivity;

    private ArrayAdapter<String> mAdapter;

    private final String[] mStrings = SearchCheeses.sCheeseStrings;

    private InputMethodManager mInputMethodManager;

    private Handler mHandler;

    private Resources mResources;
    private XmlPullParser parser;
    private AttributeSet attributes;
    private String[] urls = null;

    private IsNetwork mIsNetwork;

    public static int KEY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchView = inflater.inflate(R.layout.fragment_search, null);
        mMainActivity = (MainActivity) getActivity();
        mContext = mMainActivity;

        mIsNetwork = new IsNetwork(mContext);
        mIsNetwork.isNetwork();

        findViews();
        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        mInputMethodManager.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_HIDDEN,
//                InputMethodManager.HIDE_NOT_ALWAYS);

        mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, mStrings);
        mListView.setAdapter(mAdapter);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new ListViewListener());

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().equals("")) {
                    //Toast.makeText(mContext, "请输入查找类别", Toast.LENGTH_SHORT).show();
                    AppMsg appMsg = AppMsg.makeText(mMainActivity, "请输入查找类别", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.alert), R.layout.appmsg_red);
                    appMsg.setLayoutGravity(Gravity.TOP);
                    appMsg.show();
                } else {
                    if (mEditText.getText().toString().contains("食堂")) {
                        KEY = 1;
                    } else if (mEditText.getText().toString().contains("华科美景")) {
                        KEY = 2;
                    } else if (mEditText.getText().toString().contains("教学楼")) {
                        KEY = 3;
                    } else if (mEditText.getText().toString().contains("体育馆")) {
                        KEY = 4;
                    } else if (mEditText.getText().toString().contains("餐厅")) {
                        KEY = 1;
                    } else if (mEditText.getText().toString().contains("操场")) {
                        KEY = 5;
                    } else if (mEditText.getText().toString().contains("服务措施")) {
                        KEY = 6;
                    } else {
                        //Toast.makeText(mContext, "此类别目前没有图片", Toast.LENGTH_SHORT).show();
                        AppMsg appMsg = AppMsg.makeText(mMainActivity, "此类别目前没有图片", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.alert), R.layout.appmsg_red);
                        appMsg.setLayoutGravity(Gravity.TOP);
                        appMsg.show();
                        return;
                    }
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                    FragmentManager fragmentManager = getFragmentManager();
                    PhotoWallFragment mPhotoWallFragment = new PhotoWallFragment();
                    FragmentTransaction searchTransaction = fragmentManager.beginTransaction();
                    searchTransaction.replace(R.id.content_frame, mPhotoWallFragment);
                    searchTransaction.commit();
                }
            }
        });

        mEditText.addTextChangedListener(this);

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showList();
            }
        });

        return searchView;
    }

    public void findViews() {
        mEditText = (EditText) searchView.findViewById(R.id.search_edit);
        mListView = (ListView) searchView.findViewById(R.id.search_list);
        mImageView = (ImageView) searchView.findViewById(R.id.search_into);
        mRelativeLayout = (RelativeLayout) searchView.findViewById(R.id.search_start);
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
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mListView.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
    }

    private void showList() {
        // TODO Auto-generated method stub
        mListView.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        List list= Arrays.asList(mStrings);
        if(list.contains(mEditText.getText().toString())){
            dismissList();
        }
    }

    class ListViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String search_results = mAdapter.getItem(position);
            mEditText.setText(search_results);
            dismissList();
        }
    }
}
