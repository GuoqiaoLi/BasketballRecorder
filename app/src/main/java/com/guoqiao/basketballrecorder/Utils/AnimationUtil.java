package com.guoqiao.basketballrecorder.Utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.guoqiao.basketballrecorder.R;

/**
 * Created by Guoqiao on 3/27/16.
 */
public class AnimationUtil {

    public static void startLeftShowAnimation(Context context, View view){
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_left);
        view.startAnimation(animation);
    }

    public static void startLeftHideAnimation(Context context, View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_left_hide);
        goneAfterAnimation(animation, view);
        view.startAnimation(animation);
    }

    public static void startUpShowAnimation(Context context, View view){
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_up);
        view.startAnimation(animation);
    }

    public static void startUpHideAnimation(Context context, View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_up_hide);
        goneAfterAnimation(animation, view);
        view.startAnimation(animation);
    }

    public static void startUpLeftShowAnimation(Context context, View view){
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_up_left);
        view.startAnimation(animation);
    }

    public static void startUpLeftHideAnimation(Context context, View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_up_left_hide);
        goneAfterAnimation(animation, view);
        view.startAnimation(animation);
    }

    public static void startUpRightAnimation(Context context, View view){
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition_up_right);
        view.startAnimation(animation);
    }


    private static void goneAfterAnimation(Animation animation, final View view){
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
