package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.view.MotionEvent;

public class MotionEventCompatEclair {
	public static int findPointerIndex(MotionEvent event, int pointerId) {
		return event.findPointerIndex(pointerId);
	}

	public static int getPointerId(MotionEvent event, int pointerIndex) {
		return event.getPointerId(pointerIndex);
	}

	public static float getX(MotionEvent event, int pointerIndex) {
		return event.getX(pointerIndex);
	}

	public static float getY(MotionEvent event, int pointerIndex) {
		return event.getY(pointerIndex);
	}

	public static int getPointerCount(MotionEvent event) {
		return event.getPointerCount();
	}
}
