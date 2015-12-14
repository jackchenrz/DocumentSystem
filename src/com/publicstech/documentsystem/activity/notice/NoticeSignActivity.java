package com.publicstech.documentsystem.activity.notice;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.MApplication;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.activity.SelectActivity;
import com.publicstech.documentsystem.base.BaseActivity;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.tools.ToolToast;

public class NoticeSignActivity extends BaseActivity {

	@InjectView(R.id.etNext)
	EditText etNext;
	@InjectView(R.id.ivNext)
	ImageView ivNext;
	@InjectView(R.id.etText)
	EditText etText;
	@InjectView(R.id.tv_text)
	TextView tvText;
	@InjectView(R.id.ll_text)
	LinearLayout llText;
	@InjectView(R.id.tv_notify)
	TextView tvNotify;
	@InjectView(R.id.btn_save)
	Button btnSave;
	
	private int stepNo;
	private HashMap<String, String> properties = new HashMap<String, String>();
	
	@Override
	public int bindLayout() {
		return R.layout.activity_noticesign;
	}

	@Override
	public void initView(View view) {
		ButterKnife.inject(this);
		Intent intent = getIntent();
		stepNo = intent.getIntExtra("stepNo", 0);
		if(stepNo == 2){
			etNext.setText(getResources().getString(R.string.sign));
		}else if(stepNo == 3){
			etNext.setText(getResources().getString(R.string.pigeonhole));
		}
	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@OnClick(R.id.ivNext)
	public void onSelNext(){
		if(stepNo == 2){
			final String[] items = new String[]{getResources().getString(R.string.sign)};
			
			new  AlertDialog.Builder(this)  
			.setTitle("下一环节" )  
			.setItems(items,  new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					etNext.setText(items[which]);
				}
			} )
			.show();  
		}else if(stepNo == 3){
			final String[] items = new String[]{getResources().getString(R.string.pigeonhole)};
			
			new  AlertDialog.Builder(this)  
			.setTitle("下一环节" )  
			.setItems(items,  new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					etNext.setText(items[which]);
				}
			} )
			.show();  
		}
	}
	
	
	@OnClick(R.id.btn_save)
	public void onSave(){
		ToolAlert.loading(this, "正在签阅");
		String text = etText.getText().toString().trim();
		if(stepNo == 2){
			properties.put("stepNo", "3");
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("noticeID", MApplication.gainData(Const.RECID).toString());
			properties.put("postilDesc", text);
			sign(properties,Const.NOTICESIGNREADINGKEZHANGAUDIT);
		}else if(stepNo == 3){
			properties.put("stepNo", "4");
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("noticeID", MApplication.gainData(Const.RECID).toString());
			properties.put("postilDesc", text);
			sign(properties,Const.NOTICESIGNREADINGCHUANAUDIT);
		}
	}
	
	
	/**
	 * 签批
	 * @param text
	 * @param userIds
	 * @param properties
	 */
	private void sign(HashMap<String, String> properties,String method) {
		ToolSOAP.callService(Const.SERVICE_URL, Const.SERVICE_NAMESPACE, method, properties , new WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					if("success".equals(string)){
						ToolToast.showToast(NoticeSignActivity.this, "签阅成功");
						ToolAlert.closeLoading();
						Intent intent = new Intent(NoticeSignActivity.this,SelectActivity.class);
						NoticeListActivity.instance.finish();
						mApplication.removeToTop();
						startActivity(intent);
					}else{
						ToolAlert.closeLoading();
					}
					
				}else{
					ToolToast.showToast(NoticeSignActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(NoticeSignActivity.this, "联网错误，请检查网络连接");
			}
		});
	}
	
	
	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}

}
