package com.guoqiao.basketballrecorder.CustomeView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.guoqiao.basketballrecorder.R;

import java.util.HashMap;

/**
 * Created by Guoqiao on 3/24/16.
 */
public class CourtView extends ImageView implements View.OnTouchListener {

    Context context;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int dotRadius;
    private HashMap pointerMap;
    

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

    public void init(){
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // create 3 circle button
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View scoreBtn = inflater.inflate(R.layout.score_btn, null, false);



                break;
            case MotionEvent.ACTION_UP:
                break;
            default:break;

        }

        return false;
    }
}
