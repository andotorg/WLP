package com.farm.wcp.ekca;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.farm.wcp.ekca.PointEventHandle.POINT_HANDLE;

 
public class OperateEvent implements Serializable {
	public static String TABLE_EKCA_OP_KNOW_FQA = "EKCA_OP_KNOW_FQA";
	public static String TABLE_EKCA_OP_VISITE = "EKCA_OP_VISITE";
	public static String TABLE_EKCA_OP_LOGIN = "EKCA_OP_LOGIN";
	public static String TABLE_EKCA_OP_SEARCH = "EKCA_OP_SEARCH";
	public static String TABLE_EKCA_OP_VISITETYPE = "EKCA_OP_VISITETYPE";
	private String optype;
	private String tableName;
	private String optime;
	private String optitle;
	private String textval1;
	private String textval2;
	private String textval3;
	private int intval1;
	private int intval2;
	private int intval3;
	private float floatval1;
	private float floatval2;
	private float floatval3;
	private static final long serialVersionUID = -2972587467780861618L;

	private OperateEvent() {
	}

	public OperateEvent(String optype, String tableName, String optime, String optitle, String textval1,
			String textval2, String textval3, int intval1, int intval2, int intval3, float floatval1, float floatval2,
			float floatval3) {
		this.optype = optype;
		this.tableName = tableName;
		this.optime = optime;
		this.optitle = optitle;
		this.textval1 = textval1;
		this.textval2 = textval2;
		this.textval3 = textval3;
		this.intval1 = intval1;
		this.intval2 = intval2;
		this.intval3 = intval3;
		this.floatval1 = floatval1;
		this.floatval2 = floatval2;
		this.floatval3 = floatval3;
	}

	 
	public enum EVENT_TYPE {
		CREAT_KNOW("01", "创建知识", TABLE_EKCA_OP_KNOW_FQA, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		CREAT_FQA("02", "创建问答", TABLE_EKCA_OP_KNOW_FQA, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		EDIT_KNOW("03", "修改知识", TABLE_EKCA_OP_KNOW_FQA, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		EDIT_FQA("04", "修改问答", TABLE_EKCA_OP_KNOW_FQA, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		DEL_KNOW("05", "刪除知识", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		DEL_FQA("06", "刪除问答", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		AuditY_doc("07", "知识审核通过", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		AuditN_doc("08", "知识审核未通过", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		FOLLOW_KNOW("21", "关注知识", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		FOLLOW_FQA("22", "关注问答", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		EVALUATE_KNOW("23", "评价知识", TABLE_EKCA_OP_KNOW_FQA, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		COMMENT_KNOW("24", "评论知识", TABLE_EKCA_OP_KNOW_FQA, true, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		COMMENT_FQA("25", "评论问答", TABLE_EKCA_OP_KNOW_FQA, true, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		SEND_KNOW("26", "知识推送", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		SEND_FQA("27", "问答推送", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		FOLLOW_TYPE("28", "关注分类", TABLE_EKCA_OP_KNOW_FQA, false, false,PointEventHandle.getInctance().asList()),
		// break
		ESSENCE_KNOW("29", "设置精华知识", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		UNESSENCE_KNOW("30", "取消精华知识", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		ANSWER_FQA("31", "回答问题", TABLE_EKCA_OP_KNOW_FQA, true, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		CLOSELY_FQA("32", "追加提问", TABLE_EKCA_OP_KNOW_FQA, false, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		CHOOSE_FQA("34", "选择最佳答案", TABLE_EKCA_OP_KNOW_FQA, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		EVALUATE_FQA("35", "评价回答", TABLE_EKCA_OP_KNOW_FQA, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		DEA_ANSWER("36", "删除回答", TABLE_EKCA_OP_KNOW_FQA, true, false, 	PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		LOGIN("11", "登录", TABLE_EKCA_OP_LOGIN, false, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByDay).asList()),
		// break
		SEARCH("12", "全文检索", TABLE_EKCA_OP_SEARCH, false, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).asList()),
		// break
		VISIT_GROUP("41", "小组访问", TABLE_EKCA_OP_VISITE, true, true, 	PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		VISIT_SPECIAL("42", "专题访问", TABLE_EKCA_OP_VISITE, true, true, 	PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		VISIT_FILE("48", "访问附件", TABLE_EKCA_OP_VISITE, false, false, PointEventHandle.getInctance().add(POINT_HANDLE.operatorByObj).asList()),
		// break
		VISIT_TYPE("49", "访问分类", TABLE_EKCA_OP_VISITETYPE, false, false, 	PointEventHandle.getInctance().asList()),
		// break
		VISIT_KNOW("43", "知识访问", TABLE_EKCA_OP_VISITE, true, true, 	PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		VISIT_FQA("44", "问答访问", TABLE_EKCA_OP_VISITE, true, true, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		VISIT_USER("45", "用户主页访问", TABLE_EKCA_OP_VISITE, false, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		DOWN("46", "下载附件", TABLE_EKCA_OP_VISITE, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		VIEW("47", "预览附件", TABLE_EKCA_OP_VISITE, true, false, PointEventHandle.getInctance().add(POINT_HANDLE.defaultAll).add(POINT_HANDLE.operatorByObj).asList()),
		// break
		SETING_POINT("51", "设置积分", TABLE_EKCA_OP_VISITE, false, false, PointEventHandle.getInctance().asList()),
		// break
		PAY_POINT("52", "消费积分", TABLE_EKCA_OP_KNOW_FQA, false, false, PointEventHandle.getInctance().asList()),
		// break
		HARVEST_POINT("53", "获得积分", TABLE_EKCA_OP_KNOW_FQA, false, false, PointEventHandle.getInctance().asList());
		
		public String key;
		public String title;
		public String tableName;
		// 是否有所属人积分
		public boolean isOwnerPoint;
		// 是否有操作人积分
		public boolean isOperatorPoint;
		 
		public List<POINT_HANDLE> pointEvents;

		 
		private EVENT_TYPE(String key, String title, String tableName, boolean isOwnerPoint, boolean isOperatorPoint,
				List<POINT_HANDLE> pointEvents) {
			this.key = key;
			this.title = title;
			this.tableName = tableName;
			this.isOwnerPoint = isOwnerPoint;
			this.isOperatorPoint = isOperatorPoint;
			this.pointEvents = pointEvents;
		}

		public String getTitle() {
			return title;
		}

		 
		public static String getTitle(String key) {
			for (EVENT_TYPE node : EVENT_TYPE.values()) {
				if (key.equals(node.key)) {
					return node.title;
				}
			}
			return key;
		}

		public static EVENT_TYPE[] getEventTypes() {
			return EVENT_TYPE.values();
		}

		public static EVENT_TYPE getEvent(String key) {
			for (EVENT_TYPE node : EVENT_TYPE.values()) {
				if (key.equals(node.key)) {
					return node;
				}
			}
			throw new RuntimeException("is not exist Event by the key");
		}

		public String getKey() {
			return key;
		}

		public String getTableName() {
			return tableName;
		}

		public boolean isOwnerPoint() {
			return isOwnerPoint;
		}

		public boolean isOperatorPoint() {
			return isOperatorPoint;
		}

		public List<POINT_HANDLE> getPointEvents() {
			return pointEvents;
		}
	}

	 
	public static String getEventTable(String eventKey) {
		return EVENT_TYPE.getEvent(eventKey).tableName;
	}

	 
	public static EVENT_TYPE[] getEventTypes() {
		return EVENT_TYPE.getEventTypes();
	}

	 
	public static String getEventTitle(String val) {
		return EVENT_TYPE.getTitle(val);
	}

	// ---------------------------------------------------------------------------
	 
	public static OperateEvent getSetingPointEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.SETING_POINT.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getCreateKnowEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.CREAT_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getCreateFqaEvent(Integer point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.CREAT_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.intval1 = point;
		return obj;
	}

	 
	public static OperateEvent getEditKnowEvent(String note) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.EDIT_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = note;
		return obj;
	}

	 
	public static OperateEvent getEditFqaEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.EDIT_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getDelKnowEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.DEL_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getDelFqaEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.DEL_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getAudtYesEvent(Integer point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.AuditY_doc.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		if(point!=null){
			obj.intval1=point;
		}
		return obj;
	}

	 
	public static OperateEvent getAudtNoEvent(Integer point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.AuditN_doc.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		if(point!=null){
			obj.intval1=point;
		}
		return obj;
	}

	 
	public static OperateEvent getFollowDocEvent(String doctitle) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.FOLLOW_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = doctitle;
		return obj;
	}

	 
	public static OperateEvent getFollowTypeEvent(String typeTitle) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.FOLLOW_TYPE.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = typeTitle;
		return obj;
	}

	 
	public static OperateEvent getFollowFqaEvent(String title) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.FOLLOW_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = title;
		return obj;
	}

	 
	public static OperateEvent getEvaluateDocEvent(int point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.EVALUATE_KNOW.key;
		obj.textval1=point>0?"好评":"差评";
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.intval1 = point;
		return obj;
	}

	 
	public static OperateEvent getCommentDocEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.COMMENT_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}
	
	 
	public static OperateEvent getEssenceEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.ESSENCE_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}
	 
	public static OperateEvent getUnEssenceEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.UNESSENCE_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getCommentFqaEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.COMMENT_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getSendDocEvent(String title) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.SEND_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = title;
		return obj;
	}

	 
	public static OperateEvent getSendFqaEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.SEND_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getAnswerEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.ANSWER_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getQuestionCloselyEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.CLOSELY_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}
	// 33:追加回答(暂无)

	 
	public static OperateEvent getChooseAnswerEvent(Integer point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.CHOOSE_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.intval1 = point;
		return obj;
	}

	 
	public static OperateEvent getEvaluateFqaEvent(int point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.EVALUATE_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.intval1 = point;
		return obj;
	}

	 
	public static OperateEvent getDelAnswerEvent() {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.DEA_ANSWER.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	//
	// 
	 
	public static OperateEvent getUserLoginEvent(String loginMessage) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.LOGIN.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = loginMessage;
		return obj;
	}

	// 
	 
	public static OperateEvent getWordSearchEvent(String word, String domtype, String searchtype) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.SEARCH.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = word;
		// 文档类型 :fql问答，know知识(1.文档知识，5资源知识,6资源URL)，img图片
		obj.textval2 = domtype;
		// TYPE,TAG,AUTHOR,TITLE,
		obj.textval3 = searchtype;
		return obj;
	}

	// 访问操作EKCA_OP_VISITE
	 
	public static OperateEvent getVisitDocGroupEvent(String groupTitle) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_GROUP.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = groupTitle;
		return obj;
	}

	 
	public static OperateEvent getVisitSpecialEvent(String specialTitle) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_SPECIAL.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = specialTitle;
		return obj;
	}

	 
	public static OperateEvent getVisitDocKnowEvent(String title) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_KNOW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = title;
		return obj;
	}

	 
	public static OperateEvent getVisitFqaEvent(String title) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_FQA.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = title;
		return obj;
	}

	 
	public static OperateEvent getVisitUserHomeEvent(String userName) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_USER.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = userName;
		return obj;
	}

	 
	public static OperateEvent getDownloadDocFileEvent(String filetitle) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.DOWN.key;
		obj.textval1 = filetitle;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		return obj;
	}

	 
	public static OperateEvent getViewDocFileEvent(String fileName) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VIEW.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = fileName;
		return obj;
	}

	 
	public static OperateEvent getVisitFileEvent(String fileName) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_FILE.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = fileName;
		return obj;
	}

	 
	public static OperateEvent getVisitTypeEvent(String typeName, String parentName) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.VISIT_TYPE.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = typeName;
		obj.textval2 = parentName;
		return obj;
	}
	 
	public static OperateEvent getPayPointEvent(String eventname,int point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.PAY_POINT.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = eventname;
		obj.intval1=point;
		return obj;
	}
	
	
	 
	public static OperateEvent getHarvestPointEvent(String eventname,int point) {
		OperateEvent obj = new OperateEvent();
		obj.optype = EVENT_TYPE.HARVEST_POINT.key;
		obj.tableName = getEventTable(obj.optype);
		obj.optime = getTimeDate14();
		obj.optitle = getEventTitle(obj.optype);
		obj.textval1 = eventname;
		obj.intval1=point;
		return obj;
	}
	public String getOptype() {
		return optype;
	}

	public String getOptime() {
		return optime;
	}

	public String getTextval1() {
		return textval1;
	}

	public String getTextval2() {
		return textval2;
	}

	public String getTextval3() {
		return textval3;
	}

	public int getIntval1() {
		return intval1;
	}

	public int getIntval2() {
		return intval2;
	}

	public int getIntval3() {
		return intval3;
	}

	public float getFloatval1() {
		return floatval1;
	}

	public float getFloatval2() {
		return floatval2;
	}

	public float getFloatval3() {
		return floatval3;
	}

	public String getOptitle() {
		return optitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public static String getTimeDate14() {
		 
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date _date = new Date();

		return _sdf.format(_date);
	}

	public static List<String> getTables() {
		List<String> tables=new ArrayList<>();
		tables.add(TABLE_EKCA_OP_KNOW_FQA);
		tables.add(TABLE_EKCA_OP_VISITE);
		tables.add(TABLE_EKCA_OP_LOGIN);
		tables.add(TABLE_EKCA_OP_SEARCH);
		tables.add(TABLE_EKCA_OP_VISITETYPE);
		return tables;
	}
}
