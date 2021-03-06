package com.yundaren.support.po;

/**
 * 交易详情
 */
import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TradeInfoPo {

	// 项目ID
	private String projectId;
	// 项目金额
	private double projectAmount;
	// 实际交付金额
	private double actuallyPayment;
	// 项目工期
	private int projectPeriod;
	// 实际交付工期
	private int actuallyPeriod;
	// 是否退款(0否，1是)
	private int isRefund = -1;
	// 创建时间(雇主首次托管金额时间)
	private Date createTime;
	// 托管金额时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date trusteeTime;
	// 已托管金额
	private double trusteeMoney;
}
