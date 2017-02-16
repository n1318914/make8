package com.yundaren.user.biz;

import lombok.Setter;

import org.springside.modules.mapper.BeanMapper;

import com.yundaren.common.util.CommonUtil;
import com.yundaren.user.dao.InvitationDao;
import com.yundaren.user.po.InvitationPo;
import com.yundaren.user.vo.InvitationVo;

public class InvitationBiz {

	@Setter
	private InvitationDao invitationDao;

	public InvitationVo getInvitationCode(String invitationCode) {
		InvitationVo invitationVo = null;
		InvitationPo invitationPo = invitationDao.getInvitationCode(invitationCode);
		if (invitationPo != null) {
			invitationVo = BeanMapper.map(invitationPo, InvitationVo.class);
		}
		return invitationVo;
	}

	public int updateInvitation2Used(String invitationCode, long ssoId) {
		return invitationDao.updateInvitation2Used(invitationCode, ssoId);
	}
	
	public static void main(String[] args) {
		String sqlStr = "insert into invitation(invitation_code) values ";
		// 生成邀请码SQL语句
		for (int i = 0; i < 10; i++) {
			sqlStr += "\r\n('" + CommonUtil.getRandomStr(8) + "'),";
		}
		sqlStr = sqlStr.substring(0, sqlStr.length()-1) + ";";
		System.out.println(sqlStr);
	}
}
