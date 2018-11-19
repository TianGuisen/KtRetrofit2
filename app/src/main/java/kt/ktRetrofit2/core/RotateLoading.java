package kt.ktRetrofit2.core;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import kt.ktRetrofit2.R;

import static kt.ktRetrofit2.KtApplication.appContext;


/**
 * Created by Administrator on 2017/3/20.
 */

public class RotateLoading extends View {
    
    /**
     * 圆的粗细
     */
    private int width = dp2px(3);
    /**
     * 半径
     */
    private int radius = dp2px(30);
    /**
     * 阴影偏移
     */
    private int shadowOffset = dp2px(3);
    /**
     * 圆的颜色
     */
    private int color = ContextCompat.getColor(appContext, R.color.colorPrimary);
    
    private static final int DEFAULT_SPEED_OF_DEGREE = 10;
    
    private Paint mPaint;
    
    private RectF loadingRectF;
    private RectF shadowRectF;
    
    private int topDegree = 10;
    private int bottomDegree = 190;
    
    private float arc;
    private boolean changeBigger = true;
    
    
    private boolean isStart = false;
    
    private int speedOfDegree;
    
    private float speedOfArc;
    
    public RotateLoading(Context context) {
        super(context);
        initView(context, null);
    }
    
    public RotateLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }
    
    public RotateLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }
    
    private void initView(Context context, AttributeSet attrs) {
        speedOfDegree = DEFAULT_SPEED_OF_DEGREE;
        
        speedOfDegree = 10;
        speedOfArc = speedOfDegree / 4;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();
        arc = 10;
        loadingRectF = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
        shadowRectF = new RectF(width / 2 - radius, height / 2 - radius + shadowOffset, width / 2 + radius, height / 2 + radius + shadowOffset);
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        if (!isStart) {
            return;
        }
        
        mPaint.setColor(Color.parseColor("#1a000000"));
        canvas.drawArc(shadowRectF, topDegree, arc, false, mPaint);
        canvas.drawArc(shadowRectF, bottomDegree, arc, false, mPaint);
        
        mPaint.setColor(color);
        canvas.drawArc(loadingRectF, topDegree, arc, false, mPaint);
        canvas.drawArc(loadingRectF, bottomDegree, arc, false, mPaint);
        
        topDegree += speedOfDegree;
        bottomDegree += speedOfDegree;
        if (topDegree > 360) {
            topDegree = topDegree - 360;
        }
        if (bottomDegree > 360) {
            bottomDegree = bottomDegree - 360;
        }
        
        if (changeBigger) {
            if (arc < 160) {
                arc += speedOfArc;
                invalidate();
            }
        } else {
            if (arc > speedOfDegree) {
                arc -= 2 * speedOfArc;
                invalidate();
            }
        }
        if (arc >= 160 || arc <= 10) {
            changeBigger = !changeBigger;
            invalidate();
        }
    }
    
    public void setLoadingColor(int color) {
        this.color = color;
    }
    
    public int getLoadingColor() {
        return color;
    }
    
    public void start() {
        startAnimator();
        isStart = true;
        invalidate();
    }
    
    public void stop() {
        stopAnimator();
        invalidate();
    }
    
    public boolean isStart() {
        return isStart;
    }
    
    private void startAnimator() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.0f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.0f, 1);
        scaleXAnimator.setDuration(300);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(300);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }
    
    private void stopAnimator() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1, 0);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1, 0);
        scaleXAnimator.setDuration(300);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(300);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                isStart = false;
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animator animation) {
                
            }
        });
        animatorSet.start();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isStart;
    }
    
    public int dp2px(int dp) {
        float density = appContext.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }
}
