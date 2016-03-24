package com.guoqiao.basketballrecorder.CustomeView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Guoqiao on 3/24/16.
 */
public class CourtView extends ImageView implements View.OnTouchListener {

    

    public CourtView(Context context) {
        super(context);
    }

    public CourtView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CourtView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CourtView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
