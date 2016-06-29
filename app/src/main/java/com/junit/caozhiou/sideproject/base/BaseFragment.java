package com.junit.caozhiou.sideproject.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


/**
 * fragment基类
 * 
 * @author lubote
 * 
 */
public abstract class BaseFragment extends Fragment {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
	public abstract void tabNextRecharge();
	public abstract void refreshUserData();
	/**
	 * 添加引导图片
	 */
	public void addGuideImage(int guideResourceId) {
	
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		if (isVisibleToUser) {
			//fragment可见时加载数据
		} else {
			//不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
}
