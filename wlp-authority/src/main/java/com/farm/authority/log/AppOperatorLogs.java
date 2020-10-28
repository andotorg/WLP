package com.farm.authority.log;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.wcp.ekca.UpdateState;
import com.farm.wcp.ekca.UpdateType;
import com.farm.wcp.api.EkcaAppInter;
import com.farm.wcp.ekca.OperateEvent;
import com.farm.web.log.WcpLog;

 
public class AppOperatorLogs implements EkcaAppInter {

	@Override
	public void updateUser(String oid, String name, String type, String loginname, String imgid,
			UpdateState domainState, UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "用户信息,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void updateUserOrgid(String oid, String orgid, UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "用户机构,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void updateUserPostid(String oid, List<String> postids, UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "(" + postids.size() + ")个用户岗位", user.getId(), user.getName());
	}

	@Override
	public void updateOrg(String oid, String treecode, String name, String parentid, int sort, UpdateState domainState,
			UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "机构信息,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void updatePost(String oid, String name, String orgid, UpdateState domainState, UpdateType updateType,
			String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "岗位信息,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void updateQuestion(String oid, String title, String typeid, String anonymous, UpdateState domainState,
			UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "问题信息,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void updateType(String oid, String name, int sort, String type, String parentid, String treecode,
			String knowshow, String fqashow, UpdateState domainState, UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "分类信息,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void updateKnow(String oid, String title, String domtype, String version, String typeid,
			UpdateState domainState, UpdateType updateType, String secret) {
		LoginUser user = getuser(updateType.getUserid());
		WcpLog.info( updateType.getTitle() + "知识信息,操作对象ID:"+oid, user.getId(), user.getName());
	}

	@Override
	public void recordOperate(OperateEvent operateEvent, String userid, String userip, String knowId, String questid,
			String versionid, String oid, String pid, String secret) {
		if (StringUtils.isBlank(userid)) {
			WcpLog.info(
					"操作:" + (oid == null ? "" : ("[操作对象ID:" + oid + "]")) + operateEvent.getOptitle()
							+ (operateEvent.getTextval1() == null ? "" : "/" + operateEvent.getTextval1())
							+ (operateEvent.getTextval2() == null ? "" : "/" + operateEvent.getTextval2())
							+ (operateEvent.getTextval3() == null ? "" : "/" + operateEvent.getTextval3()),
					userid, userid);
		} else {
			LoginUser user = getuser(userid);
			WcpLog.info(
					"操作:" + (oid == null ? "" : ("[操作对象ID:" + oid + "]")) + operateEvent.getOptitle()
							+ (operateEvent.getTextval1() == null ? "" : "/" + operateEvent.getTextval1())
							+ (operateEvent.getTextval2() == null ? "" : "/" + operateEvent.getTextval2())
							+ (operateEvent.getTextval3() == null ? "" : "/" + operateEvent.getTextval3()),
					userid, user.getName());
		}
	}

	private LoginUser getuser(final String userid) {
		LoginUser user = FarmAuthorityService.getInstance().getUserById(userid);
		if (user == null) {
			return new LoginUser() {
				@Override
				public String getType() {
					return null;
				}

				@Override
				public String getName() {
					return null;
				}

				@Override
				public String getLoginname() {
					return null;
				}

				@Override
				public String getIp() {
					return null;
				}

				@Override
				public String getId() {
					return userid;
				}
			};
		}
		return user;
	}

}
