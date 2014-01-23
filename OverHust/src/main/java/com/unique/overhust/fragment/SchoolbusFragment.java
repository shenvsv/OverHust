package com.unique.overhust.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.overhust.R;

/**
 * Created by fhw on 11/17/13.
 */
public class SchoolbusFragment extends Fragment {
    private View schoolbusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        schoolbusView = inflater.inflate(R.layout.fragment_schoolbus, null);
        return schoolbusView;
    }
}
