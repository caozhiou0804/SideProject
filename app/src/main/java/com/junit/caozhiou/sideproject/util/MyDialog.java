package com.junit.caozhiou.sideproject.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;


public class MyDialog extends Dialog {

	private LinearLayout mLinearLayoutView;

	private RelativeLayout mRelativeLayoutView;

	private LinearLayout mLinearLayoutMsgView;

	private LinearLayout mLinearLayoutTopView;

	private FrameLayout mFrameLayoutCustomView;

	private View mDialogView;

	private View mDivider;

	private TextView mTitle;

	private TextView mMessage;

	private ImageView mIcon;

	private Button mButton1;

	private Button mButton2;

	private Context context;

	public MyDialog(Context context) {
		super(context, R.style.dialog_untran);
		this.context = context;
		init(context);

	}

	// public MyDialog(Context context, int theme) {
	// super(context, R.style.dialog_untran);
	// this.context = context;
	// init(context);
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);

	}

	// public static MyDialog getInstance(Context context) {
	//
	//
	// if (instance == null) {
	// synchronized (MyDialog.class) {
	// if (instance == null) {
	// instance = new MyDialog(context, R.style.dialog_untran);
	// }
	// }
	// }
	// return instance;
	//
	// }

	private void init(final Context context) {
		// TODO Auto-generated method stub
		mDialogView = View.inflate(context, R.layout.dialog_layout, null);

		mLinearLayoutView = (LinearLayout) mDialogView
				.findViewById(R.id.parentPanel);
		mRelativeLayoutView = (RelativeLayout) mDialogView
				.findViewById(R.id.main);
		mLinearLayoutTopView = (LinearLayout) mDialogView
				.findViewById(R.id.topPanel);
		mLinearLayoutMsgView = (LinearLayout) mDialogView
				.findViewById(R.id.contentPanel);
		mFrameLayoutCustomView = (FrameLayout) mDialogView
				.findViewById(R.id.customPanel);

		mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
		mMessage = (TextView) mDialogView.findViewById(R.id.message);
		mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
		mDivider = mDialogView.findViewById(R.id.titleDivider);
		mButton1 = (Button) mDialogView.findViewById(R.id.button1);
		mButton2 = (Button) mDialogView.findViewById(R.id.button2);
		setContentView(mDialogView);
		//
		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {
				// 监听dialog展示，此时执行动画
				Animation anim_xml = AnimationUtils.loadAnimation(context,
						R.anim.dialog_anim_set);
				AnimationSet animationSet = new AnimationSet(true);

				Rotate3dAnimation animation = new Rotate3dAnimation(90, 0,
						mDialogView.getWidth() / 2f,
						mDialogView.getHeight() / 2f, 0, false);
				animation.setDuration(800);

				animationSet.addAnimation(animation);
				animationSet.addAnimation(anim_xml);
				mDialogView.startAnimation(animationSet);
			}
		});
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
//				Animation anim_xml = AnimationUtils.loadAnimation(context,
//						R.anim.dialog_anim_set);
//				AnimationSet animationSet = new AnimationSet(true);
//
//				Rotate3dAnimation animation = new Rotate3dAnimation(90, 0,
//						mDialogView.getWidth() / 2f,
//						mDialogView.getHeight() / 2f, 0, false);
//				animation.setDuration(800);
//
//				animationSet.addAnimation(animation);
//				animationSet.addAnimation(anim_xml);
//				mDialogView.startAnimation(animationSet);
			}
		});

	}

	public void onButton1Listener(
			android.view.View.OnClickListener clickListener) {
		mButton1.setOnClickListener(clickListener);
	}

	public void onButton2Listener(
			android.view.View.OnClickListener clickListener) {
		mButton2.setOnClickListener(clickListener);
	}
}
