package com.dujiaosongmu.music.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            intercepted = false;//当点击事件发生后父容器不拦截该事件
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
                intercepted = true;
            }
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastXIntercept;
            int deltaY = y - mLastYIntercept;
            if (Math.abs(deltaX) > Math.abs(deltaY)) {//如果水平滑动比竖直滑动大时则拦截事件
                intercepted = true;
            } else {
                intercepted = false;
            }
            break;
        }
        case MotionEvent.ACTION_UP: {//如果为true则子容器无法处理点击事件
            intercepted = false;
            break;
        }
        default:
            break;
        }

        Log.d(TAG, "intercepted=" + intercepted);
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            scrollBy(-deltaX, 0);
            break;
        }
        case MotionEvent.ACTION_UP: {
            int scrollX = getScrollX();//返回当前view的左边界位置
            int scrollToChildIndex = scrollX / mChildWidth;
            mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = mVelocityTracker.getXVelocity();
            //处理横向滑动距离
            if (Math.abs(xVelocity) >= 50) {
                mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
            } else {
                mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
            }
            mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
            int dx = mChildIndex * mChildWidth - scrollX;
            smoothScrollBy(dx, 0);
            mVelocityTracker.clear();
            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childCount = getChildCount();//获取布局中的子元素的个数
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //获取属性值当测量模式为at_most时我们需要获得spacesize的值来确定view在父容器的大小
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount == 0) {//如果没有子元素则将该布局的宽和高设为0
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {//判断高是否采用了wrap_content属性
            final View childView = getChildAt(0);//获取第一个子布局
            measuredHeight = childView.getMeasuredHeight();//获取子布局的高度
            setMeasuredDimension(widthSpaceSize, measuredHeight);//父容器的高度正好可以将子容器包裹
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {//判断宽是否采用了wrap_content属性
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;//所有的子元素宽度之和，因为事先规定每个子元素大小相同
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        } else {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();//回收速度检测对象
        super.onDetachedFromWindow();
    }
}
