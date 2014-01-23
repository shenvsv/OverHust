package com.unique.overhust.AboutOverHust;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aphidmobile.utils.UI;
import com.unique.overhust.FirstInto.Guides;
import com.unique.overhust.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhw on 12/29/13.
 */
public class OverHustAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<OverHustResources.Data> guidelData;

    private int repeatCount = 1;
    private Context mContext;

    public OverHustAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        guidelData = new ArrayList<OverHustResources.Data>(OverHustResources.IMG_DESCRIPTIONS);
        this.mContext = context;
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
            layout = inflater.inflate(R.layout.overhustsimple, null);
        }

        final OverHustResources.Data data = guidelData.get(position % guidelData.size());

        UI.<ImageView>findViewById(layout, R.id.overhustphoto).setBackgroundResource(data.imageId);

        UI.<TextView>findViewById(layout,R.id.version);

        return layout;
    }

    public void removeData(int index) {
        if (guidelData.size() > 1) {
            guidelData.remove(index);
        }
    }
}
