package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.view.MotionEvent;

abstract interface MotionEventVersionImpl {
	public abstract int findPointerIndex(MotionEvent paramMotionEvent,
										 int paramInt);

	public abstract int getPointerId(MotionEvent paramMotionEvent, int paramInt);

	public abstract float getX(MotionEvent paramMotionEvent, int paramInt);

	public abstract float getY(MotionEvent paramMotionEvent, int paramInt);

	public abstract int getPointerCount(MotionEvent paramMotionEvent);
}
