package com.phfund.aplus.cms.oms.util;

public enum FileType {
	day("day", "一天内"), week("week", "一星期内"), month("month", "一个月内"), year("year", "一年内"), permanent("perm", "永久");

	private String shortName;
	private String desc;

	private FileType(String shortName, String desc) {
		this.shortName = shortName;
		this.desc = desc;
	}

	public String toString() {
		return this.shortName;
	}

	public String getName() {
		return this.shortName;
	}

	public String getDesc() {
		return this.desc;
	}
}