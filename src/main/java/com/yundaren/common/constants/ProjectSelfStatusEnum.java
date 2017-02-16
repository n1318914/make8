package com.yundaren.common.constants;

public enum ProjectSelfStatusEnum {
	REVIEW("审核中", ProjectSelfStatusEnum.STATUS_REVIEW),
	TODO("待启动", ProjectSelfStatusEnum.STATUS_TODO), 
	WORKING("开发中", ProjectSelfStatusEnum.STATUS_WORKING), 
	FINISH("已完成", ProjectSelfStatusEnum.STATUS_FINISH),
	NOTPASSED("未通过",ProjectSelfStatusEnum.STATUS_NOTPASSED),
	CLOSED("已关闭",ProjectSelfStatusEnum.STATUS_CLOSED);

	/**
	 * 项目状态(0待启动1开发中2已完成)
	 */
	public static final int STATUS_REVIEW = -1;
	public static final int STATUS_TODO = 0;
	public static final int STATUS_WORKING = 1;
	public static final int STATUS_FINISH = 2;
	public static final int STATUS_NOTPASSED = 3;
	public static final int STATUS_CLOSED = 4;

	// 后台状态名称
	private String name;
	// 状态码
	private int statusCode;

	private ProjectSelfStatusEnum(String name, int statusCode) {
		this.name = name;
		this.statusCode = statusCode;
	}

	public String getName() {
		return name;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static String getStatusName(int statusCode) {
		for (ProjectSelfStatusEnum obj : ProjectSelfStatusEnum.values()) {
			if (obj.getStatusCode() == statusCode) {
				return obj.getName();
			}
		}
		return "未知状态";
	}
}
