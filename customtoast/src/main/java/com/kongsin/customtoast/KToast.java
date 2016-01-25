package com.kongsin.customtoast;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Kongsin on 1/25/2016.
 */
public class KToast {

    public static final int SHORT = 2000;
    public static final int LONG = 3500;
    private TextView textTitle, textDetail;
    private Handler mHandler;
    private LinearLayout mContainer;
    private Animation animationIn, animationOut;
    private Context context;

    public KToast(Context context) {
        this.context = context;
        ViewGroup container = (ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content);
        View v = ((Activity) context).getLayoutInflater().inflate(
                R.layout.ktoast_layout, container);
        initView(v);
    }

    private void initView(View view) {

        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mContainer.setVisibility(View.GONE);
        textTitle = (TextView) view.findViewById(R.id.title);
        textDetail = (TextView) view.findViewById(R.id.detail);

        animationIn = AnimationUtils.loadAnimation(context, R.anim.activity_enter_anim);
        animationOut = AnimationUtils.loadAnimation(context, R.anim.activity_exit_anim);
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mHandler = new Handler();
    }

    public void show(String title, String detail, Integer colorBackGround, @ColorRes Integer colorText, int duration) {
        handleToast(title, detail, colorBackGround, colorText, duration);
    }

    private void handleToast(String title, String detail, Integer colorBackGround, @ColorRes Integer colorText, int duration) {
        if (colorBackGround != null) mContainer.setBackgroundResource(colorBackGround);
        if (colorText != null)
            textTitle.setTextColor(ContextCompat.getColor(context, colorText));
        if (colorText != null)
            textDetail.setTextColor(ContextCompat.getColor(context, colorText));

        if (title != null) textTitle.setText(title);
        else textTitle.setVisibility(View.GONE);

        if (detail != null) textDetail.setText(detail);
        else textDetail.setVisibility(View.GONE);

        if (animationIn.hasEnded()) {
            animationIn.reset();
        }
        if (animationOut.hasEnded()) {
            animationOut.reset();
        }
        mContainer.setVisibility(View.VISIBLE);
        mContainer.setGravity(Gravity.FILL | Gravity.TOP);
        mContainer.startAnimation(animationIn);

        mHandler.postDelayed(runnable, duration);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mContainer.startAnimation(animationOut);
        }
    };

}
