package com.zftlive.android.sample.animation;

import android.content.Context;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.common.ActionBarManager;

/**
 * 动画启动退出界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AnimationOutActivity extends BaseActivity {

	@Override
	public int bindLayout() {
		return R.layout.activity_launcher;
	}

	@Override
	public void initView(View view) {
		//初始化返回按钮
		ActionBarManager.initActionBar(getApplicationContext(), getActionBar());
		ActionBarManager.initBackTitle(getApplicationContext(),getActionBar());
	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}

}
