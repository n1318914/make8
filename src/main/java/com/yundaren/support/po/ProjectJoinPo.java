package com.yundaren.support.po;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectJoinPo {

	// 项目ID
	private String projectId;
	// 接单用户ID
	private long userId;
	// 竞标费用
	private double price;
	// 项目周期
	private int period;
	// 竞标方案附件
	private String attachment;
	// 免费售后周期(0不提供，1-12个月)
	private int matainancePeriod = -1;
	// 竞标方案
	private String plan;
	// 是否中标(-1淘汰, 0未选中，1被选中)
	private int choosed = -2;
	private Date createTime;
	private Date choosedTime;
	private String remark;
	private Date kickTime;
}
