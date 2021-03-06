package com.publicstech.documentsystem.activity.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.publicstech.documentsystem.tools.ToolThread;
import com.publicstech.documentsystem.tools.ToolToast;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;

public class SignActivity extends BaseActivity {

	@InjectView(R.id.tv_sel1)
	TextView tvSel1;
	@InjectView(R.id.tv_sel2)
	TextView tvSel2;
	@InjectView(R.id.tv_sel3)
	TextView tvSel3;
	@InjectView(R.id.etClass)
	EditText etClass;
	@InjectView(R.id.etDep)
	EditText etDep;
	@InjectView(R.id.etMode)
	EditText etMode;
	@InjectView(R.id.ivClass)
	ImageView ivClass;
	@InjectView(R.id.ivDep)
	ImageView ivDep;
	@InjectView(R.id.ivMode)
	ImageView ivMode;
	@InjectView(R.id.ll_sel1)
	LinearLayout llSel1;
	@InjectView(R.id.ll_sel2)
	LinearLayout llSel2;
	@InjectView(R.id.ll_sel3)
	LinearLayout llSel3;
	
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
	
	@InjectView(R.id.iv_line_mode)
	ImageView iv_line_mode;
	@InjectView(R.id.iv_line_dep)
	ImageView iv_line_dep;
	

	private List<SelUser> selList;
	private List<SelUser> list;
	private String[] items;
	private boolean[] bs;
	private String[] userIds;
	private String userIdsStr;
	private String userId;
	private String hostUserIdsStr;
	private String hostUserIds;
	private String mode;
	private HashMap<String, String> properties = new HashMap<String, String>();
	private String string;
	private int stepNo;
	private String url;
	private final int FILLOK = 100;
	private final int FILLOK1 = 101;
	private int flag = 0;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FILLOK:
				final StringBuffer sb = new StringBuffer();
				final StringBuffer sb1 = new StringBuffer();
				new  AlertDialog.Builder(SignActivity.this) 
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
				break;
			case FILLOK1:
				final StringBuffer sb2 = new StringBuffer();
				final StringBuffer sb3 = new StringBuffer();
				new  AlertDialog.Builder(SignActivity.this)  
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
								sb2.append(items[i]);
								sb3.append(userIds[i] + ",");
							}
						}
						etDep.setText(sb2.toString());
						MApplication.assignData("lookUsers", sb3.toString());
						items = null;
						bs = null;
						userIds = null;
					}
				})                  
				.setNegativeButton("取消" ,  null )  
				.show();
				break;
			}
		};
	};
	private Dialog loadingDialog;
	@Override
	public int bindLayout() {
		return R.layout.activity_sign;
	}

	@Override
	public void initView(View view) {
		ButterKnife.inject(this);
		Intent intent = getIntent();
		string = intent.getStringExtra("str");
		stepNo = intent.getIntExtra("stepNo", 0);
		if(stepNo == 2){
			etNext.setText(getResources().getString(R.string.systemleader));
			etMode.setText(getResources().getString(R.string.stepstep));
			etMode.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					etClass.setText("");
					mode = etMode.getText().toString().trim();
					if(mode.equals(getResources().getString(R.string.stepstep))){
						llSel2.setVisibility(View.VISIBLE);
						iv_line_dep.setVisibility(View.VISIBLE);
						tvSel1.setText("班子成员");
					}else if(mode.equals(getResources().getString(R.string.direct))){
						llSel2.setVisibility(View.GONE);
						iv_line_dep.setVisibility(View.GONE);
						tvSel1.setText("选择人员");
					}
				}
			});
		}else if(stepNo == 3){
			llSel2.setVisibility(View.GONE);
			iv_line_dep.setVisibility(View.GONE);
			llSel3.setVisibility(View.GONE);
			iv_line_mode.setVisibility(View.GONE);
			tvSel1.setText("选择人员");
			etNext.setText(getResources().getString(R.string.department));
		}else if(stepNo == 4){
			if(MApplication.gainData(Const.TOKEN).toString().equals(getResources().getString(R.string.keshikezhang))){
				llSel3.setVisibility(View.GONE);
				iv_line_mode.setVisibility(View.GONE);
				tvSel1.setText("选择主办专职");
				tvSel2.setText("选择查看人员");
				etNext.setText(getResources().getString(R.string.workshop));
			}else{
				llSel3.setVisibility(View.GONE);
				iv_line_mode.setVisibility(View.GONE);
				if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("3")){
					tvSel1.setText("选择批阅人员");
					tvSel2.setText("选择查看人员");
				}else if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("4")){
					llSel1.setVisibility(View.GONE);
					llSel2.setVisibility(View.GONE);
					iv_line_dep.setVisibility(View.GONE);
					llText.setVisibility(View.GONE);
					tvText.setVisibility(View.GONE);
				}
				etNext.setText(getResources().getString(R.string.workshop));
			}
		}else{
			llSel3.setVisibility(View.GONE);
			iv_line_mode.setVisibility(View.GONE);
			if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("3")){
				tvSel1.setText("选择批阅人员");
				tvSel2.setText("选择查看人员");
			}else if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("4")){
				llSel1.setVisibility(View.GONE);
				llSel2.setVisibility(View.GONE);
				iv_line_dep.setVisibility(View.GONE);
				llText.setVisibility(View.GONE);
				tvText.setVisibility(View.GONE);
			}
			etNext.setText(getResources().getString(R.string.pigeonhole));
		}
		if(!"keshi".equals(string)){
			btnSave.setClickable(true);
			tvNotify.setText(string);
		}else{
			btnSave.setClickable(false);
			tvNotify.setText("科室未签阅，暂不可办理。如急于落实，请与相关科室联系，或先行传达落实。");
		}
	}

	@Override
	public void doBusiness(Context mContext) {
		url = MApplication.gainData(Const.SERVICE_URL).toString();
		if(stepNo != 2){
			ToolAlert.loading(this, "正在加载...",false);
			selUser( MApplication.gainData(Const.TOKEN).toString(),0);
		}
		MApplication.assignData("signUsers", "");
		MApplication.assignData("lookUsers", "");
	}

	/**
	 * 获取选择人员的数据
	 * @param string
	 */
	private void selUser(String token,final int flag) {
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("Token",token);
		properties.put("userID", MApplication.gainData(Const.USERID).toString());
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, Const.SELECTUSER2, properties , new WebServiceCallBack() {
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
						if(stepNo == 2){
							
							if(flag == 1){
								etClass.setText("");
								MApplication.assignData("signUsers", "");
								list = new ArrayList<SelUser>(selList);
								if(list.size() == 0){
									ToolToast.showToast(SignActivity.this, "没有相关人员");
									return;
								}
								items = new String[list.size()];
								bs = new boolean[list.size()];
								userIds = new String[list.size()];
								ToolThread.runInBackground(new Runnable() {
									
									@Override
									public void run() {
										for (int i = 0; i < items.length; i++) {
											items[i] = "[" + list.get(i).role_name + "]" + list.get(i).user_name;
											userIds[i] = list.get(i).user_id;
										}
										handler.sendEmptyMessage(FILLOK);
									}
								});
							}else if(flag == 2){
								etDep.setText("");
								MApplication.assignData("lookUsers", "");
								list = new ArrayList<SelUser>(selList);
								if(list.size() == 0){
									ToolToast.showToast(SignActivity.this, "没有相关人员");
									return;
								}
								items = new String[list.size()];
								bs = new boolean[list.size()];
								userIds = new String[list.size()];
								ToolThread.runInBackground(new Runnable() {
									
									@Override
									public void run() {
										for (int i = 0; i < items.length; i++) {
											items[i] = "[" + list.get(i).role_name + "]" + list.get(i).user_name;
											userIds[i] = list.get(i).user_id;
										}
										handler.sendEmptyMessage(FILLOK1);
									}
								});
							}
						}
					}
					
				}else{
					ToolToast.showToast(SignActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(SignActivity.this, "联网错误，请检查网络连接");
			}
		});
		
	}
	
	@OnClick(R.id.ivClass)
	public void onSelClass(){
		flag = 1;
		if(stepNo == 2){
			String mode = etMode.getText().toString().trim();
			if(getResources().getString(R.string.stepstep).equals(mode)){
				selUser( MApplication.gainData(Const.TOKEN).toString(),flag);
			}else if(getResources().getString(R.string.direct).equals(mode)){
				selUser(getResources().getString(R.string.fuduanzhang),flag);
			}
		}else{
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
			ToolThread.runInBackground(new Runnable() {
				
				@Override
				public void run() {
					for (int i = 0; i < items.length; i++) {
						items[i] = "[" + list.get(i).role_name + "]" + list.get(i).user_name;
						userIds[i] = list.get(i).user_id;
					}
					handler.sendEmptyMessage(FILLOK);
				}
			});
			
		}
	}
	
	@OnClick(R.id.ivDep)
	public void onSelLookMan(){
		flag = 2;
		if(stepNo == 2){
			selUser(getResources().getString(R.string.fuduanzhang),flag);
		}else{
			etDep.setText("");
			MApplication.assignData("lookUsers", "");
			list = new ArrayList<SelUser>(selList);
			if(list.size() == 0){
				ToolToast.showToast(this, "没有相关人员");
				return;
			}
			items = new String[list.size()];
			bs = new boolean[list.size()];
			userIds = new String[list.size()];
			ToolThread.runInBackground(new Runnable() {
				
				@Override
				public void run() {
					for (int i = 0; i < items.length; i++) {
						items[i] = "[" + list.get(i).role_name + "]" + list.get(i).user_name;
						userIds[i] = list.get(i).user_id;
					}
					handler.sendEmptyMessage(FILLOK1);
				}
			});
		}
		
	}
	
	
	@OnClick(R.id.ivMode)
	public void onSelMode(){
		final String[] items = new String[]{getResources().getString(R.string.stepstep),getResources().getString(R.string.direct)};
		
		new  AlertDialog.Builder(this)  
		.setTitle("下一环节" )  
		.setItems(items,  new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				etMode.setText(items[which]);
			}
		} )
		.show(); 
	}

	@OnClick(R.id.ivNext)
	public void onSelNext(){
		if(stepNo == 2){
			final String[] items = new String[]{getResources().getString(R.string.systemleader)};
			
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
			final String[] items = new String[]{getResources().getString(R.string.department)};
			
			new  AlertDialog.Builder(this)  
			.setTitle("下一环节" )  
			.setItems(items,  new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					etNext.setText(items[which]);
				}
			} )
			.show();  
		}else if(stepNo == 4){
			final String[] items = new String[]{getResources().getString(R.string.workshop)};
			
			new  AlertDialog.Builder(this)  
			.setTitle("下一环节" )  
			.setItems(items,  new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					etNext.setText(items[which]);
				}
			} )
			.show();
		}else{
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

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
	
	@OnClick(R.id.btn_save)
	public void onSave(){
		loadingDialog = ToolAlert.createLoadingDialog(this, "正在签阅...");
		loadingDialog.show();
		String text = etText.getText().toString().trim();
		mode = etMode.getText().toString().trim();
		if(MApplication.gainData("signUsers") != null && !"".equals(MApplication.gainData("signUsers").toString())){
			userIdsStr = MApplication.gainData("signUsers").toString();
			userId = userIdsStr.substring(0, userIdsStr.length() - 1);
		}else{
			userId = "";
		}
		if(MApplication.gainData("lookUsers") != null && !"".equals(MApplication.gainData("lookUsers").toString())){
			hostUserIdsStr = MApplication.gainData("lookUsers").toString();
			hostUserIds = hostUserIdsStr.substring(0, hostUserIdsStr.length() - 1);
		}else{
			hostUserIds = "";
		}
		
		//各级权限签阅
		if(stepNo == 2){
			properties.put("signFlag", mode);
			properties.put("selectUserID", userId);
			properties.put("deptSelectUserID", hostUserIds);
			properties.put("stepNo", "3");
			properties.put("postilDesc", text);
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("recID", MApplication.gainData(Const.RECID).toString());
			if(MApplication.gainData("signUsers") == null ||
					"".equals(MApplication.gainData("signUsers"))){
				loadingDialog.dismiss();
				ToolAlert.dialog(this, null, "您确定不选择审批人员吗？", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						loadingDialog.show();
						sign(properties,Const.SIGNREADINGDUANZHANG);
					}
				},new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
			}else{
				sign(properties,Const.SIGNREADINGDUANZHANG);
			}
		}else if(stepNo == 3){
			properties.put("selectUserID", userId);
			properties.put("stepNo", "4");
			properties.put("postilDesc", text);
			properties.put("userID", MApplication.gainData(Const.USERID).toString());
			properties.put("recID", MApplication.gainData(Const.RECID).toString());
			if(MApplication.gainData("signUsers") == null || 
					"".equals(MApplication.gainData("signUsers"))){
				loadingDialog.dismiss();
				ToolAlert.dialog(this, null, "您确定不选择审批人员吗？", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						loadingDialog.show();
						sign(properties,Const.SIGNREADINGFUDUAN);
					}
				},new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
			}else{
				sign(properties,Const.SIGNREADINGFUDUAN);
			}
		}else if(stepNo == 4){
			if(MApplication.gainData(Const.TOKEN).toString().equals(getResources().getString(R.string.keshikezhang))){
				properties.put("hostSelectUserID", userId);
				properties.put("lookSelectUserID", hostUserIds);
				properties.put("stepNo", "5");
				properties.put("postilDesc", text);
				properties.put("userID", MApplication.gainData(Const.USERID).toString());
				properties.put("recID", MApplication.gainData(Const.RECID).toString());
				if(MApplication.gainData("signUsers") == null ||
						"".equals(MApplication.gainData("signUsers"))){
					loadingDialog.dismiss();
					ToolAlert.dialog(this, null, "您确定不选择审批人员吗？", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							loadingDialog.show();
							sign(properties,Const.SIGNREADINGKESHIKZ);
						}
					},new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
				}else{
					sign(properties,Const.SIGNREADINGKESHIKZ);
				}
				
			}else{
				if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("4")){
					properties.put("stepNo", "5");
					properties.put("userID", MApplication.gainData(Const.USERID).toString());
					properties.put("recID", MApplication.gainData(Const.RECID).toString());
					sign(properties,Const.SIGNREADINGKESHILOOK);
				}else if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("3")){
					properties.put("hostSelectUserID", userId);
					properties.put("lookSelectUserID", hostUserIds);
					properties.put("stepNo", "5");
					properties.put("postilDesc", text);
					properties.put("userID", MApplication.gainData(Const.USERID).toString());
					properties.put("recID", MApplication.gainData(Const.RECID).toString());
					if(MApplication.gainData("signUsers") == null ||
							"".equals(MApplication.gainData("signUsers"))){
						loadingDialog.dismiss();
						ToolAlert.dialog(this, null, "您确定不选择审批人员吗？", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								loadingDialog.show();
								sign(properties,Const.SIGNREADINGKESHIQP);
							}
						},new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
					}else{
						sign(properties,Const.SIGNREADINGKESHIQP);
					}
					
				}
			}
			
		}else if(stepNo == 5){
			if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("4")){
				properties.put("stepNo", "6");
				properties.put("userID", MApplication.gainData(Const.USERID).toString());
				properties.put("recID", MApplication.gainData(Const.RECID).toString());
				sign(properties,Const.SIGNREADINGCHEJIANLOOK);
			}else if(MApplication.gainData(Const.POSTILUSERTYPE).toString().equals("3")){
				properties.put("hostSelectUserID", userId);
				properties.put("lookSelectUserID", hostUserIds);
				properties.put("stepNo", "6");
				properties.put("postilDesc", text);
				properties.put("userID", MApplication.gainData(Const.USERID).toString());
				properties.put("recID", MApplication.gainData(Const.RECID).toString());
				if(MApplication.gainData("signUsers") == null ||
						"".equals(MApplication.gainData("signUsers"))){
					loadingDialog.dismiss();
					ToolAlert.dialog(this, null, "您确定不选择审批人员吗？", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							loadingDialog.show();
							sign(properties,Const.SIGNREADINGCHEJIANZR);
						}
					},new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
				}else{
					sign(properties,Const.SIGNREADINGCHEJIANZR);
				}
			}
		}
		
	}
	
	/**
	 * 签批
	 * @param text
	 * @param userIds
	 * @param properties
	 */
	private void sign(HashMap<String, String> properties,String method) {
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, method, properties , new WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					Log.d(TAG, string);
					if("success".equals(string) || "404".equals(string)){
						ToolToast.showToast(SignActivity.this, "签阅成功");
						loadingDialog.dismiss();
						Intent intent = new Intent(SignActivity.this,SelectActivity.class);
						DocListActivity.instance.finish();
						mApplication.removeToTop();
						startActivity(intent);
					}else{
						loadingDialog.dismiss();
					}
					
				}else{
					ToolToast.showToast(SignActivity.this, "联网失败");
					loadingDialog.dismiss();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				loadingDialog.dismiss();
				ToolToast.showToast(SignActivity.this, "联网错误，请检查网络连接");
			}
		});
	}
}
