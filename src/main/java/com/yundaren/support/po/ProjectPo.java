package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ProjectPo {

	// 活动ID
	private String id;
	// 需求类型
	private String type;
	// 心里价格
	private String priceRange;
	// 需求简述
	private String name;
	// 需求详细描述
	private String content;
	// 需求附件
	private String attachment;
	// 交付周期(天)
	private int period;
	// 竞标截止时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date bidEndTime;
	// 后台状态 -1审核未通过, 0审核中，1招标中，2托管中，3工作中，4验收中，5验收未通过,6验收通过, 8关闭(未选标),9关闭(任务完成),10已取消，11已评价
	private int backgroudStatus = -10;
	// 提单人用户ID
	private long creatorId;
	// 中标人用户ID
	private long employeeId = -1;
	// 审核人
	private long checkerId = -1;
	// 审核日期
	private Date checkTime;
	// 审核结果
	private String checkResult;
	// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	// 备注
	private String remark;
	// 发标人姓名
	private String userName;
	// 竞标个数
	private int joinCount;
	// 完成时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finishTime;
	// 审核通过时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date acceptTime;
	// 审核不通过原因
	private String acceptResult;
	// 雇主选择公开的联系方式(1手机 2邮箱 3QQ 4微信)用逗号分隔
	private String publicContact;
	// 是否为诚意项目(0否、1是)
	private int isSincerity = -1;
	// 项目排序级别(0普通，1置顶项目，2诚意项目)
	private int ranking = -1;
	// 是否删除(0否，1是)
	private int deleted = -1;
	
	private String contactMobile;
	private String contactEmail;
	private String contactQq;
	private String contactWeixin;
}
