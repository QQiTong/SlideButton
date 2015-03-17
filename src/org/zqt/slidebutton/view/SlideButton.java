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

	private Bitmap switchBG;	// �������صı���
	private Bitmap slideButtonBG; // ������ı���
	private boolean currentState = false;	// ���ص�ǰ��״̬, Ĭ��Ϊ: �ر�
	private int currentX;	// x���ƫ����
	private boolean isSliding = false;		// �Ƿ����ڻ�����
	private OnToggleStateChangedListener mListener;

	public SlideButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initBitmap();
	}

	/**
	 * ��������ǰ�ؼ��Ŀ��ʱ�ص�
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// ���ÿ��صĿ�͸�
		setMeasuredDimension(switchBG.getWidth(), switchBG.getHeight());
	}

	/**
	 * ���Ƶ�ǰ�ؼ��ķ���
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		// 1. �ѻ������صı�������������
		canvas.drawBitmap(switchBG, 0, 0, null);		// �ѱ���ƽ�̵��ؼ���
		
		// 2. ���ƻ�������ʾ��λ��, �����߹�
		
		if(isSliding) {
			int left = currentX - (slideButtonBG.getWidth() / 2);
			if(left < 0) {
				left = 0;
			} else if(left > switchBG.getWidth() - slideButtonBG.getWidth()) {
				left = switchBG.getWidth() - slideButtonBG.getWidth();
			}
			
			canvas.drawBitmap(slideButtonBG, left, 0, null);
		} else {
			if(currentState) {	// ���ƿ���Ϊ����״̬
				canvas.drawBitmap(slideButtonBG, switchBG.getWidth() - slideButtonBG.getWidth(), 0, null);
			} else {	// ���ƿ���Ϊ�ص�״̬
				canvas.drawBitmap(slideButtonBG, 0, 0, null);
			}
		}
		super.onDraw(canvas);
	}

	/**
	 * ����������ʱ��ʱ�ص��˷���
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // ����
			currentX = (int) event.getX();
			isSliding  = true;
			break;
		case MotionEvent.ACTION_MOVE: // �ƶ�
			currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP: // ̧��
			isSliding = false;
			
			// �жϵ�ǰ������, ƫ������һ��, �������������ĵ�С�ڱ��������ĵ�, ����Ϊ�ر�״̬
			
			int bgCenter = switchBG.getWidth() / 2;
			
			boolean state = currentX > bgCenter;		// �ı���״̬
			
			// �����û��ļ����¼�
			if(state != currentState && mListener != null) {
				mListener.onToggleStateChanged(state);
			}

			currentState = state;
			break;
		default:
			break;
		}

		invalidate();		// ˢ�µ�ǰ�ؼ�, ������onDraw�����ĵ���
		return true;
	}

	/**
	 * ��ʼ��ͼƬ
	 */
	private void initBitmap() {
		slideButtonBG = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button_background);
		switchBG = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
	}

	/**
	 * ���ÿ��ص�״̬
	 * @param b
	 */
	public void setToggleState(boolean b) {
		currentState = b;
	}

	/**
	 * ���ÿ���״̬�ı�ļ����¼�
	 * @param listener
	 */
	public void setOnToggleStateChangedListener(OnToggleStateChangedListener listener) {
		this.mListener = listener;
	}
}
