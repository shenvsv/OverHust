package com.unique.overhust.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.overhust.R;

/**
 * Created by fhw on 12/9/13.
 */
public class PhotoWallFragment extends Fragment {
    private View photoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        photoView = inflater.inflate(R.layout.fragment_photowall, null);
        return photoView;
    }
}
