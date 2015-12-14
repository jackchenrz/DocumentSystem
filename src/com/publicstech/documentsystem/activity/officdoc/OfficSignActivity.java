package com.publicstech.documentsystem.activity.officdoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
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
import com.publicstech.documentsystem.bean.SelUserBean;
import com.publicstech.documentsystem.bean.SelUserBean.SelUser;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolToast;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;

public class OfficSignActivity extends BaseActivity {

	@InjectView(R.id.tv_sel1)
	TextView tvSel1;
	@InjectView(R.id.tv_sel2)
	TextView tvSel2;
	@InjectView(R.id.etClass)
	EditText etClass;
	@InjectView(R.id.ivClass)
	ImageView ivClass;
	@InjectView(R.id.ll_sel1)
	LinearLayout llSel1;
	@InjectView(R.id.ll_sel2)
	LinearLayout llSel2;
	
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
	@InjectView(R.id.iv_line_seluser)
	ImageView ivSeluser;
	@InjectView(R.id.iv_line_radio)
	ImageView ivRadio;
	
	private List<SelUser> selList;
	private ArrayList<SelUser> list;
	private String[] items;
	private boolean[] bs;
	private String[] userIds;
	private int stepNo;
	private String userIdsStr;
	private String userId;
	private HashMap<String, String> properties = new HashMap<String, String>();
	
	
	@Override
	public int bindLayout() {
		return R.layout.activity_officsign;
	}

	@Override
	public void initView(View view) {
		ButterKnife.inject(this);
		Intent intent = getIntent();
		stepNo = intent.getIntExtra("stepNo", 0);
		if(stepNo == 2){
			llSel2.setVisibility(View.GONE);
			ivRadio.setVisibility(View.GONE);
			etNext.setText(getResources().getString(R.string.supervisorleader));
		}else if(stepNo == 3){
			llSel1.setVisibility(View.GONE);
			ivSeluser.setVisibility(View.GONE);
			etNext.setText(getResources().getString(R.string.officeleader));
		}else if(stepNo == 5){
			llSel1.setVisibility(View.GONE);
			ivSeluser.setVisibility(View.GONE);
			llSel2.setVisibility(View.GONE);
			ivRadio.setVisibility(View.GONE);
			etNext.setText(getResources().getString(R.string.pigeonhole));
		}
	}

	@Override
	public void doBusiness(Context mContext) {
		if(stepNo == 2){
			selUser(Const.SELECTUSER3);
		}
	}
	
	/**
	 * 获取选择人员的数据
	 * @param string
	 */
	private void selUser(String method) {
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("Token", MApplication.gainData(Const.TOKEN).toString());
		properties.put("userID", MApplication.gainData(Const.USERID).toString());
		ToolSOAP.callService(Const.SERVICE_URL, Const.SERVICE_NAMESPACE, method, properties , new WebServiceCallBack() {

			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					Log.d(TAG, string);
					if("404".equals(string)){
						ToolAlert.closeLoading();
					}else{
						SelUserBean selFuDuanBean = ToolJson.getJsonBean(string, SelUserBean.class);
						selList = selFuDuanBean.ds;
						ToolAlert.closeLoading();
					}
					
				}else{
					ToolToast.showToast(OfficSignActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(OfficSignActivity.this, "联网错误，请检查网络连接");
			}
		});
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
	
	@OnClick(R.id.ivClass)
	public void onSelClass(){
		etClass.setText("");
		MApplication.assignData("signUsers", "");
		list = new ArrayList<SelUser>(selList);
		if(list.size() == 0){
			ToolToast.showToast(this, "没有相关人员");
			return;
		}
		items = new String[list.size()];
		bs = new boolean[list.size()];
		userIds = new String[list.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = "[" + list.get(i).role_name + "]" + list.get(i).user_name;
			userIds[i] = list.get(i).user_id;
		}
		final StringBuffer sb = new StringBuffer();
		final StringBuffer sb1 = new StringBuffer();
		new  AlertDialog.Builder(this) 
		.setTitle("选择人员" )  
		.setMultiChoiceItems(items,  bs ,  new OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				bs[which] = isChecked;
			}
		} ) 
		.setPositiveButton("确定" , new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = 0; i < bs.length; i++) {
					if(bs[i]){
						sb.append(items[i]);
						sb1.append(userIds[i] + ",");
					}
				}
				etClass.setText(sb.toString());
				MApplication.assignData("signUsers", sb1.toString());
				items = null;
				bs = null;
				userIds = null;
			}
		})                  
		.setNegativeButton("取消" ,  null )  
		.show();
	}
	
	@OnClick(R.id.ivNext)
	public void onSelNext(){
		if(stepNo == 2){
			final String[] items = new String[]{getResources().getString(R.string.supervisorleader)};
			
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
			final String[] items = new String[]{getResources().getString(R.string.officeleader)};
			
			new  AlertDialog.Builder(this)  
			.setTitle("下一环节" )  
			.setItems(items,  new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					etNext.setText(items[which]);
				}
			} )
			.show();  
		}else if(stepNo == 5){
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
		if(MApplication.gainData("signUsers") != null && !"".equals(MApplication.gainData("signUsers").toString())){
			userIdsStr = MApplication.gainData("signUsers").toString();
			userId = userIdsStr.substring(0, userIdsStr.length() - 1);
		}else{
			userId = "";
		}
		
		
		if(stepNo == 2){
			properties.put("selectUser", userId);
			properties.put("stepNo", "3");
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("docID", MApplication.gainData(Const.RECID).toString());
			properties.put("postilDesc", text);
			if(MApplication.gainData("signUsers") == null ||
					"".equals(MApplication.gainData("signUsers"))){
				ToolAlert.closeLoading();
				ToolAlert.dialog(this, null, "您确定不选择审批人员吗？", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ToolAlert.loading(OfficSignActivity.this, "正在签阅");
						sign(properties,Const.SENDDOCSIGNREADINGKEZHANGAUDIT);
					}
				},new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
			}else{
				sign(properties,Const.SENDDOCSIGNREADINGKEZHANGAUDIT);
			}
		}else if(stepNo == 3){
			properties.put("stepNo", "4");
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("docID", MApplication.gainData(Const.RECID).toString());
			properties.put("postilDesc", text);
			sign(properties,Const.SENDDOCSIGNREADINGMAINLEADERAUDIT);
		}else if(stepNo == 5){
			properties.put("stepNo", "5");
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("docID", MApplication.gainData(Const.RECID).toString());
			properties.put("postilDesc", text);
			sign(properties,Const.SENDDOCSIGNREADINGCHUANAUDIT);
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
						ToolToast.showToast(OfficSignActivity.this, "签阅成功");
						ToolAlert.closeLoading();
						Intent intent = new Intent(OfficSignActivity.this,SelectActivity.class);
						OfficDocListActivity.instance.finish();
						mApplication.removeToTop();
						startActivity(intent);
					}else{
						ToolAlert.closeLoading();
					}
					
				}else{
					ToolToast.showToast(OfficSignActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(OfficSignActivity.this, "联网错误，请检查网络连接");
			}
		});
	}

}
