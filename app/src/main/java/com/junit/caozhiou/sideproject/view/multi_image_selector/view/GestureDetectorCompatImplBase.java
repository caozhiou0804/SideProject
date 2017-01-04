package com.junit.caozhiou.sideproject.view.multi_image_selector.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
	private int mTouchSlopSquare;
	private int mDoubleTapSlopSquare;
	private int mMinimumFlingVelocity;
	private int mMaximumFlingVelocity;
	private static final int LONGPRESS_TIMEOUT = ViewConfiguration
			.getLongPressTimeout();
	private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
	private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration
			.getDoubleTapTimeout();
	private static final int SHOW_PRESS = 1;
	private static final int LONG_PRESS = 2;
	private static final int TAP = 3;
	private final Handler mHandler;
	private final OnGestureListener mListener;
	private OnDoubleTapListener mDoubleTapListener;
	private boolean mStillDown;
	private boolean mDeferConfirmSingleTap;
	private boolean mInLongPress;
	private boolean mAlwaysInTapRegion;
	private boolean mAlwaysInBiggerTapRegion;
	private MotionEvent mCurrentDownEvent;
	private MotionEvent mPreviousUpEvent;
	private boolean mIsDoubleTapping;
	private float mLastFocusX;
	private float mLastFocusY;
	private float mDownFocusX;
	private float mDownFocusY;
	private boolean mIsLongpressEnabled;
	private VelocityTracker mVelocityTracker;

	public GestureDetectorCompatImplBase(Context context,
			OnGestureListener listener, Handler handler) {
		if (handler != null)
			this.mHandler = new GestureHandler(handler);
		else {
			this.mHandler = new GestureHandler();
		}
		this.mListener = listener;
		if ((listener instanceof OnDoubleTapListener)) {
			setOnDoubleTapListener((OnDoubleTapListener) listener);
		}
		init(context);
	}

	private void init(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("Context must not be null");
		}
		if (this.mListener == null) {
			throw new IllegalArgumentException(
					"OnGestureListener must not be null");
		}
		this.mIsLongpressEnabled = true;

		ViewConfiguration configuration = ViewConfiguration.get(context);
		int touchSlop = configuration.getScaledTouchSlop();
		int doubleTapSlop = configuration.getScaledDoubleTapSlop();
		this.mMinimumFlingVelocity = configuration
				.getScaledMinimumFlingVelocity();
		this.mMaximumFlingVelocity = configuration
				.getScaledMaximumFlingVelocity();

		this.mTouchSlopSquare = (touchSlop * touchSlop);
		this.mDoubleTapSlopSquare = (doubleTapSlop * doubleTapSlop);
	}

	public void setOnDoubleTapListener(
			OnDoubleTapListener onDoubleTapListener) {
		this.mDoubleTapListener = onDoubleTapListener;
	}

	public void setIsLongpressEnabled(boolean isLongpressEnabled) {
		this.mIsLongpressEnabled = isLongpressEnabled;
	}

	public boolean isLongpressEnabled() {
		return this.mIsLongpressEnabled;
	}

	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();

		if (this.mVelocityTracker == null) {
			this.mVelocityTracker = VelocityTracker.obtain();
		}
		this.mVelocityTracker.addMovement(ev);

		boolean pointerUp = (action & 0xFF) == 6;

		int skipIndex = pointerUp ? MotionEventCompat.getActionIndex(ev) : -1;

		float sumX = 0.0F;
		float sumY = 0.0F;
		int count = MotionEventCompat.getPointerCount(ev);
		for (int i = 0; i < count; i++)
			if (skipIndex != i) {
				sumX += MotionEventCompat.getX(ev, i);
				sumY += MotionEventCompat.getY(ev, i);
			}
		int div = pointerUp ? count - 1 : count;
		float focusX = sumX / div;
		float focusY = sumY / div;

		boolean handled = false;

		switch (action & 0xFF) {
		case 5:
			this.mDownFocusX = (this.mLastFocusX = focusX);
			this.mDownFocusY = (this.mLastFocusY = focusY);

			cancelTaps();
			break;
		case 6:
			this.mDownFocusX = (this.mLastFocusX = focusX);
			this.mDownFocusY = (this.mLastFocusY = focusY);

			this.mVelocityTracker.computeCurrentVelocity(1000,
					this.mMaximumFlingVelocity);
			int upIndex = MotionEventCompat.getActionIndex(ev);
			int id1 = MotionEventCompat.getPointerId(ev, upIndex);
			float x1 = VelocityTrackerCompat.getXVelocity(
					this.mVelocityTracker, id1);
			float y1 = VelocityTrackerCompat.getYVelocity(
					this.mVelocityTracker, id1);
			for (int i = 0; i < count; i++) {
				if (i == upIndex)
					continue;
				int id2 = MotionEventCompat.getPointerId(ev, i);
				float x = x1
						* VelocityTrackerCompat.getXVelocity(
								this.mVelocityTracker, id2);
				float y = y1
						* VelocityTrackerCompat.getYVelocity(
								this.mVelocityTracker, id2);

				float dot = x + y;
				if (dot < 0.0F) {
					this.mVelocityTracker.clear();
					break;
				}
			}
			break;
		case 0:
			if (this.mDoubleTapListener != null) {
				boolean hadTapMessage = this.mHandler.hasMessages(3);
				if (hadTapMessage)
					this.mHandler.removeMessages(3);
				if ((this.mCurrentDownEvent != null)
						&& (this.mPreviousUpEvent != null)
						&& (hadTapMessage)
						&& (isConsideredDoubleTap(this.mCurrentDownEvent,
								this.mPreviousUpEvent, ev))) {
					this.mIsDoubleTapping = true;

					handled |= this.mDoubleTapListener
							.onDoubleTap(this.mCurrentDownEvent);

					handled |= this.mDoubleTapListener.onDoubleTapEvent(ev);
				} else {
					this.mHandler
							.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
				}
			}

			this.mDownFocusX = (this.mLastFocusX = focusX);
			this.mDownFocusY = (this.mLastFocusY = focusY);
			if (this.mCurrentDownEvent != null) {
				this.mCurrentDownEvent.recycle();
			}
			this.mCurrentDownEvent = MotionEvent.obtain(ev);
			this.mAlwaysInTapRegion = true;
			this.mAlwaysInBiggerTapRegion = true;
			this.mStillDown = true;
			this.mInLongPress = false;
			this.mDeferConfirmSingleTap = false;

			if (this.mIsLongpressEnabled) {
				this.mHandler.removeMessages(2);
				this.mHandler.sendEmptyMessageAtTime(2,
						this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT
								+ LONGPRESS_TIMEOUT);
			}

			this.mHandler.sendEmptyMessageAtTime(1,
					this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
			handled |= this.mListener.onDown(ev);
			break;
		case 2:
			if (this.mInLongPress) {
				break;
			}
			float scrollX = this.mLastFocusX - focusX;
			float scrollY = this.mLastFocusY - focusY;
			if (this.mIsDoubleTapping) {
				handled |= this.mDoubleTapListener.onDoubleTapEvent(ev);
			} else if (this.mAlwaysInTapRegion) {
				int deltaX = (int) (focusX - this.mDownFocusX);
				int deltaY = (int) (focusY - this.mDownFocusY);
				int distance = deltaX * deltaX + deltaY * deltaY;
				if (distance > this.mTouchSlopSquare) {
					handled = this.mListener.onScroll(this.mCurrentDownEvent,
							ev, scrollX, scrollY);
					this.mLastFocusX = focusX;
					this.mLastFocusY = focusY;
					this.mAlwaysInTapRegion = false;
					this.mHandler.removeMessages(3);
					this.mHandler.removeMessages(1);
					this.mHandler.removeMessages(2);
				}
				if (distance > this.mTouchSlopSquare)
					this.mAlwaysInBiggerTapRegion = false;
			} else {
				if ((Math.abs(scrollX) < 1.0F) && (Math.abs(scrollY) < 1.0F))
					break;
				handled = this.mListener.onScroll(this.mCurrentDownEvent, ev,
						scrollX, scrollY);
				this.mLastFocusX = focusX;
				this.mLastFocusY = focusY;
			}
			break;
		case 1:
			this.mStillDown = false;
			MotionEvent currentUpEvent = MotionEvent.obtain(ev);
			if (this.mIsDoubleTapping) {
				handled |= this.mDoubleTapListener.onDoubleTapEvent(ev);
			} else if (this.mInLongPress) {
				this.mHandler.removeMessages(3);
				this.mInLongPress = false;
			} else if (this.mAlwaysInTapRegion) {
				handled = this.mListener.onSingleTapUp(ev);
				if ((this.mDeferConfirmSingleTap)
						&& (this.mDoubleTapListener != null))
					this.mDoubleTapListener.onSingleTapConfirmed(ev);
			} else {
				VelocityTracker velocityTracker = this.mVelocityTracker;
				int pointerId = MotionEventCompat.getPointerId(ev, 0);
				velocityTracker.computeCurrentVelocity(1000,
						this.mMaximumFlingVelocity);
				float velocityY = VelocityTrackerCompat.getYVelocity(
						velocityTracker, pointerId);

				float velocityX = VelocityTrackerCompat.getXVelocity(
						velocityTracker, pointerId);

				if ((Math.abs(velocityY) > this.mMinimumFlingVelocity)
						|| (Math.abs(velocityX) > this.mMinimumFlingVelocity)) {
					handled = this.mListener.onFling(this.mCurrentDownEvent,
							ev, velocityX, velocityY);
				}
			}
			if (this.mPreviousUpEvent != null) {
				this.mPreviousUpEvent.recycle();
			}

			this.mPreviousUpEvent = currentUpEvent;
			if (this.mVelocityTracker != null) {
				this.mVelocityTracker.recycle();
				this.mVelocityTracker = null;
			}
			this.mIsDoubleTapping = false;
			this.mDeferConfirmSingleTap = false;
			this.mHandler.removeMessages(1);
			this.mHandler.removeMessages(2);
			break;
		case 3:
			cancel();
		case 4:
		}

		return handled;
	}

	private void cancel() {
		this.mHandler.removeMessages(1);
		this.mHandler.removeMessages(2);
		this.mHandler.removeMessages(3);
		this.mVelocityTracker.recycle();
		this.mVelocityTracker = null;
		this.mIsDoubleTapping = false;
		this.mStillDown = false;
		this.mAlwaysInTapRegion = false;
		this.mAlwaysInBiggerTapRegion = false;
		this.mDeferConfirmSingleTap = false;
		if (this.mInLongPress)
			this.mInLongPress = false;
	}

	private void cancelTaps() {
		this.mHandler.removeMessages(1);
		this.mHandler.removeMessages(2);
		this.mHandler.removeMessages(3);
		this.mIsDoubleTapping = false;
		this.mAlwaysInTapRegion = false;
		this.mAlwaysInBiggerTapRegion = false;
		this.mDeferConfirmSingleTap = false;
		if (this.mInLongPress)
			this.mInLongPress = false;
	}

	private boolean isConsideredDoubleTap(MotionEvent firstDown,
			MotionEvent firstUp, MotionEvent secondDown) {
		if (!this.mAlwaysInBiggerTapRegion) {
			return false;
		}

		if (secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
			return false;
		}

		int deltaX = (int) firstDown.getX() - (int) secondDown.getX();
		int deltaY = (int) firstDown.getY() - (int) secondDown.getY();
		return deltaX * deltaX + deltaY * deltaY < this.mDoubleTapSlopSquare;
	}

	private void dispatchLongPress() {
		this.mHandler.removeMessages(3);
		this.mDeferConfirmSingleTap = false;
		this.mInLongPress = true;
		this.mListener.onLongPress(this.mCurrentDownEvent);
	}

	private class GestureHandler extends Handler {
		GestureHandler() {
		}

		GestureHandler(Handler handler) {
			super();
		}

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				GestureDetectorCompatImplBase.this.mListener
						.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
				break;
			case 2:
				GestureDetectorCompatImplBase.this.dispatchLongPress();
				break;
			case 3:
				if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null)
					break;
				if (!GestureDetectorCompatImplBase.this.mStillDown)
					GestureDetectorCompatImplBase.this.mDoubleTapListener
							.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
				else
					// GestureDetectorCompatImplBase.access$502(
					// GestureDetectorCompatImplBase.this, true);
					break;
			default:
				throw new RuntimeException("Unknown message " + msg);
			}
		}
	}
}
