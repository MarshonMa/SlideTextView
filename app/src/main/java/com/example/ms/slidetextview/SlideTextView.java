package com.example.ms.slidetextview;
/**
 * Created by ms13421 on 2016/8/18.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


public class SlideTextView extends TextSwitcher implements
        ViewSwitcher.ViewFactory {
    private Context mContext;
    private float mHeight;
    private int mTextColor;
    //mInRight,mOutUp分离构成向右翻页的进出动画
    private RotateAnimation mInRight;
    private RotateAnimation mOutRight;

    //mInLeft,mOutDown分离构成向左翻页的进出动画
    private RotateAnimation mInLeft;
    private RotateAnimation mOutLeft;

    public SlideTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public SlideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideTextView);
        mHeight = a.getDimensionPixelOffset(R.styleable.SlideTextView_textSize, 36);
        mTextColor = a.getColor(R.styleable.SlideTextView_textColor, getResources().getColor(R.color.blue));
        a.recycle();
        mContext = context;
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        setFactory(this);
        mInRight = createAnim(true, true);
        mOutRight = createAnim(false, true);
        mInLeft = createAnim(true, false);
        mOutLeft = createAnim(false, false);
    }

    private RotateAnimation createAnim(boolean turnIn, boolean turnUp) {
        final RotateAnimation rotation = new RotateAnimation(turnIn, turnUp);
        rotation.setDuration(800);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new DecelerateInterpolator());
        rotation.setStartOffset(200);
        return rotation;
    }

    //这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView textview = new TextView(mContext);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, mHeight);
        textview.setMaxLines(1);
        textview.setTextColor(mTextColor);
        return textview;
    }

    //定义动作，向下滚动翻页
    public void previous() {
        if (getInAnimation() != mInRight) {
            clearAnimation();
            setInAnimation(mInRight);
        }
        if (getOutAnimation() != mOutRight) {
            setOutAnimation(mOutRight);
        }
    }

    //定义动作，向上滚动翻页
    public void next() {
        if (getInAnimation() != mInLeft) {
            setInAnimation(mInLeft);
        }
        if (getOutAnimation() != mOutLeft) {
            setOutAnimation(mOutLeft);
        }
    }

    class RotateAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public RotateAnimation(boolean turnIn, boolean turnUp) {
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight();
            mCenterX = getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int direction = mTurnUp ? 1 : -1;
            final Matrix matrix = t.getMatrix();
            camera.save();
            if (mTurnIn) {
                camera.translate(direction * mCenterX * (interpolatedTime - 1.05f), 0.0f, 0.0f);
            } else {
                camera.translate(direction * mCenterX * (interpolatedTime), 0.0f, 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(centerX, centerY);
            matrix.postTranslate(-centerX, -centerY);
        }
    }
}
