package com.farm.wcp.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.farm.category.domain.ClassType;

public class BootstrapTreeViews {
	private static final Logger log = Logger.getLogger(BootstrapTreeViews.class);
	/**
	 * 构造树控件的数据对象
	 * 
	 * @param types
	 * @return
	 */
	public static List<Map<String, Object>>  initData(List<ClassType> types) {
		Collections.sort(types, new Comparator<ClassType>() {
			@Override
			public int compare(ClassType o1, ClassType o2) {
				int n = o1.getTreecode().length() - o2.getTreecode().length();
				if (n == 0) {
					return o1.getSort() - o2.getSort();
				} else {
					return n;
				}
			}
		});
		List<Map<String, Object>> treeData = new ArrayList<>();
		Map<String, Map<String, Object>> treeDataMap = new HashMap<>();
		for (ClassType type : types) {
			Map<String, Object> node = null;
			// 构造当前节点
			if (treeDataMap.get(type.getId()) == null) {
				node = new HashMap<>();
				node.put("text", type.getName());
				node.put("id", type.getId());
				treeDataMap.put(type.getId(), node);
			} else {
				node = (Map<String, Object>) treeDataMap.get(type.getId());
			}
			// ---------------------------
			// 挂载当前节点到数据结构中
			if (type.getParentid().equals("NONE")) {
				// 根节点
				treeData.add(node);
			} else {
				// 非根节点
				String parentid = type.getParentid();
				Map<String, Object> parentNode = (Map<String, Object>) treeDataMap.get(parentid);
				if (parentNode != null) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> nodes = (List<Map<String, Object>>) parentNode.get("nodes");
					if (nodes == null) {
						// 没有子节点则构造子节点序列
						nodes = new ArrayList<>();
						parentNode.put("nodes", nodes);
					}
					nodes.add(node);
				} else {
					log.warn("父机构为空，可能是组织机构中有父机构为禁用状态");
				}
			}
		}
		return treeData;
	}
}
