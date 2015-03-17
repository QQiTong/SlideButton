package org.zqt.slidebutton.interf;

/**
 * @author andong
 * 开关状态改变的监听事件
 */
public interface OnToggleStateChangedListener {

	/**
	 * 当开关的状态改变时回调此方法
	 * @param state 当前开关最新的状态
	 */
	void onToggleStateChanged(boolean state);
}
