package com.farm.exread.service;

import java.util.ArrayList;
import java.util.List;

import com.farm.exread.domain.ColumnConfig;

public class ReaderConfig {
	protected int sheetNum;
	protected int startRow;
	protected int startCol;
	protected List<ColumnConfig> columns;

	public enum ColumnType {
		STRING, INT;
	}

	private ReaderConfig() {
	};

	 
	public static ReaderConfig newInstance(int sheetNum, int startRow,
			int startCol) {
		ReaderConfig config = new ReaderConfig();
		config.sheetNum = sheetNum;
		config.startRow = startRow;
		config.startCol = startCol;
		config.columns = new ArrayList<ColumnConfig>();
		return config;
	}

	 
	public ReaderConfig addColumn(int num, String key, ColumnType columnType) {
		columns.add(new ColumnConfig(num, key, columnType));
		return this;
	}
	 
	public ReaderConfig addColumn(int num, ColumnType columnType) {
		columns.add(new ColumnConfig(num, columnType));
		return this;
	}

}
