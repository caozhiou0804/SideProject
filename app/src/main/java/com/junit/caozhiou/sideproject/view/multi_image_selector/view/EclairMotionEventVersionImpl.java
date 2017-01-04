package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.view.MotionEvent;

public class EclairMotionEventVersionImpl implements MotionEventVersionImpl {
	public int findPointerIndex(MotionEvent event, int pointerId) {
		return MotionEventCompatEclair.findPointerIndex(event, pointerId);
	}

	public int getPointerId(MotionEvent event, int pointerIndex) {
		return MotionEventCompatEclair.getPointerId(event, pointerIndex);
	}

	public float getX(MotionEvent event, int pointerIndex) {
		return MotionEventCompatEclair.getX(event, pointerIndex);
	}

	public float getY(MotionEvent event, int pointerIndex) {
		return MotionEventCompatEclair.getY(event, pointerIndex);
	}

	public int getPointerCount(MotionEvent event) {
		return MotionEventCompatEclair.getPointerCount(event);
	}
}
