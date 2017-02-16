package com.yundaren.support.po;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yundaren.basedata.vo.DictItemVo;

@Data
public class ProjectInSelfRunPo {
	private String id;
	private String name;
	private String type;
	private String budget;

	private int startTime;
	private int period = -1;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createTime;
	
	private long creatorId;
	private long consultantId;
	// 项目状态(-1审核中0待启动1开发中2已完成3未通过4已关闭)
	private int status = -2;
	//诚意项目
	private int faithProject = -1;
	private String content;
	private String attachment;

	private String creatorName;
	private String creatorCompanyName;
	
	private String typeName;
	
	private int creatorCategory;
	
	private String startTimeTag;
	
	private String consultantName;
	private String mobile;
	
	
	//实际成交金额（2016-03-16添加）
	private String dealCost;
	
	//代码仓库名称（2016-03-16添加）
	private String repoName;
	
    //项目审核不通过原因（2016-03-16添加）
	private String checkResult;
	
	//项目审核人ID（2016-03-16添加）
	private long checkerId;
	
	private String repoNick;
	
	private String isGogsAllocated;
	
	//项目招募角色ID,用","分隔
	private String enrollRole;
	
	//项目招募角色列表
	private List<DictItemVo> enrollRoleList;
	
	//项目浏览次数(2016-04-20)
	private int viewCount;
	
	//缩略图
	private String abbrImagePath;
	
	//项目审核时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date reviewTime;
	
	//需要够买域名
	private int isNeedBuyDomain = -1;
	
	//需要够买服务器和数据库吗
	private int isNeedBuyServerAndDB = -1;
	
	//最后更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date latestUpdateTime;
}
