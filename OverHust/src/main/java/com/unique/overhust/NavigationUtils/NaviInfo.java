package com.unique.overhust.NavigationUtils;

import android.app.Activity;
import android.view.Gravity;

import com.devspark.appmsg.AppMsg;
import com.unique.overhust.R;

import java.util.ArrayList;

/**
 * Created by shenvsv on 13-12-29.
 */
public class NaviInfo {
    private final ArrayList<NavigationPoint> points;
    private Activity activity;
    private NavigationTools mTools;
    private ArrayList<NavigationPoint> infoPoints;
    private int position = 0;
    Boolean isEnd = false;
    public NaviInfo(Activity activity,NavigationTools mTools){
        this.activity = activity;
        this.mTools = mTools;
        points = mTools.getPoints();
        infoPoints = getPoints();
    }
    public void show(NavigationPoint b){
        checkPo(b);
    }

    private void checkPo(NavigationPoint point) {
        String angle = "";

        switch (infoPoints.get(position).getAngle()){
            case -1: angle = "左"; break;
            case  1: angle = "右"; break;
            default: angle = "前"; break;
        }
        int distance = distance(infoPoints.get(position), point)-20;
            if (distance < 0)
            {
                if (position < infoPoints.size() - 1) {
                    position++;
                    if(position == infoPoints.size() - 1){
                        isEnd = true;
                    }

                    return;
                }

            }
        if(isEnd){

            addToast(""+distance+" 米后到达终点");
        }else{
            addToast(""+distance+" 米后"+angle+"转");
        }


    }



    private ArrayList<NavigationPoint> getPoints(){
        ArrayList<NavigationPoint> infoPoints = new ArrayList<NavigationPoint>();
        for(int i = 1;i<points.size()-1;i++){
            NavigationPoint point = points.get(i);
            if (point.getAngle()!=2){
                infoPoints.add(point);
            }
        }
        infoPoints.add(points.get(points.size()-1));
        return infoPoints;
    }
    public void addToast(String info){
        AppMsg appMsg = AppMsg.makeText(activity, info, new AppMsg.Style(AppMsg.LENGTH_SHORT,R.color.info),R.layout.navi_msg);
        appMsg.setLayoutGravity(Gravity.TOP);
        appMsg.show();
    }
    private int distance(NavigationPoint a,NavigationPoint b)
    {
        double x,y,out;

        double PI=3.14159265;
        double R=6.371229*1e6;
        double wd1 = a.getiLatitu();
        double jd1 = a.getiLongti();
        double wd2 = b.getiLatitu();
        double jd2 = b.getiLongti();
        x=(jd2-jd1)*PI*R*Math.cos( ((wd1+wd2)/2) *PI/180)/180;
        y=(wd2-wd1)*PI*R/180;
        out=Math.hypot(x,y);
        return (int)out;
    }


}
