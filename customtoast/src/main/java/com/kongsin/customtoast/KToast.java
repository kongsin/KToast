package com.kongsin.customtoast;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Kongsin on 1/25/2016.
 */
public class KToast implements GestureDetector.OnGestureListener {

    public static final int COLOR_TEAL = R.color.teal;
    public static final int BLUE_GRAY = R.color.blue_gray;
    public static final int DEEP_ORANGE = R.color.deep_orange;
    public static final int GREEN = R.color.green;
    public static final int GREY = R.color.grey;
    public static final int YELLOW = R.color.yellow;
    public static final int WHITE = R.color.white;
    public static final int BLACK = R.color.black;

    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;
    private TextView textTitle, textDetail;
    private Handler mHandler;
    private LinearLayout mContainer;
    private Animation animationIn, animationOut;
    private Context context;
    private GestureDetectorCompat gestureDetectorCompat;

    public KToast(Context context) {
        this.context = context;
        gestureDetectorCompat = new GestureDetectorCompat(context, this);
        ViewGroup container = (ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content);
        View v = ((Activity) context).getLayoutInflater().inflate(
                R.layout.ktoast_layout, container);
        initView(v);
    }

    private void initView(View view) {

        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mContainer.setVisibility(View.GONE);
        mContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mContainer.getX() < (mContainer.getPivotX() / 1.5)) {
                        mContainer.setX(0);
                    } else {
                        mContainer.startAnimation(animationOut);
                        mHandler.removeCallbacks(runnable);
                    }
                }
                return true;
            }
        });

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
                mContainer.setX(0);
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

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mContainer.setX(mContainer.getX() + (e2.getX() - e1.getX()));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }
}
