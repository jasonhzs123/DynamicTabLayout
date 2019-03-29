package com.demo.hzs.slidetouchlayoutdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SlideTouchLayout extends LinearLayout {
    private Context mContext;
    private FloatItemClickListener listener;
    private String tag = getClass().getSimpleName();

    public SlideTouchLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SlideTouchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public void setFloatItemClickListener(FloatItemClickListener listener) {
        this.listener = listener;
    }


    private void initView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_slide_touch, null);
        addView(inflate);

        Button test_exit = findViewById(R.id.test_exit);
        test_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener && !isIntercept) {
                    listener.onExitClick();
                }
            }
        });
        Button test_change = findViewById(R.id.test_change);
        test_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener && !isIntercept) {
                    listener.onChangeClick();
                }
            }
        });
    }

    private boolean isIntercept = true;
    int lastX, lastY;
    private int l, b, r, t;
    private int originX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int ea = event.getAction();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        switch (ea) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();//获取触摸事件触摸位置的原始X坐标
                originX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //event.getRawX();获得移动的位置
                int dx = (int) event.getRawX() - lastX;
                l = getLeft() + dx;
                b = getBottom();
                r = getRight() + dx;
                t = getTop();
                //下面判断移动是否超出屏幕
                if (l < 0) {
                    l = 0;
                    r = l + getWidth();
                }
                if (r > screenWidth) {
                    r = screenWidth;
                    l = r - getWidth();
                }
                layout(l, t, r, b);
                lastX = (int) event.getRawX();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                int detX = Math.abs((int) event.getRawX() - originX);
                if (30 < detX) {// 防止点击的时候稍微有点移动点击事件被拦截了
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(tag, "onDraw");
        layout(l, t, r, b);
    }

    public interface FloatItemClickListener {
        void onExitClick();

        void onChangeClick();
    }
}
