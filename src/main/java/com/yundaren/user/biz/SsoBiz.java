package com.yundaren.user.biz;

import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.common.util.MD5Util;
import com.yundaren.user.dao.SsoDao;
import com.yundaren.user.po.SsoUserPo;
import com.yundaren.user.vo.SsoUserVo;

public class SsoBiz {

	@Setter
	private SsoDao ssoDao;

	public long createSsoUser(SsoUserVo ssoUserVo, boolean isEncryptPwd) {
		SsoUserPo ssoUserPo = BeanMapper.map(ssoUserVo, SsoUserPo.class);
		if (isEncryptPwd) {
			// PASSWORD密文存放
			String encryptedPassword = MD5Util.encodeByMD5(ssoUserVo.getPassword());
			ssoUserPo.setPassword(encryptedPassword);			
		}
		return ssoDao.createSsoUser(ssoUserPo);
	}

	public SsoUserVo getSsoUserByUserName(String userName) {
		SsoUserVo ssoUserVo = null;
		SsoUserPo ssoUserPo = ssoDao.getSsoUserByUserName(userName);
		if (ssoUserPo != null) {
			ssoUserVo = BeanMapper.map(ssoUserPo, SsoUserVo.class);
		}
		return ssoUserVo;
	}

	public SsoUserVo getSsoUserById(long id) {
		SsoUserVo ssoUserVo = null;
		SsoUserPo ssoUserPo = ssoDao.getSsoUserById(id);
		if (ssoUserPo != null) {
			ssoUserVo = BeanMapper.map(ssoUserPo, SsoUserVo.class);
		}
		return ssoUserVo;
	}

	public int update2ActiveStatus(String loginName) {
		return ssoDao.update2ActiveStatus(loginName);
	}

	public int updateSsoUser(SsoUserVo ssoUserVo) {
		SsoUserPo ssoUserPo = BeanMapper.map(ssoUserVo, SsoUserPo.class);
		return ssoDao.updateSsoUser(ssoUserPo);
	}

	public SsoUserVo getSsoUserByAccountType(long userId, int accountType) {
		SsoUserVo ssoUserVo = null;
		SsoUserPo ssoUserPo = ssoDao.getSsoUserByAccountType(userId, accountType);
		if (ssoUserPo != null) {
			ssoUserVo = BeanMapper.map(ssoUserPo, SsoUserVo.class);
		}
		return ssoUserVo;
	}

	public int updateLoginName(String oldLoginName, String newLoginName) {
		return ssoDao.updateLoginName(oldLoginName, newLoginName);
	}

	public int updateSsoUserPassword(String loginName, String newPassword) {
		return ssoDao.updateSsoUserPassword(loginName, newPassword);
	}
	
	public int updatePasswordByID(long uid, String newPassword) {
		return ssoDao.updatePasswordByID(uid, newPassword);
	}
}
