package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureDetectorCompatImplJellybeanMr2 implements
		GestureDetectorCompatImpl {
	private final GestureDetector mDetector;

	public GestureDetectorCompatImplJellybeanMr2(Context context,
			GestureDetector.OnGestureListener listener, Handler handler) {
		this.mDetector = new GestureDetector(context, listener, handler);
	}

	public boolean isLongpressEnabled() {
		return this.mDetector.isLongpressEnabled();
	}

	public boolean onTouchEvent(MotionEvent ev) {
		return this.mDetector.onTouchEvent(ev);
	}

	public void setIsLongpressEnabled(boolean enabled) {
		this.mDetector.setIsLongpressEnabled(enabled);
	}

	public void setOnDoubleTapListener(
			GestureDetector.OnDoubleTapListener listener) {
		this.mDetector.setOnDoubleTapListener(listener);
	}
}
