package com.yundaren.support.vo;

/**
 * 预约顾问
 */
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.support.po.WeixinProjectPo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class WeixinProjectVo {
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
	private String priceRange;
	private int period;
	private String closeReason;
	private String projectId;

	// remark的值固定格式为priceRange=100-1000元,peroid=100
	public String getPriceRange() {
		String returnStr = "";
		try {
			if (!StringUtils.isEmpty(remark)) {
				returnStr = remark.substring(remark.indexOf("=") + 1, remark.indexOf(","));
			}
		} catch (Exception e) {
			log.error("format priceRange error! reserve id=" + id + ",remark=" + remark);
			return "待商议";
		}
		return returnStr;
	}

	// remark的值固定格式为priceRange=100-1000元,peroid=100
	public int getPeriod() {
		int returnInt = 0;
		try {
			if (!StringUtils.isEmpty(remark)) {
				String peroid = remark.substring(remark.lastIndexOf("=") + 1);
				returnInt = Integer.parseInt(peroid);
			}
		} catch (Exception e) {
			log.error("format period error! reserve id=" + id + ",remark=" + remark);
			return 30;
		}
		return returnInt;
	}
}
