package com.yundaren.common.constants;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.support.vo.ProjectVo;

public enum ProjectStatusEnum{
	BIDING(1, ProjectStatusEnum.TYPE_BIDING, "竞标中", "", ProjectStatusEnum.BACK_BIDING),
	PLANING(2, ProjectStatusEnum.TYPE_WORKING,"托管中", "",ProjectStatusEnum.BACK_PLANING),
	WORKING(2, ProjectStatusEnum.TYPE_WORKING,"工作中", "",ProjectStatusEnum.BACK_WORKING),
	ACCEPTING(2, ProjectStatusEnum.TYPE_WORKING,"验收中", "",ProjectStatusEnum.BACK_ACCPETING),
	ACCEPT_FAILED(2, ProjectStatusEnum.TYPE_WORKING,"验收未通过", "",ProjectStatusEnum.BACK_ACCPET_FAILED),
	ACCEPT_PASS(2, ProjectStatusEnum.TYPE_WORKING,"验收通过", "",ProjectStatusEnum.BACK_ACCPET_PASS),
	CLOSED(3, ProjectStatusEnum.TYPE_FINISHED,"已完成", "",ProjectStatusEnum.BACK_CLOSED),
	FINISHED(3, ProjectStatusEnum.TYPE_FINISHED,"已完成", "",ProjectStatusEnum.BACK_FINISHED),
	CANCEL(3, ProjectStatusEnum.TYPE_FINISHED,"已取消", "",ProjectStatusEnum.BACK_CANCEL),
	EVALUATION(3, ProjectStatusEnum.TYPE_FINISHED,"已评价", "",ProjectStatusEnum.BACK_EVALUATION),
	CHECK_FAILED(4, ProjectStatusEnum.TYPE_CHECK_INVAILD,"审核未通过", "未通过原因:%s", ProjectStatusEnum.BACK_CHECK_INVALID),
	CHECKING(5, ProjectStatusEnum.TYPE_CHECK, "审核中", "等待管理员审核", ProjectStatusEnum.BACK_CHECKING);

	/**
	 * 后台状态 -1审核未通过, 0审核中，1招标中，2托管中，3工作中，4验收中，
	 * 5验收未通过,6验收通过, 8关闭(未选标),9关闭(任务完成),10已取消，11已评价
	 */
	public static final int BACK_CHECK_INVALID = -1;
	public static final int BACK_CHECKING = 0;
	public static final int BACK_BIDING = 1;
	public static final int BACK_PLANING = 2;
	public static final int BACK_WORKING = 3;
	public static final int BACK_ACCPETING = 4;
	public static final int BACK_ACCPET_FAILED = 5;
	public static final int BACK_ACCPET_PASS = 6;
	public static final int BACK_CLOSED = 8;
	public static final int BACK_FINISHED = 9;
	public static final int BACK_CANCEL = 10;
	public static final int BACK_EVALUATION = 11;

	public static final String TYPE_CHECK="审核中";
	public static final String TYPE_CHECK_INVAILD="审核未通过";
	public static final String TYPE_BIDING="竞标中";
	public static final String TYPE_WORKING="工作中";
	public static final String TYPE_FINISHED="已完成";

	// typeID-显示状态
	private int typeId;
	// 显示状态-名称
	private String type;
	// 后台状态名称
	private String sName;
	// 状态提示操作
	private String notice;
	// 状态码
	private int statusCode;
	private ProjectStatusEnum(int typeId, String type, String name, String notice, int statusCode) {
		this.typeId = typeId;
		this.type = type;
		this.sName = name;
		this.notice = notice;
		this.statusCode = statusCode;
	}
	public int getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	public String getsName() {
		return sName;
	}
	public String getNotice() {
		return notice;
	}
	public int getStatusCode() {
		return statusCode;
	}

	public static void setStatus(ProjectVo projectVo) {
		// 如果还处于审核中状态
		for (ProjectStatusEnum obj : ProjectStatusEnum.values()) {
			// 如果是typeID是审核的枚举
			if (obj.getStatusCode() == projectVo.getBackgroudStatus()) {
				projectVo.setStatus(obj);
				return;
			}
		}
	}
	
	/**
	 * 根据typeID返回显示状态对应的后台状态
	 */
	public static List<Integer> getBackListByType(int typeId) {
		List<Integer> listBackStatus = new ArrayList<Integer>();
		for (ProjectStatusEnum obj : ProjectStatusEnum.values()) {
			if (obj.getTypeId() == typeId) {
				listBackStatus.add(obj.getStatusCode());
			}
		}
		return listBackStatus;
	}
	
	/**
	 * 根据typeID返回显示状态对应的后台状态
	 */
	public static String getBackListStrByType(int typeId) {
		String resultStr = "";
		List<Integer> listBackStatus = ProjectStatusEnum.getBackListByType(typeId);
		for (int i : listBackStatus) {
			resultStr += i + ",";
		}
		return StringUtils.isEmpty(resultStr) ? resultStr : resultStr
				.substring(0, resultStr.lastIndexOf(","));
	}
}
