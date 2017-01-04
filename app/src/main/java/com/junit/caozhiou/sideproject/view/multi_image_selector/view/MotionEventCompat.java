package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.os.Build;
import android.view.MotionEvent;

public class MotionEventCompat {
	static final MotionEventVersionImpl IMPL;
	public static final int ACTION_MASK = 255;
	public static final int ACTION_POINTER_DOWN = 5;
	public static final int ACTION_POINTER_UP = 6;
	public static final int ACTION_HOVER_MOVE = 7;
	public static final int ACTION_SCROLL = 8;
	public static final int ACTION_POINTER_INDEX_MASK = 65280;
	public static final int ACTION_POINTER_INDEX_SHIFT = 8;
	public static final int ACTION_HOVER_ENTER = 9;
	public static final int ACTION_HOVER_EXIT = 10;

	public static int getActionMasked(MotionEvent event) {
		return event.getAction() & 0xFF;
	}

	public static int getActionIndex(MotionEvent event) {
		return (event.getAction() & 0xFF00) >> 8;
	}

	public static int findPointerIndex(MotionEvent event, int pointerId) {
		return IMPL.findPointerIndex(event, pointerId);
	}

	public static int getPointerId(MotionEvent event, int pointerIndex) {
		return IMPL.getPointerId(event, pointerIndex);
	}

	public static float getX(MotionEvent event, int pointerIndex) {
		return IMPL.getX(event, pointerIndex);
	}

	public static float getY(MotionEvent event, int pointerIndex) {
		return IMPL.getY(event, pointerIndex);
	}

	public static int getPointerCount(MotionEvent event) {
		return IMPL.getPointerCount(event);
	}

	static {
		if (Build.VERSION.SDK_INT >= 5)
			IMPL = new EclairMotionEventVersionImpl();
		else
			IMPL = new BaseMotionEventVersionImpl();
	}
}
