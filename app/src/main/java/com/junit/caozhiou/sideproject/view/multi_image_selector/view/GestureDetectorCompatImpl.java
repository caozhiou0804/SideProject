package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.view.GestureDetector;
import android.view.MotionEvent;

abstract interface GestureDetectorCompatImpl {
	public abstract boolean isLongpressEnabled();

	public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);

	public abstract void setIsLongpressEnabled(boolean paramBoolean);

	public abstract void setOnDoubleTapListener(
			GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
}
