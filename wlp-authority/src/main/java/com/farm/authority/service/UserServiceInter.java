package com.farm.authority.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.farm.authority.domain.Action;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.domain.User;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

 
public interface UserServiceInter {
	 
	public User insertUserEntity(User entity, LoginUser user, String orgId, String postIds);

	 
	public User insertUserEntity(User entity, LoginUser user);

	 
	public User insertUserEntity(String name, String loginname, String clientPassword);

	 
	public User insertUserEntity(String name, String loginname, String clientPassword, String imgid);

	 
	public User editUserEntity(User entity, LoginUser user, String orgId, String postIds,boolean PostSetNullAble);

	 
	public User editUserEntity(User entity, LoginUser user);

	 
	public void deleteUserEntity(String id, LoginUser user);

	 
	public void editUserType(String userid, String type, LoginUser user);

	 
	public User getUserEntity(String id);

	 
	public DataQuery createUserSimpleQuery(DataQuery query, LoginUser currentUser);

	 
	public boolean validateIsRepeatLoginName(String loginname, String userId);

	 
	public void initDefaultPassWord(String userid, LoginUser currentUser);

	 
	public User getUserByLoginName(String loginName);

	 
	public String setLoginTime(String userId);

	 
	public DataQuery createUserPostQuery(DataQuery query);

	 
	public List<Action> getUserActions(String userId);

	 
	public List<WebMenu> getUserMenus(String userId);

	 
	public List<String> getUserPostIds(String userId);

	 
	public List<Post> getUserPosts(String userId);

	 
	public Organization getUserOrganization(String userId);

	 
	public User registUser(User user, String orgid);

	 
	public User registUser(User user);

	 
	public Organization getOrg(String userid);

	 
	public List<Post> getPost(String userid);

	 
	public DataQuery createOrgUserQuery(DataQuery query);

	 
	public void editCurrentUser(String id, String name, String photoid, String orgid);

	 
	public void editUserPassword(String userid, String oldClientPassword, String newClientPassword);

	 
	public void editUserPassword(String userid, String newClientPassword);

	 
	public boolean editUserPasswordByLoginName(String loginname, String oldClientPassword, String newClientPassword);

	 
	public boolean validCurrentUserPwd(String userid, String clientPassword);

	 
	public Integer getUsersNum();

	 
	public void doUserImport(MultipartFile file, LoginUser currentUser);

	 
	public DataResult searchUserByUsernameAndOrgname(String word, Integer pagenum);

	 
	public void setUserOrganization(String userid, String orgid, LoginUser currentUser);

	 
	public void setUserPost(String userid, String postids, LoginUser currentUser);

	 
	public List<User> getSuperUsers();

	 
	public List<String> getSuperUserids();

	 
	public User editUserState(String userid, String state, LoginUser currentUser);

	 
	public void visitUserHomePage(String userid, LoginUser currentUser, String currentIp);

	 
	public List<String> getAllLiveUserIds();

	 
	public void addUserPost(String userid, String postids, LoginUser currentUser);

	 
	public void delUserPost(String userid, String postids, LoginUser currentUser);
}