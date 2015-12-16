package com.publicstech.documentsystem.activity;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.base.BaseActivity;
import com.publicstech.documentsystem.tools.ToolSP;
import com.publicstech.documentsystem.tools.ToolToast;

public class SettingActivity extends BaseActivity{

	@InjectView(R.id.etServerIP)
	EditText etServerIP;
	@InjectView(R.id.etServerPort)
	EditText etServerPort;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_setting;
	}

	@Override
	public void initView(View view) {
		ButterKnife.inject(this);
	}

	@Override
	public void doBusiness(Context mContext) {
		etServerIP.setText(ToolSP.getString(this, Const.SERVICE_IP, ""));
	}
	
	@OnClick(R.id.btnSave)
	public void onSave(){
		String serverIP = etServerIP.getText().toString();
		String serverPort = etServerPort.getText().toString();
		
		if(TextUtils.isEmpty(serverIP)){
			ToolToast.showToast(this, "请输入IP地址");
			return;
		}
		ToolSP.saveString(this, Const.SERVICE_IP, serverIP);
		ToolSP.saveString(this, Const.SERVICE_PORT, serverPort);
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		WeakReference<Activity> task = new WeakReference<Activity>(this);
		mApplication.removeTask(task);
	}

}
