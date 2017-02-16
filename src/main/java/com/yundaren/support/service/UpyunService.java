package com.yundaren.support.service;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author kai.xu 又拍云存储服务
 */
public interface UpyunService {

	/**
	 * 上传图片
	 * 
	 * @param ins
	 *            图片文件流
	 * @param extName
	 *            图片文件后缀
	 * @param type
	 *            上传图片类型 (1身份证 2组织机构 3税务登记 4营业执照  5、6公司图片 7 友链LOGO)
	 * @return
	 */
	public String uploadImage(HttpServletRequest request, InputStream ins, String extName, int type,
			String fileName) throws Exception;

	/**
	 * 上传附件
	 * 
	 * @param ins
	 *            文件流
	 * @param extName
	 *            文件后缀
	 * @param projectId
	 *            项目ID
	 * @param type
	 *            上传文件类型 (1项目附件,2竞标附件,3个人简历)
	 * @return
	 */
	public String uploadFile(HttpServletRequest request, InputStream ins, String extName,
			int type) throws Exception;
}
