package com.farm.web.tag;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.farm.parameter.service.ParameterServiceInter;
import com.farm.util.spring.BeanFactory;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 当某表数据为空或不为空时显示内容
 * 
 * @author macpl
 *
 */
public class TableHaveData extends TagSupport {
	private String tablename;
	private Boolean isBlank;
	private static LoadingCache<String, Integer> ALL_CACHES = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static ParameterServiceInter aloneIMP = (ParameterServiceInter) BeanFactory
			.getBean("parameterServiceImpl");

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (ALL_CACHES == null) {
			ALL_CACHES = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(5, TimeUnit.MINUTES)
					.build(new CacheLoader<String, Integer>() {
						@Override
						public Integer load(String key) throws Exception {
							return aloneIMP.getTableDateSize(tablename);
						}
					});
		}
		Integer datasize = null;
		try {
			datasize = ALL_CACHES.get("getTableDateSize-" + tablename);
		} catch (ExecutionException e) {
			e.printStackTrace();
			datasize = 1;
		}
		// 权限未注册或用户有权限或权限不检查
		if (isBlank) {
			if (datasize <= 0) {
				return EVAL_BODY_INCLUDE;
			} else {
				return SKIP_BODY;
			}
		} else {
			if (datasize > 0) {
				return EVAL_BODY_INCLUDE;
			} else {
				return SKIP_BODY;
			}
		}
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public Boolean getIsBlank() {
		return isBlank;
	}

	public void setIsBlank(Boolean isBlank) {
		this.isBlank = isBlank;
	}

}
