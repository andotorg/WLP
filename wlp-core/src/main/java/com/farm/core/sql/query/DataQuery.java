package com.farm.core.sql.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.farm.core.auth.util.MD5Utils;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.util.spring.HibernateSessionFactory;

 
public class DataQuery {
	static final Logger log = Logger.getLogger(DataQuery.class);
	private final static int CACHE_MAX_SIZE = 5000;

	 
	public enum CACHE_UNIT {
		 
		Millisecond(1),  
		second(1000),  
		minute(1000 * 60);
		public int num;

		CACHE_UNIT(int val) {
			num = val;
		}
	}

	private int pagesize = 10;
	private boolean isCount = true;
	private String currentPage;// 当前页
	private String sortTitleText;// 排序字段ok
	private String sortTypeText;// 排序类型ok
	private String ruleText;// 查询条件
	private String titles;// 结果集中展示的字段
	private String tables;// 表名或者内建视图
	private String userWhere;// 自定义查询条件
	private DBSort defaultSort;// 默认排序条件
	private String countSql;
	protected static final Map<String, DataResult> resultCache = new HashMap<String, DataResult>();// 结果集合的缓存
	private boolean DISTINCT = false;
	private boolean ruleValTrim = true;// 是否启用自动去除查询条件值得两边空格
	private long cacheMillisecond;// 启用缓存(毫秒数)：只要该数字大于0则表示启用，从缓存区读取数据如果缓存区没有数据则数据库查询并更新到缓冲区
	protected List<DBSort> sort = new ArrayList<DBSort>();
	protected List<DBRule> queryRule = new ArrayList<DBRule>();

	 
	private String realSql;
	 
	private String realCountSql;

	 
	public static Map<String, Object> getCacheInfo() {
		Map<String, Object> map = new HashMap<>();
		map.put("DATAQUERY.CACHE.CURRENT.SIZE", resultCache.size());
		map.put("DATAQUERY.CACHE.MAX.SIZE", CACHE_MAX_SIZE);
		return map;
	}

	 
	public static void clearCache() {
		resultCache.clear();
	}

	 
	public static DataQuery getInstance(String currentPage, String titles, String tables) {
		DataQuery query = new DataQuery();
		query.setCurrentPage(currentPage);
		query.setTitles(titles);
		query.setTables(tables);
		return query;
	}

	public static DataQuery getInstance(int currentPage, String titles, String tables) {
		return getInstance(String.valueOf(currentPage), titles, tables);
	}

	public static DataQuery getInstance() {
		return new DataQuery();
	}

	 
	public DataResult search(Session session) throws SQLException {
		DataResult result = null;
		try {
			String key = null;
			long startTime = System.currentTimeMillis();
			// -------------------缓存查询----------------------------
			if (cacheMillisecond > 0) {
				key = getQueryMD5Key(this);
				if (key != null) {
					// 启用缓存功能
					{
						DataResult dr = resultCache.get(key);
						// 是否已经有缓存
						if (dr != null) {
							long time = new Date().getTime() - dr.getCtime().getTime();
							// 判断缓存是否超时
							if (time < cacheMillisecond) {
								// 缓存可用
								return dr;
							} else {
								// 启动线程的时候就预先更新缓存时间避免在线程运行中启动多次线程
								dr.setCtime(new Date());
								// 超时也返回过时的，并启动线程查询
								Searcher search = new Searcher(this);
								Thread searchThread = new Thread(search);
								searchThread.start();
								return dr;
							}
						}
					}
				}
				// 缓存中超出缓存最大值的时候就将缓存清空
				if (resultCache.size() > CACHE_MAX_SIZE) {
					resultCache.clear();
				}
			}
			// -------------------缓存查询----------------------------
			if (sort.size() <= 0) {
				sort.add(defaultSort);
			}

			try {
				Searcher search = new Searcher(this);
				result = search.doSearch(session);
				result.setTitles(DataResults.getTitles(titles));
				result.setSortTitleText(sortTitleText);
				result.setSortTypeText(sortTypeText);
			} catch (Exception e) {
				throw new SQLException(e);
			}
			if (cacheMillisecond > 0) {
				// 启用缓存功能,将当前结果存入缓存
				if (key != null) {
					result.setCtime(new Date());
					resultCache.put(key, result);
				}
			}
			{
				// 写日志
				long endTime = System.currentTimeMillis();
				long executeTime = endTime - startTime;
				if (executeTime > 100) {
					log.warn("SQL------runTime:<" + executeTime + "ms>----------------------------------");
					log.warn("SQL-RealSql-----:" + getRealSql());
					if (StringUtils.isNotBlank(getRealCountSql())) {
						log.warn("SQL-RealCountSql:" + getRealCountSql());
					}
				} else {
					log.debug("SQL------runTime:--<" + executeTime + "ms>--");
				}
			}
		} catch (Exception e) {
			log.warn("SQL-ERROR-----:" + e.getMessage());
			log.warn("SQL-ERROR-Sql-----:" + getRealSql());
			if (StringUtils.isNotBlank(getRealCountSql())) {
				log.warn("SQL-ERROR-CountSql:" + getRealCountSql());
			}
			throw e;
		}
		return result;
	}

	 
	public DataResult search() throws SQLException {
		Session session = HibernateSessionFactory.getSession();
		DataResult result = null;
		try {
			result = search(session);
		} finally {
			session.close();
		}
		return result;
	}

	 
	protected static String getQueryMD5Key(DataQuery query) {
		String sql = "";
		try {
			sql = HibernateQueryHandle.praseSQL(query) + ",PAGE:" + query.getCurrentPage();
			sql = MD5Utils.encodeMd5(sql);
		} catch (SQLException e) {
			log.error(e + e.getMessage());
			return null;
		}
		return sql;
	}

	 
	public DataQuery addSort(DBSort dbsort) {
		sort.add(dbsort);
		return this;
	}

	 
	public DataQuery addDefaultSort(DBSort dbsort) {
		defaultSort = dbsort;
		return this;
	}

	 
	public DataQuery clearSort() {
		this.sort.clear();
		return this;
	}

	 
	public DataQuery addRule(DBRule rule) {
		DataQuerys.wipeVirus(rule.getValue());
		this.queryRule.add(rule);
		return this;
	}

	 
	public DBRule getAndRemoveRule(int index) {
		DBRule dbrule = this.queryRule.get(index);
		queryRule.remove(index);
		ruleText = parseRules();
		return dbrule;
	}

	 
	public DBRule getAndRemoveRule(String titleName) {
		int n = -1;
		for (int i = 0; i < queryRule.size(); i++) {
			if (queryRule.get(i).getKey().equals(titleName.toUpperCase())) {
				n = i;
			}
		}
		DBRule dbrule = null;
		if (n >= 0) {
			dbrule = getAndRemoveRule(n);
		}
		return dbrule;
	}

	 
	private String parseRules() {
		StringBuffer sb = null;
		for (DBRule node : queryRule) {
			// parentid:=:NONE_,_
			if (sb == null) {
				sb = new StringBuffer();
			} else {
				sb.append("_,_");
			}
			sb.append(node.getKey());
			sb.append(":");
			sb.append(node.getComparaSign());
			sb.append(":");
			sb.append(node.getValue());
		}
		if (sb == null) {
			return "";
		} else {
			return sb.toString();
		}
	}

	 
	public DataQuery clearRule() {
		this.queryRule.clear();
		return this;
	}

	 
	public DataQuery setDistinct(boolean var) {
		DISTINCT = var;
		return this;
	}

	 
	public boolean isDistinct() {
		return DISTINCT;
	}

	// pojo-------------------------------
	 
	public int getPagesize() {
		return pagesize;
	}

	 
	public DataQuery setPagesize(int pagesize) {
		this.pagesize = pagesize;
		return this;
	}

	 
	public String getCurrentPage() {
		if (currentPage == null || currentPage.trim().length() <= 0) {
			return "1";
		}
		return currentPage;
	}

	 
	public DataQuery setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	 
	public DataQuery setCurrentPage(int currentPage) {
		this.currentPage = String.valueOf(currentPage);
		return this;
	}

	 
	public String getSortTitleText() {
		return sortTitleText;
	}

	 
	public String getSortTypeText() {
		return sortTypeText;
	}

	 
	public void setSortTitleText(String sortTitleText) {
		this.sortTitleText = sortTitleText;
		if (this.sortTitleText != null && this.sortTypeText != null) {
			sort.clear();
			sort.add(new DBSort(this.sortTitleText, this.sortTypeText));
		}
	}

	 
	public void setSortTypeText(String sortTypeText) {
		if (sortTypeText != null && sortTypeText.toUpperCase().trim().equals("NULL")) {
			sortTypeText = null;
		}
		this.sortTypeText = sortTypeText;
		if (this.sortTitleText != null && this.sortTypeText != null) {
			sort.clear();
			sort.add(new DBSort(this.sortTitleText, this.sortTypeText));
		}
	}

	 
	public String getRuleText() {
		return ruleText;
	}

	 
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
		List<DBRule> list = null;
		if (!checkStringForLimitDomain(ruleText)) {
			list = new ArrayList<DBRule>();
		} else {
			ruleText = ruleText.replace("_D_", ":");
			String[] strarray = ruleText.split("_,_");
			list = new ArrayList<DBRule>();
			for (String onestr : strarray) {
				if (onestr != null && !onestr.trim().equals("")) {
					String[] valueT = onestr.split(":");
					if (valueT.length >= 3 && valueT[0] != null && valueT[1] != null && valueT[2] != null) {
						if (!valueT[0].equals("") && !valueT[0].equals("") && !valueT[0].equals("")) {
							DBRule dbrule = new DBRule(valueT[0], (ruleValTrim ? valueT[2].trim() : valueT[2]),
									valueT[1]);
							list.add(dbrule);
						}
					}
				}
			}
		}
		queryRule.clear();
		queryRule.addAll(list);
	}

	 
	public String getTitles() {
		return titles;
	}

	 
	public void setTitles(String titles) {
		this.titles = titles;
	}

	 
	public String getTables() {
		return tables;
	}

	 
	public void setTables(String tables) {
		this.tables = tables;
	}

	 
	private boolean checkStringForLimitDomain(String str) {
		if (str == null)
			return false;
		else
			return true;
	}

	 
	public String getUserWhere() {
		return userWhere;
	}

	 
	public void setSqlRule(String sql) {
		this.userWhere = sql;
	}

	 
	public DataQuery addSqlRule(String SQLString) {
		if (this.userWhere == null) {
			this.userWhere = "";
		}
		this.userWhere = this.userWhere + SQLString;
		return this;
	}

	 
	public static DataQuery init(DataQuery query, String tables, String titles) {
		if (query == null) {
			query = new DataQuery();
		}
		query.setTables(tables);
		query.setTitles(titles);
		if (query.sortTypeText == null) {
			query.sortTypeText = "asc";
		}
		if (query.getCurrentPage() == null) {
			query.setCurrentPage("1");
		}
		// 删除所有排序字段不合乎要求的
		{
			int n = 0;
			List<Integer> indexArray = new ArrayList<Integer>();
			for (; n < query.sort.size(); n++) {
				if (query.sort.get(n).getSortTitleText() == null
						|| query.sort.get(n).getSortTitleText().trim().toUpperCase().equals("NULL")) {
					indexArray.add(n);
				}
			}
			for (Integer index : indexArray) {
				query.sort.remove(index.intValue());
			}
		}

		return query;
	}

	 
	public DBSort getDefaultSort() {
		return defaultSort;
	}

	 
	public void setCache(int cachetime, CACHE_UNIT cache_unit) {
		this.cacheMillisecond = cachetime * cache_unit.num;
	}

	 
	public void setDefaultSort(DBSort defaultSort) {
		this.defaultSort = defaultSort;
	}

	 
	public List<DBRule> getQueryRule() {
		return queryRule;
	}

	 
	public void setNoCount() {
		this.isCount = false;
	}

	 
	public boolean isCount() {
		return isCount;
	}

	public String getCountSql() {
		return countSql;
	}

	 
	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	 
	public String getRule(String key) {
		for (DBRule rule : this.queryRule) {
			if (rule.getKey().equals(key)) {
				return rule.getValue();
			}
		}
		return null;
	}

	public String getRealSql() {
		return realSql;
	}

	public void setRealSql(String realSql) {
		this.realSql = realSql;
	}

	public String getRealCountSql() {
		return realCountSql;
	}

	public void setRealCountSql(String realCountSql) {
		this.realCountSql = realCountSql;
	}

}
