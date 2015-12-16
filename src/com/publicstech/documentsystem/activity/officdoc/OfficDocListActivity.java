package com.publicstech.documentsystem.activity.officdoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.MApplication;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.bean.OfficDocAlListBean;
import com.publicstech.documentsystem.bean.OfficDocListBean;
import com.publicstech.documentsystem.fragment.OfficDocListFragment;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.tools.ToolThread;
import com.publicstech.documentsystem.tools.ToolToast;

public class OfficDocListActivity extends FragmentActivity {
	@InjectView(R.id.viewpager)
	ViewPager pager;
	@InjectView(R.id.tabstrip)
	PagerTabStrip tabStrip;
	@InjectView(R.id.ll_loading)
	LinearLayout llLoading;
	@InjectView(R.id.progressBar)
	ProgressBar progressBar;
	@InjectView(R.id.tv_desc_loading)
	TextView tv_desc_loading;

	public static OfficDocListActivity instance = null; 
	private String string;
	private MyAdapter adapter;
	private OfficDocListBean officDocListBean;
	private OfficDocAlListBean officDocListBeanal;
	List<Fragment> viewContainter = new ArrayList<Fragment>();
	ArrayList<String> titleContainer = new ArrayList<String>();
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (officDocListBean == null && officDocListBeanal != null) {
				fragment = (OfficDocListFragment) viewContainter.get(1);
				fragment.setValue(1, officDocListBeanal);
				progressBar.setVisibility(View.GONE);
				tv_desc_loading.setText("没有待办公文");
			}else if(officDocListBeanal == null && officDocListBean != null){
				fragment = (OfficDocListFragment) viewContainter.get(0);
				fragment.setValue(0, officDocListBean);
				llLoading.setVisibility(View.GONE);
				
			}else if(officDocListBeanal != null && officDocListBean != null){
				fragment = (OfficDocListFragment) viewContainter.get(0);
				fragment.setValue(0, officDocListBean);
				fragment = (OfficDocListFragment) viewContainter.get(1);
				fragment.setValue(1, officDocListBeanal);
				llLoading.setVisibility(View.GONE);
				
			}else{
				fragment = (OfficDocListFragment) viewContainter.get(0);
				progressBar.setVisibility(View.GONE);
				tv_desc_loading.setText("没有待办公文");
				return;
			}
		};
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_doc);
		ButterKnife.inject(this);
		instance = this;
		Intent intent = getIntent();
		officDocListBean = (OfficDocListBean) intent.getSerializableExtra("officdocList");
		llLoading.setVisibility(View.VISIBLE);
		doBusiness();
	}

	private OfficDocListFragment fragment;
	private String url;

	private void doBusiness() {
		url = MApplication.gainData(Const.SERVICE_URL).toString();
		// 取消tab下面的长横线
		tabStrip.setDrawFullUnderline(false);
		// 设置tab的背景色
		tabStrip.setBackgroundColor(this.getResources().getColor(R.color.white));
		// 设置当前tab页签的下划线颜色
		tabStrip.setTabIndicatorColor(this.getResources().getColor(R.color.red));
		tabStrip.setTextSpacing(200);
		titleContainer.add("待办公文");
		titleContainer.add("已办公文");
		viewContainter.add(new OfficDocListFragment());
		viewContainter.add(new OfficDocListFragment());
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("userID", MApplication.gainData(Const.USERID).toString());
		ToolSOAP.callService(url, Const.SERVICE_NAMESPACE, Const.GETYBDOCSHOUWENLIST, properties, new WebServiceCallBack() {

			@Override
			public void onSucced(SoapObject result) {
				if(result != null){
					string = result.getProperty(0).toString();
					Log.d("ckj", string);
					if("404".equals(string)){
						officDocListBeanal = null;
					}else{
						officDocListBeanal = ToolJson.getJsonBean(string, OfficDocAlListBean.class);
					}
					ToolThread.runInBackground(new Runnable() {
						
						@Override
						public void run() {
							SystemClock.sleep(2000);
							handler.sendEmptyMessage(100);
						}
					});
					
				}else{
					ToolToast.showToast(OfficDocListActivity.this, "联网失败");
					ToolAlert.closeLoading();
				}
			}
			
			@Override
			public void onFailure(String result) {
				if(result != null){
				}
				ToolAlert.closeLoading();
				ToolToast.showToast(OfficDocListActivity.this, "联网错误，请检查网络连接");
			}
		});
		
		adapter = new MyAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				if ("待办公文".equals(titleContainer.get(arg0))) {
					if (officDocListBean == null ) {
						llLoading.setVisibility(View.VISIBLE);
						progressBar.setVisibility(View.GONE);
						tv_desc_loading.setText("没有待办公文");
					}else{
						llLoading.setVisibility(View.GONE);
					}
				} else if ("已办公文".equals(titleContainer.get(arg0))) {
					if (officDocListBeanal == null) {
						llLoading.setVisibility(View.VISIBLE);
						progressBar.setVisibility(View.GONE);
						tv_desc_loading.setText("没有已办公文");
					}else{
						llLoading.setVisibility(View.GONE);
					}
				}

			}
		});
	}

	private class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titleContainer.get(position);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = viewContainter.get(arg0);
			return fragment;
		}

		@Override
		public int getCount() {
			return viewContainter.size();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
