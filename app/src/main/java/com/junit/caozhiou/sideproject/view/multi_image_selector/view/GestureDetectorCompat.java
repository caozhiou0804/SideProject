package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class GestureDetectorCompat {
	private final GestureDetectorCompatImpl mImpl;

	public GestureDetectorCompat(Context context,
			OnGestureListener listener) {
		this(context, listener, null);
	}

	public GestureDetectorCompat(Context context,
			OnGestureListener listener, Handler handler) {
		if (VERSION.SDK_INT > 17)
			this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context,
					listener, handler);
		else
			this.mImpl = new GestureDetectorCompatImplBase(context, listener,
					handler);
	}

	public boolean isLongpressEnabled() {
		return this.mImpl.isLongpressEnabled();
	}

	public boolean onTouchEvent(MotionEvent event) {
		return this.mImpl.onTouchEvent(event);
	}

	public void setIsLongpressEnabled(boolean enabled) {
		this.mImpl.setIsLongpressEnabled(enabled);
	}

	public void setOnDoubleTapListener(
			OnDoubleTapListener listener) {
		this.mImpl.setOnDoubleTapListener(listener);
	}
}
