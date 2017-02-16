package com.yundaren.support.po;

/**
 * 交易详情
 */
import java.util.Date;

import lombok.Data;

@Data
public class WeixinProjectPo {

	// ID
	private long id;
	// 需求类型
	private String type;
	// 需求详细描述
	private String content;
	// 联系人姓名
	private String contactsName;
	// 联系人电话
	private String contactNumber;
	// 联系状态 0未处理，1洽谈中，2已发布，3已关闭
	private int status = -1;
	// 创建时间
	private Date createTime;
	// IP位置信息
	private String ipAddress;
	private String remark;
	private String closeReason;
	private String projectId;
}
