package com.farm.authority.service;

import java.util.List;
import java.util.Map;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.easyui.EasyUiTreeNode;

 
public interface OrganizationServiceInter {
	 
	public Organization insertOrganizationEntity(Organization entity, LoginUser user);

	 
	public Organization editOrganizationEntity(Organization entity, LoginUser user);

	 
	public void deleteOrganizationEntity(String id, LoginUser user);

	 
	public Organization getOrganizationEntity(String id);

	 
	public DataQuery createOrganizationSimpleQuery(DataQuery query);

	 
	public Post insertPost(String orgId, String postname, String extendis, LoginUser user);

	 
	public Post editPost(String postId, String postname, String extendis, LoginUser user);

	 
	public List<Organization> getParentOrgs(String orgid);

	 
	public void deletePostEntity(String id, LoginUser user);

	 
	public Post getPostEntity(String id);

	 
	public DataQuery createPostSimpleQuery(DataQuery query);

	 
	public void moveOrgTreeNode(String orgId, String targetOrgId, LoginUser currentUser);

	 
	public List<EasyUiTreeNode> loadPostTree(String ids);

	 
	public void addUserPost(String userId, String postId, LoginUser currentUser);

	 
	public void removePostUsers(String postId, String userid, LoginUser currentUser);

	 
	public void setPostActionTree(List<String> actionTreeIds, String postId);

	 
	public List<Organization> getTree();

	 
	public List<Map<String, Object>> getPostList(String orgId);

	 
	public List<Map<String, Object>> getPostListWithPOrgPost(String orgId);

	 
	public List<String> getOrgUsers(String orgid);

	 
	public List<String> getPostUser(String postid);

	 
	public List<Organization> getList();

	 
	public Organization getOrganizationByAppid(String appid);

	 
	public void removeOrgUsers(String orgid, String userids, LoginUser currentUser);

}