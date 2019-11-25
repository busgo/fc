package com.busgo.fc.inner.query;

import	lombok.Getter;
/***
 *
 * @author create by AutoGenerator
 */
@Getter
public enum SortModeEnum {


	 ASC("asc"),
	 DESC("desc");

	private String mode;

	 SortModeEnum(String mode) {
		this.mode = mode;
	}
}
