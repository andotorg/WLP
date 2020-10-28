package com.farm.authority.service.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.domain.User;
import com.farm.core.inter.impl.AppOpratorHandle;
import com.farm.wcp.ekca.UpdateState;
import com.farm.wcp.ekca.UpdateType;

public class AuthOpratorUtils {
	 
	public static void syncUser(User user, UpdateType updateType) {
		UpdateState state = null;
		// 0:禁用,1:可用,2:删除
		if (user.getState().equals("1")) {
			state = UpdateState.getAble();
		}
		if (user.getState().equals("0")) {
			state = UpdateState.getDisabled();
		}
		if (user.getState().equals("2")) {
			state = UpdateState.getDelete();
		}
		if (user.getState().equals("3")) {
			state = UpdateState.getAuditing();
		}
		// 如果是删除操作直接将状态设置为删除
		if (updateType.getVal().equals(UpdateType.getDel(null).getVal())) {
			state = UpdateState.getDelete();
		}
		AppOpratorHandle.getInstance().updateUser(user.getId(), user.getName(), user.getType(), user.getLoginname(),
				user.getImgid(), state, updateType, null);
	}

	 
	public static void syncUserOrg(String userid, String orgid, UpdateType updateType) {
		if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(orgid)) {
			AppOpratorHandle.getInstance().updateUserOrgid(userid, orgid, updateType, null);
		}
	}

	 
	public static void syncUserPost(String userid, String postids, UpdateType updateType) {
		if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(postids)) {
			String[] postIdArr = postids.split(",");
			List<String> posts = Arrays.asList(postIdArr);
			AppOpratorHandle.getInstance().updateUserPostid(userid, posts, updateType, null);
		}
	}

	 
	public static void syncOrg(Organization org, UpdateType updateType) {
		UpdateState state = null;
		// 1可用、2删除
		if (org.getState().equals("1")) {
			state = UpdateState.getAble();
		}
		if (org.getState().equals("0")) {
			state = UpdateState.getDisabled();
		}
		if (org.getState().equals("2")) {
			state = UpdateState.getDelete();
		}
		// 如果是删除操作直接将状态设置为删除
		if (updateType.getVal().equals(UpdateType.getDel(null).getVal())) {
			state = UpdateState.getDelete();
		}
		AppOpratorHandle.getInstance().updateOrg(org.getId(), org.getTreecode(), org.getName(), org.getParentid(),
				org.getSort(), state, updateType, null);
	}

	 
	public static void syncPost(Post post, UpdateType updateType) {
		UpdateState state = null;
		// 1可用、2删除
		if (post.getPstate().equals("1")) {
			state = UpdateState.getAble();
		}
		if (post.getPstate().equals("0")) {
			state = UpdateState.getDisabled();
		}
		if (post.getPstate().equals("2")) {
			state = UpdateState.getDelete();
		}
		// 如果是删除操作直接将状态设置为删除
		if (updateType.getVal().equals(UpdateType.getDel(null).getVal())) {
			state = UpdateState.getDelete();
		}
		AppOpratorHandle.getInstance().updatePost(post.getId(), post.getName(), post.getOrganizationid(), state,
				updateType, null);
	}

}
