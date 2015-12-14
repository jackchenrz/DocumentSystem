package com.publicstech.documentsystem.bean;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DocAlListBean implements Serializable{
	public List<YBDoc> ds;
	public class YBDoc implements Serializable{
		public String CreateDate;
		public String CurrentStepName;
		public String FileCode;
		public String FileDZ;
		public String RecID;
		public String RecTitle;
		public String SendDept;
	}
}
