package com.farm.report.jxls;

import java.util.Map;


 
public interface ReportManagerInter {
	 
	public void generate(String fileName, Map<String, Object> parameter)
			throws ReportException;

	 
	public String getReportPath(String fileName);

}
