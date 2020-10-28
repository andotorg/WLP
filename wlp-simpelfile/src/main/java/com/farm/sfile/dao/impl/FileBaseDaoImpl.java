package com.farm.sfile.dao.impl;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.dao.FileBaseDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

 
@Repository
public class FileBaseDaoImpl extends HibernateSQLTools<FileBase>implements FileBaseDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(FileBase filebase) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(filebase);
	}

	@Override
	public int getAllListNum() {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from farm_code_field");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public FileBase getEntity(String filebaseid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (FileBase) session.get(FileBase.class, filebaseid);
	}

	@Override
	public FileBase insertEntity(FileBase filebase) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(filebase);
		return filebase;
	}

	@Override
	public void editEntity(FileBase filebase) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(filebase);
	}

	@Override
	public Session getSession() {
		// TODO 自动生成代码,修改后请去除本注释
		return sessionFatory.getCurrentSession();
	}

	@Override
	public DataResult runSqlQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<FileBase> selectEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
	}

	@Override
	public int countEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return countSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@Override
	protected Class<?> getTypeClass() {
		return FileBase.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public void delRelationsViewTables(String fileid) {
		if (StringUtils.isNotBlank(fileid)) {
			//删除任务日志表
			{
				Session session = sessionFatory.getCurrentSession();
				SQLQuery query = session.createSQLQuery(
						"DELETE FROM WDAP_VIEW_LOG WHERE TASKID IN (SELECT ID FROM WDAP_VIEW_TASK WHERE FILEID=?)");
				query.setString(0, fileid);
				query.executeUpdate();
			}
			{
				//删除任务表
				Session session = sessionFatory.getCurrentSession();
				SQLQuery query = session.createSQLQuery(
						"DELETE FROM WDAP_VIEW_TASK WHERE FILEID=?");
				query.setString(0, fileid);
				query.executeUpdate();
			}
			{
				//删除预览文件
				Session session = sessionFatory.getCurrentSession();
				SQLQuery query = session.createSQLQuery(
						"DELETE FROM WDAP_VIEW_DOC WHERE FILEID=?");
				query.setString(0, fileid);
				query.executeUpdate();
			}
		}
	}
}
