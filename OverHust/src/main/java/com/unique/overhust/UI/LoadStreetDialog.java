package com.unique.overhust.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.unique.overhust.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fhw on 1/4/14.
 */
public class LoadStreetDialog extends Dialog {
    private int time;
    private Handler handler;

    public LoadStreetDialog(Context context, int theme) {
        super(context, theme);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    time++;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_street_dialog);

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.dialoganim);
        super.show();
    }

    public int showTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        },0,1000);

        return time;
    }
}
