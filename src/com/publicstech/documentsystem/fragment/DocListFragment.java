package com.publicstech.documentsystem.fragment;

import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.publicstech.documentsystem.Const;
import com.publicstech.documentsystem.MApplication;
import com.publicstech.documentsystem.R;
import com.publicstech.documentsystem.activity.doc.DocDetailActivity;
import com.publicstech.documentsystem.adapter.CommonAdapter;
import com.publicstech.documentsystem.base.BaseFragment;
import com.publicstech.documentsystem.bean.DocAlListBean;
import com.publicstech.documentsystem.bean.DocAlListBean.YBDoc;
import com.publicstech.documentsystem.bean.DocListBean;
import com.publicstech.documentsystem.bean.DocListBean.Doc;
import com.publicstech.documentsystem.tools.ToolAlert;
import com.publicstech.documentsystem.tools.ToolJson;
import com.publicstech.documentsystem.tools.ToolSOAP;
import com.publicstech.documentsystem.tools.ToolToast;
import com.publicstech.documentsystem.tools.ToolSOAP.WebServiceCallBack;
import com.publicstech.documentsystem.view.AutoListView;
import com.publicstech.documentsystem.view.AutoListView.OnRefreshListener;

public class DocListFragment extends BaseFragment {

	@InjectView(R.id.lv_autodoclist)
	AutoListView lvAutodoclist;
	private List<Doc> docList;
	private List<YBDoc> docListal;
	private CommonAdapter<Doc> mAdapter;
	private CommonAdapter<YBDoc> mAdapteral;
	private DocListBean docListBean;
	private DocAlListBean docListBeanal;
	private int page;
	private String method;
	public View initView() {
		
		View view = View.inflate(getActivity(),R.layout.fragment_common, null);
		ButterKnife.inject(this, view);
		lvAutodoclist.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				HashMap<String, String> properties = new HashMap<String, String>();
				properties.put("userID", MApplication.gainData(Const.USERID).toString());
				ToolSOAP.callService(Const.SERVICE_URL, Const.SERVICE_NAMESPACE, method, properties, new WebServiceCallBack() {
					
					@Override
					public void onSucced(SoapObject result) {
						if(result != null){
							String string = result.getProperty(0).toString();
							if("404".equals(string)){
							}else{
								
								if(page == 0){
									DocListBean docListBean = ToolJson.getJsonBean(string, DocListBean.class);
									docList = docListBean.ds;
									mAdapter.notifyDataSetChanged();
								}else if(page == 1){
									docListBeanal = ToolJson.getJsonBean(string, DocAlListBean.class);
									docListal = docListBeanal.ds;
									mAdapteral.notifyDataSetChanged();
								}
								lvAutodoclist.onRefreshComplete();
							}
							
						}else{
							ToolToast.showToast(getActivity(), "联网失败");
							ToolAlert.closeLoading();
						}
					}
					
					@Override
					public void onFailure(String result) {
						if(result != null){
//							Log.d(TAG, result);
						}
						ToolToast.showToast(getActivity(), "联网错误，请检查网络连接");
					}
				});
			}
		});
		return view;
	}

	@Override
	public void initData() {
		
	}
	 static class ViewHolder {
			@InjectView(R.id.tv_doc)
			TextView tvDoc;
			public ViewHolder(View view) {
				ButterKnife.inject(this, view);
			}
		}

	@Override
	/**
	 * activity传递数据给fragment
	 */
	public void setValue(int page,Object obj) {
		this.page = page;
		if(page == 0){
			this.method = Const.GETLIST;
			docListBean = (DocListBean)obj;
			docList = docListBean.ds;
			mAdapter = new CommonAdapter<Doc>(docList) {
				
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					View view;
					ViewHolder vh;
					if(convertView == null){
						view = getActivity().getLayoutInflater().inflate(R.layout.listview_item, parent,false);
						vh = new ViewHolder(view);
						view.setTag(vh);
					}else{
						view = convertView;
						vh = (ViewHolder) view.getTag();
					}
					vh.tvDoc.setText(docList.get(position).FileTitle);
					return view;
				}
			}; 
			lvAutodoclist.setAdapter(mAdapter);
			lvAutodoclist.setResultSize(docList.size());
			clickItem(docList);
		}else if(page == 1){
			this.method = Const.GETYBRECSHOUWENLIST;
			docListBeanal = (DocAlListBean)obj;
			docListal = docListBeanal.ds;
			mAdapteral = new CommonAdapter<YBDoc>(docListal) {
				
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					View view;
					ViewHolder vh;
					if(convertView == null){
						view = getActivity().getLayoutInflater().inflate(R.layout.listview_item, parent,false);
						vh = new ViewHolder(view);
						view.setTag(vh);
					}else{
						view = convertView;
						vh = (ViewHolder) view.getTag();
					}
					vh.tvDoc.setText(docListal.get(position).RecTitle);
					return view;
				}
			}; 
			lvAutodoclist.setAdapter(mAdapteral);
			lvAutodoclist.setResultSize(docListal.size());
			clickItem(docListal);
		}
	}
	
	/**
	 * 条目点击事件注册
	 * @param list
	 */
	private void clickItem(final List list){
		lvAutodoclist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == list.size() + 1){
					return;
				}
				if(page == 0){
					Doc doc = (Doc) list.get(position - 1);
					Intent intent = new Intent(getActivity(),DocDetailActivity.class);
					intent.putExtra("doc", doc);
					intent.putExtra("page", page);
					startActivity(intent);
				}else if(page == 1){
					YBDoc doc = (YBDoc) list.get(position - 1);
					Intent intent = new Intent(getActivity(),DocDetailActivity.class);
					intent.putExtra("doc", doc);
					intent.putExtra("page", page);
					startActivity(intent);
				}
			}
		});
	}


}
