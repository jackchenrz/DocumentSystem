package com.publicstech.documentsystem.activity;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.MApplication;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.base.BaseActivity;
import com.publicstech.documentsystem.bean.UserBean;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.tools.ToolSP;
import com.publicstech.documentsystem.tools.ToolToast;

public class LoginActivity extends BaseActivity{

	@InjectView(R.id.etLoginName)
	EditText etLoginName;
	@InjectView(R.id.etPassword)
	EditText etPassword;
	@InjectView(R.id.cb_rember)
	CheckBox cbRember;
	@Override
	public int bindLayout() {
		return R.layout.activity_login;
	}

	@Override
	public void initView(View view) {
		
		ButterKnife.inject(this);
	}
	
	@OnClick(R.id.btnLogin)
	public void onLogin(){
		String userName = etLoginName.getText().toString().trim();
		String pwd = etPassword.getText().toString().trim();
		if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)){
			ToolToast.showToast(this, "帐号或密码不能为空");
			return;
		}
		loadingDialog = ToolAlert.createLoadingDialog(this, "正在登陆，请稍后...");
		loadingDialog.show();
		if(Const.LOCAL.equalsIgnoreCase(userName) && Const.LOCALPWD.equals(pwd)){
			loadingDialog.dismiss();
			Intent intent = new Intent(this,SettingActivity.class);
			startActivity(intent);
		}else{
			MApplication.assignData(Const.USERNAME, userName);
			url = "http://" + ToolSP.getString(this, Const.SERVICE_IP, "") + ":" + ToolSP.getString(this, Const.SERVICE_PORT, "") + "/RecSerApp.asmx";
			if("".equals(ToolSP.getString(this, Const.SERVICE_IP, ""))){
				loadingDialog.dismiss();
				ToolToast.showToast(this, "请先联系管理员设置IP地址");
				return;
			}
			MApplication.assignData(Const.SERVICE_URL, url);
			matchUser(userName,pwd);
		}
	}

	private void matchUser(final String userName, final String pwd) {
		
		
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("userName", userName);
		properties.put("passWord", pwd);
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, Const.LOGIN, properties , new WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					loadingDialog.dismiss();
					String string = result.getProperty(0).toString();
					Log.d(TAG, string);
					if("404".equals(string)){
						ToolToast.showToast(LoginActivity.this, "用户名或者密码错误");
					}else{
						if(cbRember.isChecked()){
							ToolSP.saveString(LoginActivity.this, Const.USERNAME, userName);
							ToolSP.saveString(LoginActivity.this, Const.PWD, pwd);
						}else{
							ToolSP.clear(LoginActivity.this);
						}
						UserBean userBean = ToolJson.getJsonBean(string, UserBean.class);
						if(userBean.ds != null || userBean.ds.size() != 0){
							MApplication.assignData(Const.TOKEN, userBean.ds.get(0).token);
							MApplication.assignData(Const.USERID, userBean.ds.get(0).user_id);
							MApplication.assignData(Const.STEP, userBean.ds.get(0).Step);
							Intent intent = new Intent(LoginActivity.this,SelectActivity.class);
							startActivity(intent);
						}else{
							ToolToast.showToast(LoginActivity.this, "联网错误");
							loadingDialog.dismiss();
						}
					}
					
				}else{
					ToolToast.showToast(LoginActivity.this, "登陆失败");
					loadingDialog.dismiss();
				}
			}
			
			@Override
			public void onFailure(String result) {
				loadingDialog.dismiss();
				if(result != null){
					Log.d(TAG, result);
				}
				ToolToast.showToast(LoginActivity.this, "联网错误，请检查网络连接");
			}
		});
	}

	@Override
	public void doBusiness(Context mContext) {
		etLoginName.setText(ToolSP.getString(this, Const.USERNAME, ""));
		etPassword.setText(ToolSP.getString(this, Const.PWD, ""));
	}

	@Override
	public void resume() {
		etLoginName.setText(ToolSP.getString(this, Const.USERNAME, ""));
		etPassword.setText(ToolSP.getString(this, Const.PWD, ""));
		MApplication.removeData(Const.TOKEN);
		MApplication.removeData(Const.USERID);
		MApplication.removeData(Const.POSTILUSERTYPE);
		MApplication.removeData(Const.RECID);
		MApplication.removeData(Const.USERNAME);
	}

	@Override
	public void destroy() {
		
	}
	private long exitTime = 0;
	private String url;
	private Dialog loadingDialog;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出应用",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				mApplication.removeAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
