package com.unique.overhust.CommonUtils;

import android.app.Application;

/**
 * Created by fhw on 12/21/13.
 */
public class ShareContext extends Application {
    private static ShareContext instance;

    public static ShareContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}
