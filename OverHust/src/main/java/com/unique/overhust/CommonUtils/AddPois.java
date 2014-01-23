package com.unique.overhust.CommonUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.unique.overhust.MapUtils.StreetPoiData;
import com.unique.overhust.NavigationUtils.NavigationPoint;
import com.unique.overhust.R;

import java.util.ArrayList;

/**
 * Created by vsv on 12/26/13.
 */
public class AddPois {

    private final Bitmap backBitmap;
    private ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();
    private Context mContext;
    private Bitmap textBitmap;
    private int w = 0;

    public AddPois(Context mContext) {
        this.mContext = mContext;

        //backSBitmap =  new BitmapFactory().decodeResource(mContext.getResources(), R.drawable.cover_short);

        textBitmap = new BitmapFactory().decodeResource(mContext.getResources(), R.drawable.cover_long);
        backBitmap = new BitmapFactory().decodeResource(mContext.getResources(),R.drawable.cover);
        w = backBitmap.getWidth()- 20;
        //System.out.println("t"+textBitmap.getHeight()+textBitmap.getWidth()+"/"+backBitmap.getHeight()+backBitmap.getWidth());//43255 6569   43-26 =
        for(int i = 0;i < NavigationPoint.name.length; i++){
            Bitmap bitmap = getNBm(NavigationPoint.name[i]);
            int x = (int)(NavigationPoint.namex[i]*1E6);
            int y = (int)(NavigationPoint.namey[i]*1E6);
            pois.add(new StreetPoiData(x,y,bitmap,bitmap,20));
        }

    }
    //demo add for release designer
    private Bitmap getNBm(String name){
        Bitmap bmp = null;
        int textSize = 17;
        Paint p = new Paint();
        String familyName ="黑体";
        Typeface font = Typeface.create(familyName,Typeface.BOLD);
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        p.setTypeface(font);

        p.setTextSize(textSize);
        bmp = Bitmap.createBitmap(name.length()*textSize+20 ,105, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(bmp);
        //canvasTemp.drawColor(Color.BLACK);
        canvasTemp.drawBitmap(zoomImage(textBitmap,name.length()*textSize+20,43)
                ,0,0,null);
        canvasTemp.drawBitmap(backBitmap, (name.length()*textSize - w)/2, 40, null);
        canvasTemp.drawText(name,10,textSize+12,p);

        return bmp;
    }

    private Bitmap getBm(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;

        return BitmapFactory.decodeResource(mContext.getResources(), resId, options);
    }
    public Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public ArrayList<StreetPoiData> getPois() {
        return pois;
    }
}
