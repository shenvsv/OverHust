package com.unique.overhust.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.unique.overhust.MainActivity.MainActivity;
import com.unique.overhust.MainActivity.MeActivity;
import com.unique.overhust.MainActivity.SettingActivity;
import com.unique.overhust.R;

/**
 * Created by fhw on 11/15/13.
 */
public class DrawerFragment extends Fragment {

    private View drawerView;
    private ImageView map, navigation, search, schoolbus, me, setting;
    private MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        drawerView = inflater.inflate(R.layout.fragment_drawer, null);
        findViews();

        //按钮监听
        setOnClick();

        return drawerView;
    }

    public void setOnClick() {
        map.setOnClickListener(new MyOnClickListener());
        navigation.setOnClickListener(new MyOnClickListener());
        search.setOnClickListener(new MyOnClickListener());
        schoolbus.setOnClickListener(new MyOnClickListener());
        me.setOnClickListener(new MyOnClickListener());
        setting.setOnClickListener(new MyOnClickListener());
    }

    public void findViews() {
        map = (ImageView) drawerView.findViewById(R.id.map);
        navigation = (ImageView) drawerView.findViewById(R.id.navigation);
        search = (ImageView) drawerView.findViewById(R.id.search);
        schoolbus = (ImageView) drawerView.findViewById(R.id.schoolbus);
        me = (ImageView) drawerView.findViewById(R.id.me);
        setting = (ImageView) drawerView.findViewById(R.id.setting);
    }

    class MyOnClickListener implements View.OnClickListener {

        FragmentManager fragmentManager = getFragmentManager();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.map:
                    MapFragment mMapFragment = new MapFragment();
                    FragmentTransaction mapTransaction = fragmentManager.beginTransaction();
                    mapTransaction.replace(R.id.content_frame, mMapFragment);
                    mapTransaction.commit();
                    mMainActivity.closeDrawer();
                    map.setImageResource(R.drawable.ic_map_press);
                    navigation.setImageResource(R.drawable.ic_navigation);
                    search.setImageResource(R.drawable.ic_search);
                    schoolbus.setImageResource(R.drawable.ic_schoolbus);
                    break;
                case R.id.navigation:
                    NavigationFragment mNavigationFragment = new NavigationFragment();
                    FragmentTransaction navigationTransaction = fragmentManager.beginTransaction();
                    navigationTransaction.replace(R.id.content_frame, mNavigationFragment);
                    navigationTransaction.commit();
                    mMainActivity.closeDrawer();
                    map.setImageResource(R.drawable.ic_map);
                    navigation.setImageResource(R.drawable.ic_navigation_press);
                    search.setImageResource(R.drawable.ic_search);
                    schoolbus.setImageResource(R.drawable.ic_schoolbus);
                    break;
                case R.id.schoolbus:
                    SchoolbusFragment mSchoolbusFragment = new SchoolbusFragment();
                    FragmentTransaction schoolbusTransaction = fragmentManager.beginTransaction();
                    schoolbusTransaction.replace(R.id.content_frame, mSchoolbusFragment);
                    schoolbusTransaction.commit();
                    mMainActivity.closeDrawer();
                    map.setImageResource(R.drawable.ic_map);
                    navigation.setImageResource(R.drawable.ic_navigation);
                    search.setImageResource(R.drawable.ic_search);
                    schoolbus.setImageResource(R.drawable.ic_schoolbus_press);
                    break;
                case R.id.search:
                    SearchFragment mSearchFragment = new SearchFragment();
                    FragmentTransaction searchTransaction = fragmentManager.beginTransaction();
                    searchTransaction.replace(R.id.content_frame, mSearchFragment);
                    searchTransaction.commit();
                    mMainActivity.closeDrawer();
                    map.setImageResource(R.drawable.ic_map);
                    navigation.setImageResource(R.drawable.ic_navigation);
                    search.setImageResource(R.drawable.ic_search_press);
                    schoolbus.setImageResource(R.drawable.ic_schoolbus);
                    break;
                case R.id.me:
                    //mMainActivity.closeDrawer();
                    Intent meIntent = new Intent(mMainActivity, MeActivity.class);
                    startActivity(meIntent);
                    //mMainActivity.finish();
                    break;
                case R.id.setting:
                    //mMainActivity.closeDrawer();
                    Intent settingIntent = new Intent(mMainActivity, SettingActivity.class);
                    startActivity(settingIntent);
                    //mMainActivity.finish();
                    break;
                default:
                    break;
            }
        }
    }

}
