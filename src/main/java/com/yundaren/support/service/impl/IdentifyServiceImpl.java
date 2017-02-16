package com.yundaren.support.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.yundaren.support.biz.IdentifyBiz;
import com.yundaren.support.biz.ProjectBiz;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.vo.IdentifyVo;

@Slf4j
public class IdentifyServiceImpl implements IdentifyService {

	@Setter
	private IdentifyBiz identifyBiz;

	@Setter
	private ProjectBiz projectBiz;

	@Setter
	private DomainConfig domainConfig;

	@Override
	public long addIdentify(IdentifyVo identifyVo) {
		//identifyVo.setStatus(0);// 待审核状态
		return identifyBiz.addIdentify(identifyVo);
	}

	@Override
	public List<IdentifyVo> getAcceptIdentifyList() {
		return identifyBiz.getAcceptIdentifyList();
	}

	@Override
	public int updateIdentifyByUID(IdentifyVo identifyVo) {
		return identifyBiz.updateIdentifyByUID(identifyVo);
	}

	@Override
	public IdentifyVo getIdentifyByUID(long uid) {
		IdentifyVo identifyInfo = identifyBiz.getIdentifyByUID(uid);
		setImgPath4IdentifyVo(identifyInfo);
		return identifyInfo;
	}

	@Override
	public IdentifyVo getIdentifyByIDCard(String idCard) {
		IdentifyVo identifyInfo = identifyBiz.getIdentifyByIDCard(idCard);
		setImgPath4IdentifyVo(identifyInfo);
		return identifyInfo;
	}

	@Override
	public IdentifyVo getIdentifyByCName(String companyName) {
		IdentifyVo identifyInfo = identifyBiz.getIdentifyByCName(companyName);
		setImgPath4IdentifyVo(identifyInfo);
		return identifyInfo;
	}

	private void setImgPath4IdentifyVo(IdentifyVo identifyInfo) {
		if (identifyInfo != null) {
			String upyunDomain = domainConfig.getBindDomain();
			String fileSecretKey = "!" + identifyInfo.getFileSecretKey();

			// 身份证图片
			String idCardImg = "";
			
			//if(identifyInfo.getStatus() != 3 && identifyInfo.getStatus() != 4){
				idCardImg = StringUtils.isEmpty(identifyInfo.getIdCardImg()) ? "" : upyunDomain
						   + identifyInfo.getIdCardImg() + fileSecretKey;
			///}else{
			///	idCardImg = identifyInfo.getIdCardImg();
			//}
					
					
			identifyInfo.setIdCardImg(idCardImg);

			// 营业执照图片
			String businessLicenseImg = "";
			
			//if(identifyInfo.getStatus() != 3 && identifyInfo.getStatus() != 4){
				businessLicenseImg = StringUtils.isEmpty(identifyInfo.getBusinessLicenseImg()) ? ""
						: upyunDomain + identifyInfo.getBusinessLicenseImg() + fileSecretKey;
			///}else{
			///	businessLicenseImg = identifyInfo.getBusinessLicenseImg();
			///}
			
			identifyInfo.setBusinessLicenseImg(businessLicenseImg);
		}
	}

	@Override
	public long preAddIdentify(IdentifyVo identifyVo) {
		return identifyBiz.addIdentify(identifyVo);
	}
}
