package com.example.myswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：忆 on 2017/2/20 16:54
 * 功能：自定义开关
 */

public class MySwitch extends View {

    private static final String NAME_SPACE ="http://schemas.android.com/apk/res-auto" ;
    private Paint mPaint;
    private Bitmap mBitmapBg;
    private Bitmap mBitmapSlide;
    private int MAX_LEFT;
    private int mSlideLeft;
    private boolean isOpen;
    int startX=0;

    public MySwitch(Context context) {
        super(context);
        initView();
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        isOpen = attrs.getAttributeBooleanValue(NAME_SPACE,"chech",true);
        //获取属性
        int slideId = attrs.getAttributeResourceValue(NAME_SPACE,"slide",-1);
        //加载自定义滑块
        if (slideId>0){
            mBitmapSlide=BitmapFactory.decodeResource(getResources(),slideId);
        }
        if (isOpen){
            mSlideLeft = MAX_LEFT;
        }else {
            mSlideLeft=0;
        }
        invalidate();
    }

    /**
     * @param context      上下文
     * @param attrs        属性
     * @param defStyleAttr 风格样式
     */
    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //初始化背景
        mBitmapBg = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        //初始化滑块
        mBitmapSlide = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        //滑块最大左边距
        MAX_LEFT = mBitmapBg.getWidth()-mBitmapSlide.getWidth();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    if (isOpen) {
                        isOpen = false;
                        mSlideLeft = 0;
                    } else {
                        isOpen = true;
                        mSlideLeft = MAX_LEFT;
                    }
                    //view重绘的方法，刷新view，重新调用onDraw
                    invalidate();
                    //回调当前开关状态
                    if (mListener != null) {
                        mListener.OnCheckChange(MySwitch.this, isOpen);
                    }
                }
            }
        });
    }

    /**
     * 设置尺寸的回调
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //按照背景图片来确定控件大小
        setMeasuredDimension(mBitmapBg.getWidth(),mBitmapBg.getHeight());
    }

    //measure->layout->draw
    //onMeasure->onLayout->onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawRect(200, 200, 400, 400, mPaint);
        //绘制背景
        canvas.drawBitmap(mBitmapBg,0,0,null);
        canvas.drawBitmap(mBitmapSlide,mSlideLeft,0,null);
    }

    int moveX=0;//位移距离
    boolean isClick;//标记当前是触摸还是单击
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录起点x坐标
                startX = (int) event.getX();//获取相当于当前控件的X 坐标
                break;
            case MotionEvent.ACTION_MOVE:
                //2.记录移动后的x坐标
                int endX = (int) event.getX();
                //3.记录x偏移量
                int dx = endX-startX;
                //4.根据偏移量，更新mSlideLeft
                mSlideLeft+=dx;
                moveX+=Math.abs(dx);//向左向右都要统计下来，所以要用dx绝对值
                //避免滑块超出边界
                if (mSlideLeft<0){
                    mSlideLeft=0;
                }
                if (mSlideLeft>MAX_LEFT){
                    mSlideLeft=MAX_LEFT;
                }
                //5.刷新界面
                invalidate();
                //6.重新初始化坐标
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //根据位移判断是单击事件还是移动事件
                if (moveX<5){
                    //单击事件
                    isClick=true;
                }else {
                    //移动事件
                    isClick=false;
                }
                moveX=0;
                if (!isClick) {
                    //根据当前位置切换开关状态
                    if (mSlideLeft < MAX_LEFT / 2) {
                        mSlideLeft = 0;
                        isOpen = false;
                    } else {
                        mSlideLeft = MAX_LEFT;
                        isOpen = true;
                    }
                    invalidate();
                    if (mListener != null) {
                        mListener.OnCheckChange(MySwitch.this, isOpen);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private OnCheckChangeListener mListener;
    //设置开关监听
    public void setOnCheckChangeListener(OnCheckChangeListener listener){
        mListener = listener;
    }
    /**
     * 监听开关状态的回调接口
     */
    public interface OnCheckChangeListener{
         void OnCheckChange(View view,boolean isChecked);
    }
}
