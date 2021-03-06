package com.publicstech.documentsystem.activity.notice;

import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.MApplication;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.base.BaseActivity;
import com.publicstech.documentsystem.bean.ApproveRecordBean;
import com.publicstech.documentsystem.bean.ApproveRecordBean.Record;
import com.publicstech.documentsystem.bean.NoticeAlListBean.YBNotice;
import com.publicstech.documentsystem.bean.NoticeDetailBean;
import com.publicstech.documentsystem.bean.NoticeDetailBean.NoticeDetail;
import com.publicstech.documentsystem.bean.NoticeListBean.Notice;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolDensity;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.tools.ToolToast;

public class NoticeDetailActivity extends BaseActivity {

	@InjectView(R.id.tv_doctitle)
	TextView tvTitle;
	@InjectView(R.id.tv_doctype)
	TextView tvType;
	@InjectView(R.id.tv_doctime)
	TextView tvTime;
	@InjectView(R.id.tab)
	TableLayout tabLayout;
	@InjectView(R.id.iv_gosign)
	ImageView ivGosign;
	@InjectView(R.id.btn_gosave)
	Button btnGosave;
	
	private NoticeDetail noticeDetail;
	private List<Record> record;
	private ApproveRecordBean approveRecordBean;
	private HashMap<String, String> properties = new HashMap<String, String>();
	private String param;
	private Notice doc;
	private YBNotice ybNotice;
	private String url;
	private Dialog loadingDialog;
	
	/**
	 * 添加审批记录
	 */
	protected void addTab() {
		for (int i = 0; i < approveRecordBean.ds.size(); i++) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new TableLayout.LayoutParams(ToolDensity.dip2px(this, 600),LayoutParams.WRAP_CONTENT));
			TextView tv = new TextView(this);
			android.widget.TableRow.LayoutParams params = new TableRow.LayoutParams(ToolDensity.dip2px(this, 100),LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER;
			tv.setLayoutParams(params);
			tv.setText(record.get(i).StepName);
			tr.addView(tv);
			
			TextView tv1 = new TextView(this);
			android.widget.TableRow.LayoutParams params1 = new TableRow.LayoutParams(ToolDensity.dip2px(this, 100),LayoutParams.WRAP_CONTENT);
			params1.gravity = Gravity.CENTER;
			tv1.setLayoutParams(params1);
			tv1.setText(record.get(i).Real_Name);	
			tr.addView(tv1);
			
			TextView tv2 = new TextView(this);
			android.widget.TableRow.LayoutParams params2 = new TableRow.LayoutParams(ToolDensity.dip2px(this, 100),LayoutParams.WRAP_CONTENT);
			params2.gravity = Gravity.CENTER;
			tv2.setLayoutParams(params2);
			tv2.setText(record.get(i).PostilType);	
			tr.addView(tv2);
			
			TextView tv3 = new TextView(this);
			android.widget.TableRow.LayoutParams params3 = new TableRow.LayoutParams(ToolDensity.dip2px(this, 100),LayoutParams.WRAP_CONTENT);
			params3.gravity = Gravity.CENTER;
			tv3.setLayoutParams(params3);
			tv3.setText(record.get(i).PostilDesc);	
			tr.addView(tv3);
			
			TextView tv4 = new TextView(this);
			android.widget.TableRow.LayoutParams params4 = new TableRow.LayoutParams(ToolDensity.dip2px(this, 100),LayoutParams.WRAP_CONTENT);
			params4.gravity = Gravity.CENTER;
			tv4.setLayoutParams(params4);
			tv4.setText(record.get(i).StartDate);	
			tr.addView(tv4);
			
			TextView tv5 = new TextView(this);
			android.widget.TableRow.LayoutParams params5 = new TableRow.LayoutParams(ToolDensity.dip2px(this, 100),LayoutParams.WRAP_CONTENT);
			params5.gravity = Gravity.CENTER;
			tv5.setLayoutParams(params5);
			tv5.setText(record.get(i).EndDate);	
			tr.addView(tv5);
			tabLayout.addView(tr);
		}
	}
	@Override
	public int bindLayout() {
		return R.layout.activity_docdetail;
	}


	@Override
	public void initView(View view) {
		ButterKnife.inject(this);
		loadingDialog = ToolAlert.createLoadingDialog(this, "正在加载中...");
		loadingDialog.show();
		Intent intent = getIntent();
		int page = intent.getIntExtra("page", 0);
		if(page == 0){
			doc = (Notice) intent.getSerializableExtra("doc");
			param = doc.DocID;
			MApplication.assignData(Const.RECID, param);
			ivGosign.setVisibility(View.VISIBLE);
			btnGosave.setVisibility(View.VISIBLE);
			
		}else if(page == 1){
			ybNotice = (YBNotice)intent.getSerializableExtra("doc");
			param =  ybNotice.RecID;
			ivGosign.setVisibility(View.GONE);
			btnGosave.setVisibility(View.GONE);
		}
	}

	@Override
	public void doBusiness(Context mContext) {
		url = MApplication.gainData(Const.SERVICE_URL).toString();
		properties.put("noticeID", param);
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, Const.GETNOTICEDETAIL, properties , new WebServiceCallBack() {

			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					Log.d(TAG, string);
					if("404".equals(string)){
						ToolToast.showToast(NoticeDetailActivity.this, "没有该通知详情");
					}else{
						NoticeDetailBean jsonBean = ToolJson.getJsonBean(string, NoticeDetailBean.class);
						noticeDetail = jsonBean.ds.get(0);
						tvTitle.setText("标        题：" + noticeDetail.NoticeTitle);
						tvType.setText("通知种类：" + noticeDetail.NoticeType);
						tvTime.setText("发文日期：" + noticeDetail.CreateDate);
					}
					getAutoRecord(Const.AUDITRECORDNOTICE,param);
				}else{
					ToolToast.showToast(NoticeDetailActivity.this, "联网失败");
					loadingDialog.dismiss();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				loadingDialog.dismiss();
			}
		});
		
		
	}

	protected void getAutoRecord(String method,String param) {
		properties.remove("recID");
		properties.put("noticeID", param);
		properties.put("userID", MApplication.gainData(Const.USERID).toString());
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, method, properties , new WebServiceCallBack() {

			@Override
			public void onSucced(SoapObject result) {
				
				if(result != null){
					String string = result.getProperty(0).toString();
					Log.d(TAG, string);
					if("404".equals(string)){
						loadingDialog.dismiss();
						ToolToast.showToast(NoticeDetailActivity.this, "没有该记录");
					}else{
						approveRecordBean = ToolJson.getJsonBean(string, ApproveRecordBean.class);
						record = approveRecordBean.ds;
						addTab();
						loadingDialog.dismiss();
					}
				}else{
					ToolToast.showToast(NoticeDetailActivity.this, "联网失败");
					loadingDialog.dismiss();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				loadingDialog.dismiss();
				ToolToast.showToast(NoticeDetailActivity.this, "联网错误，请检查网络连接");
			}
		});
	}
	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
	
	@OnClick(R.id.btn_gosave)
	public void OnGoSave(){
			
		int stepNo = 0;
		for (Record re : record) {
			if(re.PostilUserID.equals(MApplication.gainData(Const.USERID).toString())){
				stepNo = Integer.parseInt(re.StepNo);
			}
		}
		
		if(stepNo == 2 || stepNo == 3){
			Intent intent = new Intent(this,NoticeSignActivity.class);
			intent.putExtra("stepNo", stepNo);
			startActivity(intent);
		}else{
			ToolToast.showToast(this, "如需签阅，请在电脑端操作");
			return;
		}
		
		}

}
