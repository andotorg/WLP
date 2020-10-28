package com.farm.core;

import com.farm.core.config.PropertiesUtils;
import com.farm.core.time.TimeTool;
import com.farm.util.validate.ValidUtils;
import com.farm.util.web.FarmFormatUnits;
import com.farm.util.web.FarmproHotnum;
import com.farm.util.web.WebHotCase;
import com.farm.util.web.WebVisitBuff;

 
public class FarmUtils {
	 
	public static ValidUtils getValidUtils() {
		return new ValidUtils();
	}

	 
	public static PropertiesUtils getPropertiesUtils(String fileName) {
		return new PropertiesUtils(fileName);
	}

	 
	public static TimeTool getTimeTools() {
		return new TimeTool();
	}

	 
	public static FarmFormatUnits getFormatUtils() {
		return new FarmFormatUnits();
	}

	

	 
	public static FarmproHotnum getHotUtils() {
		return new FarmproHotnum();
	}

	 
	public static WebHotCase getHotWordUtils() {
		return new WebHotCase();
	}

	 
	public static WebVisitBuff getWebVisitBuff(String domain, int maxNum) {
		return WebVisitBuff.getInstance(domain, maxNum);
	}

}
