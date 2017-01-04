package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.view.MotionEvent;

public class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
	public int findPointerIndex(MotionEvent event, int pointerId) {
		if (pointerId == 0) {
			return 0;
		}
		return -1;
	}

	public int getPointerId(MotionEvent event, int pointerIndex) {
		if (pointerIndex == 0) {
			return 0;
		}
		throw new IndexOutOfBoundsException(
				"Pre-Eclair does not support multiple pointers");
	}

	public float getX(MotionEvent event, int pointerIndex) {
		if (pointerIndex == 0) {
			return event.getX();
		}
		throw new IndexOutOfBoundsException(
				"Pre-Eclair does not support multiple pointers");
	}

	public float getY(MotionEvent event, int pointerIndex) {
		if (pointerIndex == 0) {
			return event.getY();
		}
		throw new IndexOutOfBoundsException(
				"Pre-Eclair does not support multiple pointers");
	}

	public int getPointerCount(MotionEvent event) {
		return 1;
	}
}
