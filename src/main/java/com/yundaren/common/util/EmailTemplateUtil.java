package com.yundaren.common.util;

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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yundaren.support.service.UpyunService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Slf4j
public class EmailTemplateUtil implements InitializingBean {

	private static Configuration cfg; // 模版配置对象

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化FreeMarker配置
		cfg = new Configuration();
		// 设置FreeMarker的模版文件夹位置
		String classPath = this.getClass().getClassLoader().getResource("/").getPath();
		classPath += "template";
		cfg.setDirectoryForTemplateLoading(new File(classPath));
		
		// 初始化E代测登录
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						EdaiceUtil.login();
						// 一小时登录一次E代测
						Thread.sleep(1000 * 60 * 60 * 1);
					} catch (Exception e) {
						log.error("", e);
					}
				}
			}
		}, "t_login_edaice").start();
	}

	public static Template getTemplate(String templateName) {
		Template template = null;
		try {
			template = cfg.getTemplate("email/" + templateName, "UTF-8");
		} catch (Exception e) {
			log.warn("get Email template " + templateName + " not found.");
		}
		return template;
	}
}
