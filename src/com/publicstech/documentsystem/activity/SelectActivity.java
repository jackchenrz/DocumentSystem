package com.publicstech.documentsystem.activity;

import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.MApplication;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.activity.doc.DocListActivity;
import com.publicstech.documentsystem.activity.notice.NoticeListActivity;
import com.publicstech.documentsystem.activity.officdoc.OfficDocListActivity;
import com.publicstech.documentsystem.base.BaseActivity;
import com.publicstech.documentsystem.bean.DocListBean;
import com.publicstech.documentsystem.bean.DocListBean.Doc;
import com.publicstech.documentsystem.bean.NoticeListBean;
import com.publicstech.documentsystem.bean.NoticeListBean.Notice;
import com.publicstech.documentsystem.bean.OfficDocListBean;
import com.publicstech.documentsystem.bean.OfficDocListBean.OfficDoc;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolToast;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.view.widget.BadgeView;

public class SelectActivity extends BaseActivity {

	@InjectView(R.id.ll_document)
	LinearLayout llDocument;
	@InjectView(R.id.iv_document)
	ImageView ivDocument;
	@InjectView(R.id.ll_officdoc)
	LinearLayout llOfficdoc;
	@InjectView(R.id.iv_officdoc)
	ImageView ivOfficdoc;
	@InjectView(R.id.ll_notice)
	LinearLayout llNotice;
	@InjectView(R.id.iv_notice)
	ImageView ivNotice;
	
	private BadgeView mBadgeViewForChat;
	private DocListBean docListBean;
	private OfficDocListBean officdocListBean;
	private NoticeListBean noticeListBean;
	private List<Doc> docList;
	private List<OfficDoc> officdocList;
	private List<Notice> noticeList;
	private HashMap<String, String> properties = new HashMap<String, String>();
	
	@Override
	public int bindLayout() {
		return R.layout.activity_select;
	}

	@Override
	public void initView(View view) {
		ButterKnife.inject(this);
		ToolAlert.loading(this, "正在加载中...");
	}
	
	@OnClick(R.id.ll_document)
	public void onDocument(){
		if(mBadgeViewForChat != null){
			mBadgeViewForChat.setVisibility(View.GONE);
		}
		Intent intent = new Intent(SelectActivity.this,DocListActivity.class);
		intent.putExtra("docList", docListBean);
		startActivity(intent);
	}
	@OnClick(R.id.ll_officdoc)
	public void onOfficdoc(){
		if(mBadgeViewForChat != null){
			mBadgeViewForChat.setVisibility(View.GONE);
		}
		Intent intent = new Intent(SelectActivity.this,OfficDocListActivity.class);
		intent.putExtra("officdocList", officdocListBean);
		startActivity(intent);
	}
	@OnClick(R.id.ll_notice)
	public void onNotice(){
		if(mBadgeViewForChat != null){
			mBadgeViewForChat.setVisibility(View.GONE);
		}
		Intent intent = new Intent(SelectActivity.this,NoticeListActivity.class);
		intent.putExtra("noticeList", noticeListBean);
		startActivity(intent);
	}

	@Override
	public void doBusiness(Context mContext) {
		properties.put("userID", MApplication.gainData(Const.USERID).toString());
		ToolSOAP.callService(Const.SERVICE_URL, Const.SERVICE_NAMESPACE, Const.GETLIST, properties, new WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					if("404".equals(string)){
						docListBean = null;
					}else{
						docListBean = ToolJson.getJsonBean(string, DocListBean.class);
						docList = docListBean.ds;
					}
					//添加红色数字提示
					if (llDocument != null) {
						llDocument.removeView(mBadgeViewForChat);
					}
					mBadgeViewForChat = new BadgeView(SelectActivity.this);
					mBadgeViewForChat.setVisibility(View.VISIBLE);
					if(docList != null){
						mBadgeViewForChat.setBadgeCount(docList.size());
					}
					mBadgeViewForChat.setTargetView(ivDocument);
					getDocList();
				}else{
					ToolToast.showToast(SelectActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(SelectActivity.this, "联网错误，请检查网络连接");
			}
		});
	}

	protected void getDocList() {
		ToolSOAP.callService(Const.SERVICE_URL, Const.SERVICE_NAMESPACE, Const.GETDOCLIST, properties, new WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					if("404".equals(string)){
						officdocListBean = null;
					}else{
						officdocListBean = ToolJson.getJsonBean(string, OfficDocListBean.class);
						officdocList = officdocListBean.ds;
					}
					//添加红色数字提示
					if (llOfficdoc != null) {
						llOfficdoc.removeView(mBadgeViewForChat);
					}
					mBadgeViewForChat = new BadgeView(SelectActivity.this);
					mBadgeViewForChat.setVisibility(View.VISIBLE);
					if(officdocList != null){
						mBadgeViewForChat.setBadgeCount(officdocList.size());
					}
					mBadgeViewForChat.setTargetView(ivOfficdoc);
					
					getNoticeList();
				}else{
					ToolToast.showToast(SelectActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(SelectActivity.this, "联网错误，请检查网络连接");
			}
		});
	}

	protected void getNoticeList() {
		ToolSOAP.callService(Const.SERVICE_URL, Const.SERVICE_NAMESPACE, Const.GETNOTICELIST, properties, new WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					String string = result.getProperty(0).toString();
					if("404".equals(string)){
						noticeListBean = null;
					}else{
						noticeListBean = ToolJson.getJsonBean(string, NoticeListBean.class);
						noticeList = noticeListBean.ds;
					}
					//添加红色数字提示
					if (llNotice != null) {
						llNotice.removeView(mBadgeViewForChat);
					}
					mBadgeViewForChat = new BadgeView(SelectActivity.this);
					mBadgeViewForChat.setVisibility(View.VISIBLE);
					if(noticeList != null){
						mBadgeViewForChat.setBadgeCount(noticeList.size());
					}
					mBadgeViewForChat.setTargetView(ivNotice);
					ToolAlert.closeLoading();
					
				}else{
					ToolToast.showToast(SelectActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
					Log.d(TAG, result);
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(SelectActivity.this, "联网错误，请检查网络连接");
			}
		});
	}

	@Override
	public void resume() {
		ToolAlert.loading(this, "正在加载中...");
		doBusiness(this);
	}

	@Override
	public void destroy() {

	}

}
