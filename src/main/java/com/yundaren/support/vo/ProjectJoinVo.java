package com.yundaren.support.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectJoinVo {

	// 项目ID
	private String projectId;
	// 接单用户ID
	private long userId;
	// 竞标费用
	private double price;
	// 项目周期
	private int period;
	// 竞标方案
	private String plan;
	// 是否中标(-1淘汰 ,0未选中，1被选)
	private int choosed = -2;
	// 竞标方案附件
	private String attachment;
	// 免费售后周期(0不提供，1-12个月)
	private int matainancePeriod = -1;
	//接单人姓名
	private String userName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date choosedTime;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date kickTime;

	private UserInfoVo userInfo;
	
	/**
	 * added by peton to compare the project join view in arraylist
	 */
	@Override  
	public boolean equals(Object obj) {  
		boolean flag = obj instanceof ProjectJoinVo;  
		
        if(flag == false){  
            return false;  
        }  
        
        ProjectJoinVo pjVo = (ProjectJoinVo)obj;  
        
        if(this.projectId.equals(pjVo.getProjectId()) && this.userId == pjVo.getUserId()){  
            return true;  
        }else {  
            return false;  
        }  
	}
}
