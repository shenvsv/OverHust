package com.unique.overhust.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.unique.overhust.R;


/**
 * Created by fhw on 1/3/14.
 */
public class NetworkErrorDialog extends Dialog {

    private ImageView iKnow;

    public NetworkErrorDialog(Context context) {
        super(context);
    }

    public NetworkErrorDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.networkerror_dialog);
        iKnow=(ImageView)findViewById(R.id.iknow);
        iKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.dialoganim);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
