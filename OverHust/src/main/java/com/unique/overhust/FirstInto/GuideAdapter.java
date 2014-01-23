package com.unique.overhust.FirstInto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aphidmobile.utils.UI;
import com.unique.overhust.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhw on 12/24/13.
 */
public class GuideAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Guides.Data> guidelData;

    private int repeatCount = 1;
    private Context mContext;

    public GuideAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        guidelData = new ArrayList<Guides.Data>(Guides.IMG_DESCRIPTIONS);
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return guidelData.size() * repeatCount;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = convertView;
        if (convertView == null) {
            layout = inflater.inflate(R.layout.guidesimple, null);
        }

        final Guides.Data data = guidelData.get(position % guidelData.size());

        UI.<ImageView>findViewById(layout, R.id.guidephoto).setBackgroundResource(data.imageId);

        return layout;
    }

    public void removeData(int index) {
        if (guidelData.size() > 1) {
            guidelData.remove(index);
        }
    }
}
