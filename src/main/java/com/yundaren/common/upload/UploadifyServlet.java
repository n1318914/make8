package com.yundaren.common.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yundaren.support.service.UpyunService;

@Slf4j
public class UploadifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected String dirTemp = "attached/temp/";

	@Autowired
	private UpyunService upyunService;

	private static final String EXT_TYPE_IMAGE = "image";

	private static final String EXT_TYPE_FILE = "file";

	// 允许上传的图片后缀
	private static final String[] LEGAL_EXTNAME_ARRAY = new String[] {"jpg", "png", "gif"};

	// 允许上传图片大小2M
	private static final int UPLOAD_IMG_SIZE = 20 * 1024 * 1024;

	// 允许上传的附件后缀
	private static final String[] LEGAL_EXTFILE_ARRAY = new String[] {"doc", "docx", "pdf", "zip","rar", "ppt",
			"pptx", "xls", "xlsx","jpg", "png", "gif","bmp","jpeg"};

	// 允许上传附件大小5M
	private static final int UPLOAD_FILE_SIZE = 20 * 1024 * 1024;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		// 临时文件目录
		String tempPath = this.getServletContext().getRealPath("/") + dirTemp;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(10 * 1024 * 1024); // 设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
		factory.setRepository(new File(tempPath)); // 设定存储临时文件的目录。

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");		
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			String filePath = "";
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				long fileSize = item.getSize();
				if (!item.isFormField()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();					
					// 文件上传格式file|image
					String ext = request.getParameter("ext");
					int type = Integer.parseInt(request.getParameter("type"));
					InputStream is = item.getInputStream();
					if (ext.equalsIgnoreCase(EXT_TYPE_FILE)) {
						if (checkFileOversize(fileSize, UPLOAD_FILE_SIZE)) {
							out.print("{\"error\":\"您上传的附件大小超过20M,请您将上传的附件大小控制在20M以内!\"}");
							break;
						}
						if (!checkFileExtNameOK(fileExt, LEGAL_EXTFILE_ARRAY)) {
							out.print("{\"error\":\"上传的附件格式不正确!\"}");
							break;
						}
						// 执行上传upyun接口						
						filePath = upyunService.uploadFile(request, is, fileExt, type);
					} else {
						if (checkFileOversize(fileSize, UPLOAD_IMG_SIZE)) {
							out.print("{\"error\":\"您上传的图片大小超过20M,请您将上传的图片大小控制在5M以内!\"}");
							break;
						}
						if (!checkFileExtNameOK(fileExt, LEGAL_EXTNAME_ARRAY)) {
							out.print("{\"error\":\"上传的图片格式不正确!\"}");
							break;
						}
						// 执行上传upyun接口
						filePath = upyunService.uploadImage(request, is, fileExt, type, fileName);
					}
					if(request.getParameter("needPath")==null||Integer.parseInt(request.getParameter("needPath").toString())!=1)
						filePath = "{}";
						out.print(filePath);
				}
			}

		} catch (Exception e) {
			log.error("", e);
			out.print("{\"error\": \"上传失败！\"}");
		}	
		out.flush();
		out.close();
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				servletConfig.getServletContext());
		super.init(servletConfig);
	}

	// 检查文件后缀是否合法
	private boolean checkFileExtNameOK(String fileExtName, String[] legalExts) {
		if (!Arrays.asList(legalExts).contains(fileExtName)) {
			return false;
		}
		return true;
	}

	// 大小是否超过限制
	private boolean checkFileOversize(long fileSize, long limitSize) {
		if (fileSize > limitSize) {
			return true;
		}
		return false;
	}
}
