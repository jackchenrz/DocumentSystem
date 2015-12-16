package com.publicstech.documentsystem.activity.doc;

import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

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
import com.publicstech.documentsystem.bean.DocAlListBean.YBDoc;
import com.publicstech.documentsystem.bean.DocDetailBean;
import com.publicstech.documentsystem.bean.DocDetailBean.DocDetail;
import com.publicstech.documentsystem.bean.DocListBean.Doc;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolDensity;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.tools.ToolToast;

public class DocDetailActivity extends BaseActivity {

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
	
	private DocDetail docDetail;
	private List<Record> record;
	private ApproveRecordBean approveRecordBean;
	private HashMap<String, String> properties = new HashMap<String, String>();
	private String param;
	private Doc doc;
	private YBDoc ybDoc;
	private String url;
	
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
		ToolAlert.loading(this, "正在加载中...");
		Intent intent = getIntent();
		int page = intent.getIntExtra("page", 0);
		if(page == 0){
			doc = (Doc) intent.getSerializableExtra("doc");
			param = doc.RecID;
			MApplication.assignData(Const.RECID, param);
			ivGosign.setVisibility(View.VISIBLE);
			btnGosave.setVisibility(View.VISIBLE);
			
		}else if(page == 1){
			ybDoc = (YBDoc)intent.getSerializableExtra("doc");
			param =  ybDoc.RecID;
			ivGosign.setVisibility(View.GONE);
			btnGosave.setVisibility(View.GONE);
		}
	}

	@Override
	public void doBusiness(Context mContext) {
		url = MApplication.gainData(Const.SERVICE_URL).toString();
		properties.put("recID", param);
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, Const.GETDETAIL, properties , new WebServiceCallBack() {

			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					if("404".equals(string)){
						ToolToast.showToast(DocDetailActivity.this, "没有该文电详情");
					}else{
						DocDetailBean jsonBean = ToolJson.getJsonBean(string, DocDetailBean.class);
						docDetail = jsonBean.ds.get(0);
						tvTitle.setText("标        题：" + docDetail.RecTitle);
						tvType.setText("文电种类：" + docDetail.RecType);
						tvTime.setText("发文日期：" + docDetail.CreateDate);
					}
					getAutoRecord(Const.AUDITRECORD,param);
				}else{
					ToolToast.showToast(DocDetailActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
			if(result != null){
				Log.d(TAG, result);
				}
			}
		});
		
		
	}

	protected void getAutoRecord(String method,String param) {
		properties.remove("recID");
		properties.put("DocID", param);
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, method, properties , new WebServiceCallBack() {

			@Override
			public void onSucced(SoapObject result) {
				
				if(result != null){
					String string = result.getProperty(0).toString();
					Log.d(TAG, string);
					if("404".equals(string)){
						ToolAlert.closeLoading();
						ToolToast.showToast(DocDetailActivity.this, "没有该记录");
					}else{
						approveRecordBean = ToolJson.getJsonBean(string, ApproveRecordBean.class);
						record = approveRecordBean.ds;
						for (Record re : record) {
							if(re.Real_Name.equals(MApplication.gainData(Const.USERNAME))){
								MApplication.removeData(Const.POSTILUSERTYPE);
								MApplication.assignData(Const.POSTILUSERTYPE, re.PostilUserType);
							}
						}
						addTab();
						ToolAlert.closeLoading();
					}
				}else{
					ToolToast.showToast(DocDetailActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(DocDetailActivity.this, "联网错误，请检查网络连接");
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
		
		if(MApplication.gainData(Const.STEP)!= null && !"".equals(MApplication.gainData(Const.STEP).toString())){
			int step = Integer.parseInt(MApplication.gainData(Const.STEP).toString());
			String str = "";
			int stepNo = 0;
			for (Record re : record) {
				if("".equals(re.PostilType) && Integer.parseInt(re.StepNo) < step){
					str = "注意：上级领导未签批，签发办理前请确认领导意见。";
					if(step == 5 && Integer.parseInt(re.StepNo) == 4){
						str = "keshi";
					}
				}
				if(re.PostilUserID.equals(MApplication.gainData(Const.USERID).toString())){
					stepNo = Integer.parseInt(re.StepNo);
				}
			}
			
			if(stepNo == 2 || stepNo == 3 || stepNo == 4 || stepNo == 5){
				Intent intent = new Intent(this,SignActivity.class);
				intent.putExtra("str", str);
				intent.putExtra("stepNo", stepNo);
				startActivity(intent);
			}else{
				ToolToast.showToast(this, "如需签阅，请在电脑端操作");
				return;
			}
		}else{
			ToolToast.showToast(this, "如需签阅，请在电脑端操作");
			return;
		}
		
		
	}
	

}
