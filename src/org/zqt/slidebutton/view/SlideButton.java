package org.zqt.slidebutton.view;

import org.zqt.slidebutton.R;
import org.zqt.slidebutton.interf.OnToggleStateChangedListener;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SlideButton extends View {

	private Bitmap switchBG;	// 滑动开关的背景
	private Bitmap slideButtonBG; // 滑动块的背景
	private boolean currentState = false;	// 开关当前的状态, 默认为: 关闭
	private int currentX;	// x轴的偏移量
	private boolean isSliding = false;		// 是否正在滑动中
	private OnToggleStateChangedListener mListener;

	public SlideButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initBitmap();
	}

	/**
	 * 当测量当前控件的宽高时回调
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// 设置开关的宽和高
		setMeasuredDimension(switchBG.getWidth(), switchBG.getHeight());
	}

	/**
	 * 绘制当前控件的方法
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		// 1. 把滑动开关的背景画到画布上
		canvas.drawBitmap(switchBG, 0, 0, null);		// 把背景平铺到控件上
		
		// 2. 绘制滑动块显示的位置, 开或者关
		
		if(isSliding) {
			int left = currentX - (slideButtonBG.getWidth() / 2);
			if(left < 0) {
				left = 0;
			} else if(left > switchBG.getWidth() - slideButtonBG.getWidth()) {
				left = switchBG.getWidth() - slideButtonBG.getWidth();
			}
			
			canvas.drawBitmap(slideButtonBG, left, 0, null);
		} else {
			if(currentState) {	// 绘制开关为开的状态
				canvas.drawBitmap(slideButtonBG, switchBG.getWidth() - slideButtonBG.getWidth(), 0, null);
			} else {	// 绘制开关为关的状态
				canvas.drawBitmap(slideButtonBG, 0, 0, null);
			}
		}
		super.onDraw(canvas);
	}

	/**
	 * 当产生触摸时间时回调此方法
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下
			currentX = (int) event.getX();
			isSliding  = true;
			break;
		case MotionEvent.ACTION_MOVE: // 移动
			currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP: // 抬起
			isSliding = false;
			
			// 判断当前滑动块, 偏向于哪一边, 如果滑动块的中心点小于背景的中心点, 设置为关闭状态
			
			int bgCenter = switchBG.getWidth() / 2;
			
			boolean state = currentX > bgCenter;		// 改变后的状态
			
			// 调用用户的监听事件
			if(state != currentState && mListener != null) {
				mListener.onToggleStateChanged(state);
			}

			currentState = state;
			break;
		default:
			break;
		}

		invalidate();		// 刷新当前控件, 会引起onDraw方法的调用
		return true;
	}

	/**
	 * 初始化图片
	 */
	private void initBitmap() {
		slideButtonBG = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button_background);
		switchBG = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
	}

	/**
	 * 设置开关的状态
	 * @param b
	 */
	public void setToggleState(boolean b) {
		currentState = b;
	}

	/**
	 * 设置开关状态改变的监听事件
	 * @param listener
	 */
	public void setOnToggleStateChangedListener(OnToggleStateChangedListener listener) {
		this.mListener = listener;
	}
}
