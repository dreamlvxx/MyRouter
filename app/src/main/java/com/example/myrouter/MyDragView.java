package com.example.myrouter;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

public class MyDragView extends LinearLayout {

    private ViewDragHelper mViewDragHelper;
    private ViewDragHelper.Callback mCallback;

    private View view0;

    private Point point0 = new Point();

    public MyDragView(Context context) {
        this(context, null);
    }

    public MyDragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view0 = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        point0.x = view0.getLeft();
        point0.y = view0.getTop();
    }

    public MyDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCallback = new ViewDragHelper.Callback() {


            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return getChildAt(0) == child;
            }


            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return point0.y;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - view0.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == view0) {
                    mViewDragHelper.settleCapturedViewAt(point0.x, point0.y);
                    invalidate();
                }
            }

        };
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

}
