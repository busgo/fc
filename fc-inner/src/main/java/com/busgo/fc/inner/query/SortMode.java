package com.busgo.fc.inner.query;

import	lombok.Data;

import	java.io.Serializable;
/***
 *
 * @author Create By AutoGenerator
 */
@Data
public class SortMode implements Serializable {


	private String columnName;

	private SortModeEnum sortMode;


	public SortMode(String columnName, SortModeEnum sortMode) {
		this.columnName = columnName;
		this.sortMode = sortMode;
	}
}
