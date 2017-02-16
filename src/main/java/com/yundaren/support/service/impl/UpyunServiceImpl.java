package com.yundaren.support.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.UpYun;
import com.yundaren.support.service.UpyunService;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

/**
 * 又拍云存储服务
 */
@Slf4j
public class UpyunServiceImpl implements UpyunService {

	@Setter
	DomainConfig domainConfig;
	@Setter
	private UpYun upyun;

	/**
	 * 上传图片
	 * 
	 * @param ins
	 *            图片文件流
	 * @param extName
	 *            图片文件后缀
	 * @param type
	 *            上传图片类型 (1身份证 2组织机构 3税务登记 4营业执照 5项目附件)
	 * @return
	 */
	@Override
	public String uploadImage(HttpServletRequest request, InputStream ins, String extName, int type,
			String ofileName) throws Exception {
		SsoUserVo ssoUserVo = (SsoUserVo) request.getSession().getAttribute(
				CommonConstants.SESSION_LOGIN_USER);
		if (ssoUserVo == null) {
			return "";
		}
		String filePath = "";
		Map<String, String> paramsMap = new HashMap<String, String>();

		// 上传友链LOGO，保存在IMG目录下
		if (type == 7) {
			filePath = "/img/flink/" + ofileName;
			upyun.setFileSecret("");
		} else {
			UserInfoVo userInfo = ssoUserVo.getUserInfoVo();
			String newFileName = DateUtil.formatTime(new Date(), "yyyyMMddHHmmss")
					+ new Random().nextInt(1000) + "." + extName;
			// 文件命名规则 ${uid}/${type}/${时间戳}+三位随机数+.fileExtName
			String fileName = userInfo.getId() + "/" + type + "/" + newFileName;
			// 要传到upyun后的文件路径
			filePath = domainConfig.getUploadImgRoot() + "/img/" + fileName;

			// 设置待上传文件的"访问密钥"
			upyun.setFileSecret(userInfo.getFileSecretKey());

			paramsMap.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), upyun.getQuality());// 图片压缩质量
		}
		log.info("uploadImage upyun filepath is " + filePath);

		// 缓存至SESSION，用于提交时更新数据表
		CommonUtil.setUploadImgSession(request, filePath, type);
		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, ins, true, paramsMap);
		if (!result) {
			throw new Exception("图片上传upyun服务器失败!");
		} else
			return domainConfig.getBindDomain() + filePath;
	}

	@Override
	public String uploadFile(HttpServletRequest request, InputStream ins, String extName, int type)
			throws Exception {

		SsoUserVo ssoUserVo = (SsoUserVo) request.getSession().getAttribute(
				CommonConstants.SESSION_LOGIN_USER);
		if (ssoUserVo == null) {
			return "";
		}

		UserInfoVo userInfo = ssoUserVo.getUserInfoVo();
		String timeFix = DateUtil.formatTime(new Date(), "yyyyMMddHHmmss");

		String newFileName = "description_" + timeFix + "." + extName;
		if (type == 2) {
			newFileName = "plan_" + timeFix + "." + extName;
		} else if (type == 3) {
			newFileName = "resume_" + timeFix + "." + extName;
		}
		// 文件命名规则 ${uid}/${type}/description_${时间戳}+.fileExtName
		String fileName = userInfo.getId() + "/" + type + "/" + newFileName;
		// 要传到upyun后的文件路径
		String filePath = domainConfig.getUploadImgRoot() + "/docs/" + fileName;
		log.info("uploadFile upyun filepath is " + filePath);

		// 缓存至SESSION，用于提交时更新数据表
		CommonUtil.setUploadFileSession(request, filePath, type);
		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, ins, true, null);
		if (!result) {
			throw new Exception("文件上传upyun服务器失败!");
		}
		return domainConfig.getBindDomain() + filePath;
	}
}
