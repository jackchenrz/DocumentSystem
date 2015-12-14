package com.publicstech.documentsystem.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = initView();
		return view;
	}
	public abstract View initView();

	public abstract void initData();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		initData();
//	}
	public void setValue(int page, Object obj) {
		// TODO Auto-generated method stub
		
	}
}
