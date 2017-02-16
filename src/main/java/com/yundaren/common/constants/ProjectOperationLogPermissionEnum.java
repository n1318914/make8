package com.yundaren.common.constants;

public enum ProjectOperationLogPermissionEnum {
	ALL("所有人", ProjectOperationLogPermissionEnum.PERMISSION_ALL),
	ADMIN("管理员", ProjectOperationLogPermissionEnum.PERMISSION_ADMIN ), 
	CONSULTANT("顾问", ProjectOperationLogPermissionEnum.PERMISSION_CONSULTANT), 
	DEVELOPER("开发", ProjectOperationLogPermissionEnum.PERMISSION_DEV),
	CREATOR("雇主",ProjectOperationLogPermissionEnum.PERMISSION_CREATOR);
	
	
	/**
	 * 项目状态(0待启动1开发中2已完成)
	 */
	public static final int PERMISSION_ADMIN = 0;
	public static final int PERMISSION_ALL = 1;
	public static final int PERMISSION_CONSULTANT = 2;
	public static final int PERMISSION_DEV = 3;
	public static final int PERMISSION_CREATOR = 4;
	
	// 后台状态名称
	private String name;
	// 状态码
	private int permissionCode;

	private ProjectOperationLogPermissionEnum(String name, int permissionCode) {
		this.name = name;
		this.permissionCode = permissionCode;
	}
	
	public String getName() {
		return name;
	}

	public int getPermissionCode() {
		return permissionCode;
	}
}
