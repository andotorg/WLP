package com.farm.core.inter.impl;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import com.farm.core.inter.BusinessHandleInter;
import com.farm.core.inter.domain.BusinessHandler;
import com.farm.wcp.ekca.UpdateState;
import com.farm.wcp.ekca.UpdateType;
import com.farm.wcp.api.EkcaAppInter;
import com.farm.wcp.ekca.OperateEvent;

 
public class AppOpratorHandle extends BusinessHandleServer implements EkcaAppInter {
	 
	private static String serverId = "AppOperatorHandleInterId";
	private static AppOpratorHandle obj = null;
	private static final Logger log = Logger.getLogger(AppOpratorHandle.class);

	 
	public static AppOpratorHandle getInstance() {
		if (obj == null) {
			obj = new AppOpratorHandle();
		}
		return obj;
	}

	@Override
	public void updateUser(final String oid, final String name, final String type, final String loginname,
			final String imgid, final UpdateState updateState, final UpdateType updateType, final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateUser(oid, name, type, loginname, imgid, updateState, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updateUserOrgid(final String oid, final String orgid, final UpdateType updateType,
			final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateUserOrgid(oid, orgid, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updateUserPostid(final String oid, final List<String> postids, final UpdateType updateType,
			final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateUserPostid(oid, postids, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updateOrg(final String oid, final String treecode, final String name, final String parentid,
			final int sort, final UpdateState updateState, final UpdateType updateType, final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateOrg(oid, treecode, name, parentid, sort, updateState, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updatePost(final String oid, final String name, final String orgid, final UpdateState domainState,
			final UpdateType updateType, final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updatePost(oid, name, orgid, domainState, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updateQuestion(final String oid, final String title, final String typeid, final String anonymous,
			final UpdateState domainState, final UpdateType updateType, final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateQuestion(oid, title, typeid, anonymous, domainState, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updateType(final String oid, final String name, final int sort, final String type,
			final String parentid, final String treecode, final String knowshow, final String fqashow,
			final UpdateState domainState, final UpdateType updateType, final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateType(oid, name, sort, type, parentid, treecode, knowshow, fqashow, domainState,
							updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void updateKnow(final String oid, final String title, final String domtype, final String version,
			final String typeid, final UpdateState domainState, final UpdateType updateType, final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.updateKnow(oid, title, domtype, version, typeid, domainState, updateType, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	@Override
	public void recordOperate(final OperateEvent operateEvent, final String userid, final String userip,
			final String knowId, final String questId, final String versionid, final String oid, final String pid,
			final String secret) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
				try {
					hander.recordOperate(operateEvent, userid, userip, knowId, questId, versionid, oid, pid, secret);
				} catch (RemoteException e) {
					log.warn(e.getMessage());
				}
			}
		});
	}

	public void recordOperateSync(final OperateEvent operateEvent, final String userid, final String userip,
			final String knowId, final String questId, final String versionid, final String oid, final String pid,
			final String secret) {
		try {
			setSync(true);
			runAll(serverId, new BusinessHandleInter() {
				@Override
				public void execute(BusinessHandler impl) {
					EkcaAppInter hander = (EkcaAppInter) impl.getBeanImpl();
					try {
						hander.recordOperate(operateEvent, userid, userip, knowId, questId, versionid, oid, pid,
								secret);
					} catch (RemoteException e) {
						log.warn(e.getMessage());
					}
				}
			});
		} finally {
			setSync(false);
		}
	}
}
