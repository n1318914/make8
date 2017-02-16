package com.yundaren.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import lombok.extern.slf4j.Slf4j;

import com.yundaren.support.config.EmailSendConfig;

@Slf4j
public class EmailSender {
	private static final String charset = "UTF-8";
	private static final String defaultMimetype = "text/plain";

	public static void main(String[] args) throws Exception {
//		EmailSendConfig config = new EmailSendConfig();
//		config.setHost("smtp.mxhichina.com");
//		config.setPort("25");
//		config.setUserName("kai.xu@yundaren.com");
//		config.setPassword("Yundaren@123");

//		config.setHost("smtp.dm.aliyun.com");// smtp.mxhichina.com //smtp.dm.aliyun.com
//		config.setPort("25");
//		config.setUserName("no-reply@notice.waibao.me");// service@waibao.me //no-reply@waibao.me
//		config.setPassword("Waibaome123");
//		config.setReceiver("kai_zi@126.com");
//		config.setNick("外包么");

//		String content = "";
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader("D://temp/3-4/1.txt"));
//			String line = "";
//			while ((line = reader.readLine()) != null) {
//				content += line;
//			}
//			System.out.println(content);
//			reader.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		List<String> listMail = new ArrayList<String>();
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader("D://temp/3-4/list.txt"));
//			String line = "";
//			while ((line = reader.readLine()) != null) {
//				listMail.add(line);
//			}
//			listMail.add(line);
//			reader.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		for (int i = 10; i < 20; i++) {

			EmailSendConfig config = new EmailSendConfig();

//			String receiver = listMail.get(i);
			String receiver = "2172931891@qq.com";
			config.setHost("smtp.dm.aliyun.com");// smtp.mxhichina.com //smtp.dm.aliyun.com
			config.setPort("25");
			config.setUserName("no-reply@notice.make8.com");// service@waibao.me //no-reply@waibao.me
			config.setPassword("Makebang123");
			config.setReceiver(receiver);
			config.setNick("eas-rocsolid");

			String now = DateUtil.formatTime(System.currentTimeMillis(), DateUtil.DATA_STAND_FORMAT_STR);
//			System.out.println(now + " : 第" + (i + 1) + "次发送..." + receiver);
			EmailSender.send(config, "rs-" + "20160328-" + i, "eas-rocsoild, Hello!");
			Thread.sleep(1000);
		}

//			EmailSendConfig config = new EmailSendConfig();
//
//			String receiver = "649906858@qq.com";
//			config.setHost("smtp.dm.aliyun.com");// smtp.mxhichina.com //smtp.dm.aliyun.com
//			config.setPort("25");
//			config.setUserName("no-reply@notice.make8.com");// service@waibao.me //no-reply@waibao.me
//			config.setPassword("Makebang123");
//			config.setReceiver(receiver);
//			config.setNick("码客帮");
//			
//			String now = DateUtil.formatTime(System.currentTimeMillis(), DateUtil.DATA_STAND_FORMAT_STR);
////			System.out.println(now + " : 第" + (i + 1) + "次发送..."  + receiver);
//			EmailSender.send(config, "【紧急重要】外包么更换新品牌了！", "Good!");
//			Thread.sleep(1000);
	}

	public static boolean send(EmailSendConfig mailConfig, String subject, String mailContent) {
		return send(mailConfig, subject, mailContent, null);
	}

	/**
	 * 发送邮件
	 * 
	 * @param mailConfig
	 *            邮件settings
	 * @param subject
	 *            标题
	 * @param mailContent
	 *            邮件内容
	 * @param attachements
	 *            附件
	 * @param mimetype
	 *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
	 */
	public static boolean send(final EmailSendConfig mailConfig, String subject, String mailContent,
			File[] attachements) {

		// 群发和对内发的发信邮箱分开
		String host = mailConfig.getHost();
		final String fUserName = mailConfig.getUserName();
		final String fPassword = mailConfig.getPassword();

		Properties props = new Properties();
		props.put("mail.smtp.host", host);// smtp服务器地址
		props.put("mail.smtp.auth", "true");// 需要校验
		props.put("mail.smtp.port", mailConfig.getPort());// 端口
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fUserName, fPassword);// 登录用户名/密码
			}
		});
//		session.setDebug(true);
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			String nickName = MimeUtility.encodeText(mailConfig.getNick());
			mimeMessage.setFrom(new InternetAddress(fUserName, nickName));// 发件人邮件

			String[] receivers = mailConfig.getReceiver();
			if (receivers != null && receivers.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					toAddress[i] = new InternetAddress(receivers[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);// 收件人邮件
			}

			// 设置邮件密送
			String[] bccs = mailConfig.getBcc();
			if (bccs != null && bccs.length > 0) {
				InternetAddress[] bccAddress = new InternetAddress[bccs.length];
				for (int i = 0; i < bccs.length; i++) {
					bccAddress[i] = new InternetAddress(bccs[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.BCC, bccAddress);
			}
			mimeMessage.setSubject(subject, charset);

			Multipart multipart = new MimeMultipart();
			// 正文
			MimeBodyPart body = new MimeBodyPart();
			// body.setText(message, charset);不支持html
			String mimetype = mailConfig.getMimeType();
			body.setContent(mailContent, (mimetype != null && !"".equals(mimetype) ? mimetype
					: defaultMimetype) + ";charset=" + charset);
			multipart.addBodyPart(body);// 发件内容
			// 附件
			if (attachements != null) {
				for (File attachement : attachements) {
					MimeBodyPart attache = new MimeBodyPart();
					// ByteArrayDataSource bads = new ByteArrayDataSource(byte[],"application/x-any");
					attache.setDataHandler(new DataHandler(new FileDataSource(attachement)));
					String fileName = getLastName(attachement.getName());
					attache.setFileName(MimeUtility.encodeText(fileName, charset, null));
					multipart.addBodyPart(attache);
				}
			}
			mimeMessage.setContent(multipart);
			// SimpleDateFormat formcat = new SimpleDateFormat("yyyy-MM-dd");
			mimeMessage.setSentDate(new Date());// formcat.parse("2010-5-23")
			Transport.send(mimeMessage);
			log.info("send mail successful.");
			return true;
		} catch (Exception e) {
			log.error("send mail failed.", e);
			return false;
		}
	}

	private static String getLastName(String fileName) {
		int pos = fileName.lastIndexOf("\\");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		pos = fileName.lastIndexOf("/");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		return fileName;
	}
}
