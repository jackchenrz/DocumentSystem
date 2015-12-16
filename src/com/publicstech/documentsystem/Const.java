package com.publicstech.documentsystem;

/**
 * 一些系统常量的定义
 * 
 */
public class Const {

	/**
	 * webService 相关
	 */
//	public static final String SERVICE_URL = "http://192.168.1.50:7021/RecSerApp.asmx";
	public static final String SERVICE_URL = "service_url";
	public static final String SERVICE_NAMESPACE = "http://tempuri.org/";
	public static final String LOCAL = "la";
	public static final String LOCALPWD = "111111";
	public static final String SERVICE_IP = "ip";
	public static final String SERVICE_PORT = "port";
	
	
	//上级文电相关
	public static final String LOGIN = "Login";// 登陆
	public static final String GETLIST = "GetList";// 得到待办上级文电列表
	public static final String GETYBRECSHOUWENLIST = "GetYbRecShouWenList";// 得到已办上级文电列表
	public static final String GETDETAIL = "GetDetail";// 得到待办上级文电详情
	public static final String AUDITRECORD = "AuditRecord";// 得到审批记录
	public static final String SELECTUSER = "SelectUser";// 选择人员
	public static final String SELECTUSER2 = "SelectUser2";// 选择人员
	public static final String SIGNREADINGDUANZHANG = "SignReadingDuanZhang";// 段长签阅
	public static final String SIGNREADINGFUDUAN = "SignReadingFuDuan";// 副段长签阅
	public static final String SIGNREADINGKESHIKZ = "SignReadingKeShiKZ";// 科长签阅
	public static final String SIGNREADINGKESHILOOK = "SignReadingKeShiLook";// 科室查看
	public static final String SIGNREADINGKESHIQP = "SignReadingKeShiQP";// 科室签阅
	public static final String SIGNREADINGCHEJIANZR = "SignReadingCheJianZR";// 车间签阅
	public static final String SIGNREADINGCHEJIANLOOK = "SignReadingCheJianLook";// 车间查看
	
	//段发公文相关
	public static final String GETDOCLIST = "GetDocList";// 得到待办段发公文列表
	public static final String GETDOCLISTFA = "GetDocListFa";// 得到待办段发公文发文列表
	public static final String GETYBDOCSHOUWENLIST = "GetYbDocShouWenList";// 得到已办段发公文列表
	public static final String GETDOCDETAIL = "GetDocDetail";// 得到段发公文详情
	public static final String AUDITRECORDDOC = "AuditRecordDoc";// 得到审批记录
	public static final String SENDDOCSIGNREADINGKEZHANGAUDIT = "SendDocSignReadingKeZhangAudit";// 科长签阅
	public static final String SENDDOCSIGNREADINGMAINLEADERAUDIT = "SendDocSignReadingMainLeaderAudit";// 主管领导签阅
	public static final String SENDDOCSIGNREADINGCHUANAUDIT = "SendDocSignReadingChuanAudit";// 相关人员传阅
	public static final String SELECTUSER3 = "SelectUser3";// 人员选择
	
	//生产安全通知相关
	public static final String GETNOTICELIST = "GetNoticeList";// 得到待办安全通知列表
	public static final String GETNOTICEDETAIL = "GetNoticeDetail";// 得到待办安全通知列表
	public static final String GETYBNOTICESHOUWENLIST = "GetYbNoticeShouWenList";// 得到已办安全通知列表
	public static final String AUDITRECORDNOTICE = "AuditRecordNotice";// 得到已办安全通知列表
	public static final String NOTICESIGNREADINGKEZHANGAUDIT = "NoticeSignReadingKeZhangAudit";// 得到已办安全通知列表
	public static final String NOTICESIGNREADINGCHUANAUDIT = "NoticeSignReadingChuanAudit";// 得到已办安全通知列表
	
	//用户相关
	public static final String USERID = "user_id";// 用户ID
	public static final String USERNAME = "userName";// 用户角色
	public static final String PWD = "pwd";// 用户角色
	public static final String POSTILUSERTYPE = "PostilUserType";//签阅用户：3，查看用户：4
	public static final String TOKEN = "token";// 用户角色
	public static final String RECID = "recID";// 文档ID
	public static final String STEP = "Step";// 用户层级
	
	public static boolean net_error_try = false;
	public static boolean auto_inject = true;
	public static int DATABASE_VERSION = 1;

	// adapter 的分页相关
	public static String netadapter_page_no = "page";
	public static String netadapter_step = "step";
	public static Integer netadapter_step_default = 20;
	public static String netadapter_timeline = "timeline";
	public static String netadapter_json_timeline = "id";
	public static String netadapter_no_more = "已经没有了";

	public static String[] ioc_instal_pkg = null;

	// 网络访问返回数据的格式定义
	public static String response_success = "success";
	public static String response_msg = "msg";
	public static String response_data = "data";
	public static String response_total = "total";
	public static String response_current = "current";
	public static String response_code = "code";
	public static int net_pool_size = 10;

}
