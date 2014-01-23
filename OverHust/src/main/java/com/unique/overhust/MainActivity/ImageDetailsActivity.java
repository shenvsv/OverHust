package com.unique.overhust.MainActivity;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.appmsg.AppMsg;
import com.unique.overhust.CommonUtils.ZoomImageView;
import com.unique.overhust.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ImageDetailsActivity extends SwipeBackActivity {

    //控制滑动返回的边缘大小
    private static final int EDGE_SIZE = 100;

    /**
     * 自定义的ImageView控制，可对图片进行多点触控缩放和拖动
     */
    private ZoomImageView zoomImageView;

    private SwipeBackLayout mSwipeBackLayout;

    /**
     * 待展示的图片
     */
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        zoomImageView = (ZoomImageView) findViewById(R.id.zoom_image_view);
        // 取出图片路径，并解析成Bitmap对象，然后在ZoomImageView中显示
        String imagePath = getIntent().getStringExtra("image_path");
        bitmap = BitmapFactory.decodeFile(imagePath);
        zoomImageView.setImageBitmap(bitmap);

        //左侧滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(EDGE_SIZE);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_image_details, container, false);
            return rootView;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AppMsg appMsg = AppMsg.makeText(this, "左滑边缘滑动返回", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.overhust),R.layout.appmsg_green);
//        appMsg.setLayoutGravity(Gravity.BOTTOM);
//        appMsg.show();
//        return false;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将Bitmap对象回收掉
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

}
